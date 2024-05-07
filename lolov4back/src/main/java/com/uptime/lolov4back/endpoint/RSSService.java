package com.uptime.lolov4back.endpoint;

import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.uptime.lolov4back.endpoint.dto.CategoryInfo;
import com.uptime.lolov4back.endpoint.dto.ChannelInfo;
import com.uptime.lolov4back.endpoint.dto.ItemInfo;
import com.uptime.lolov4back.endpoint.dto.MediaInfo;
import com.uptime.lolov4back.entity.category.Category;
import com.uptime.lolov4back.entity.category.CategoryMapper;
import com.uptime.lolov4back.entity.category.CategoryRepository;
import com.uptime.lolov4back.entity.channel.Channel;
import com.uptime.lolov4back.entity.channel.ChannelMapper;
import com.uptime.lolov4back.entity.channel.ChannelRepository;
import com.uptime.lolov4back.entity.item.Item;
import com.uptime.lolov4back.entity.item.ItemMapper;
import com.uptime.lolov4back.entity.item.ItemRepository;
import com.uptime.lolov4back.entity.itemcategory.ItemCategory;
import com.uptime.lolov4back.entity.itemcategory.ItemCategoryRepository;
import com.uptime.lolov4back.entity.media.Media;
import com.uptime.lolov4back.entity.media.MediaRepository;
import com.uptime.lolov4back.util.DateConverter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jdom2.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.uptime.lolov4back.util.DateConverter.convertToLocalDateTime;

@Service
@AllArgsConstructor
public class RSSService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final MediaRepository mediaRepository;
    private final ChannelRepository channelRepository;

    private final ChannelMapper channelMapper;
    private final CategoryMapper categoryMapper;
    private final ItemMapper itemMapper;

    public void fetchChannel(ChannelInfo channelInfo) {
        try {
            handleIncomingChannel(channelInfo);
            parse(channelInfo);
        } catch (IOException | FeedException e) {
            e.printStackTrace();
            // handle the exception
        }
    }

    public List<ItemInfo> getItemsBy(Integer channelId, Integer categoryId) {

        List<Item> items = itemRepository.findItemsBy(channelId);
        List<Item> itemsByCategory = new ArrayList<>();
        List<ItemCategory> itemCategories = itemCategoryRepository.findCategoriesBy(channelId);

        if (categoryId != 0) {
            for (ItemCategory itemCategory : itemCategories) {
                if (itemCategory.getCategory().getId().equals(categoryId) &&
                        !itemsByCategory.contains(itemCategory.getItem())) {
                    itemsByCategory.add(itemCategory.getItem());
                }
            }
            List<ItemInfo> itemInfos = itemMapper.toItemInfos(itemsByCategory);
            addCategories(itemInfos, itemCategories);
            addMedia(itemInfos);
            return itemInfos;
        } else {
            List<ItemInfo> itemInfos = itemMapper.toItemInfos(items);
            addCategories(itemInfos, itemCategories);
            addMedia(itemInfos);
            return itemInfos;
        }
    }

    private static void addCategories(List<ItemInfo> itemInfos, List<ItemCategory> itemCategories) {
        for (ItemInfo itemInfo : itemInfos) {
            List<CategoryInfo> categoryInfos = new ArrayList<>();
            for (ItemCategory itemCategory : itemCategories) {
                if (itemCategory.getItem().getId().equals(itemInfo.getItemId())) {
                    boolean categoryExists = categoryInfos.stream()
                            .anyMatch(categoryInfo -> categoryInfo.getCategoryId().equals(itemCategory.getCategory().getId()));

                    if (!categoryExists) {
                        CategoryInfo categoryInfo = new CategoryInfo();
                        categoryInfo.setCategoryId(itemCategory.getCategory().getId());
                        categoryInfo.setCategoryName(itemCategory.getCategory().getName());
                        categoryInfos.add(categoryInfo);
                    }
                }
            }
            itemInfo.setCategoryInfos(categoryInfos);
        }
    }

    private void addMedia(List<ItemInfo> itemInfos) {
        MediaInfo mediaInfo = new MediaInfo();
        for (ItemInfo itemInfo : itemInfos) {
            Optional<Media> optionalMedia = mediaRepository.findMediaBy(itemInfo.getItemId());
            if (optionalMedia.isPresent()) {
                mediaInfo.setItemId(optionalMedia.get().getItem().getId());
                mediaInfo.setUrl(optionalMedia.get().getUrl());
                mediaInfo.setMedium(optionalMedia.get().getMedium());
                itemInfo.setMediaInfo(mediaInfo);
            }
        }
    }

    public List<CategoryInfo> getCategories(Integer channelId) {

        List<Category> categories = categoryRepository.getCategoriesBy();
        List<Category> categoriesByChannel = new ArrayList<>();

        if (channelId != 0) {
            List<ItemCategory> itemCategories = itemCategoryRepository.findAllItemCategoriesSorted();
            for (ItemCategory itemCategory : itemCategories) {
                if (itemCategory.getItem().getChannel().getId().equals(channelId) &&
                        !categoriesByChannel.contains(itemCategory.getCategory())) {
                    categoriesByChannel.add(itemCategory.getCategory());
                }
            }
            return categoryMapper.toCategoryInfos(categoriesByChannel);
        } else {
            return categoryMapper.toCategoryInfos(categories);
        }
    }

    public List<ChannelInfo> getChannels(Integer id) {
        List<Channel> channels = channelRepository.getChannelsBy(id);
        return channelMapper.toChannelInfos(channels);
    }

    @Transactional
    public void updateChannel(Integer id, ChannelInfo channelInfo) {
        Channel channel = channelRepository.getReferenceById(id);

        channel.setTitle(channelInfo.getChannelTitle());
        channel.setUrl(channelInfo.getChannelURL());
        channelRepository.save(channel);
    }

    public void deleteChannel(Integer id) {
        channelRepository.deleteById(id);
    }

    public void emptyAllTablesExceptChannel() {
        // Delete all records from the tables except the channel table
        itemCategoryRepository.deleteAll();
        mediaRepository.deleteAll();
        itemRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    private void handleIncomingChannel(ChannelInfo channelInfo) {
        if (!channelRepository.channelExistsBy(channelInfo.getChannelURL()) &&
                !channelRepository.channelExistsByTitle(channelInfo.getChannelTitle())) {
            Channel channel = new Channel();
            channel.setTitle(channelInfo.getChannelTitle());
            channel.setUrl(channelInfo.getChannelURL());
            channelRepository.save(channel);
        }
    }

    private void parse(ChannelInfo channelInfo) throws IOException, FeedException {
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(channelInfo.getChannelURL())));

        for (SyndEntry entry : feed.getEntries()) {
            Item item = new Item();
            item.setTitle(entry.getTitle());
            item.setLink(entry.getLink());
            item.setGuid(entry.getUri());
            item.setPubDate(DateConverter.convertToLocalDateTime(entry.getPublishedDate()));
            item.setDescription(entry.getDescription().getValue());
            item.setSource(feed.getTitle());
            item.setAuthor(entry.getAuthor());

            Channel channel = channelRepository.getChannelBy(channelInfo.getChannelURL());
            item.setChannel(channel);
            itemRepository.save(item);

            for (SyndCategory syndCategory : entry.getCategories()) {
                Category category = new Category();
                String categoryName = syndCategory.getName();
                ItemCategory itemCategory = new ItemCategory();

                if (categoryRepository.categoryExistsBy(categoryName)) {
                    category = categoryRepository.getCategoryBy(categoryName);
                    itemCategory.setCategory(category);

                    Item savedItem = itemRepository.getByGuid(item.getGuid());
                    itemCategory.setItem(savedItem);

                    itemCategoryRepository.save(itemCategory);
                } else {
                    category.setName(categoryName);
                    categoryRepository.save(category);

                    Category savedCategory = categoryRepository.getCategoryBy(categoryName);
                    itemCategory.setCategory(savedCategory);

                    Item savedItem = itemRepository.getByGuid(item.getGuid());
                    itemCategory.setItem(savedItem);

                    itemCategoryRepository.save(itemCategory);
                }
            }

            for (Element element : (List<Element>) entry.getForeignMarkup()) {
                if (element.getNamespaceURI().equals("http://search.yahoo.com/mrss/")) {
                    Media media = new Media();
                    media.setUrl(element.getAttributeValue("url"));
                    media.setType(element.getAttributeValue("type"));
                    media.setMedium(element.getAttributeValue("medium"));

                    // Parse width and height if they exist
                    String width = element.getAttributeValue("width");
                    String height = element.getAttributeValue("height");
                    if (width != null) {
                        media.setWidth(Integer.parseInt(width));
                    }
                    if (height != null) {
                        media.setHeight(Integer.parseInt(height));
                    }

                    Item savedItem = itemRepository.getByGuid(item.getGuid());
                    media.setItem(savedItem);

                    mediaRepository.save(media);
                }
            }
        }
    }
}
