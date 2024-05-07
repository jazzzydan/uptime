package com.uptime.lolov4back.endpoint;

import com.uptime.lolov4back.endpoint.dto.CategoryInfo;
import com.uptime.lolov4back.endpoint.dto.ChannelInfo;
import com.uptime.lolov4back.endpoint.dto.ItemInfo;
import com.uptime.lolov4back.util.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class RSSController {

    private RSSService rssService;

    @GetMapping("/items")
    @Operation(summary = "Return list of articles by channelId and categoryId sorted by date descending",
            description = "If channelId is 0, then all channels are returned. If categoryId is 0, then all categories are returned")
    public List<ItemInfo> getItemsBy(@RequestParam Integer channelId, @RequestParam Integer categoryId) {
        return rssService.getItemsBy(channelId, categoryId);
    }

    @GetMapping("/categories/{channelId}")
    @Operation(summary = "Return list of categories by channelId sorted by name ascending",
            description = "If channelId is 0, then categories for all channels are returned. Used for category dropdown")
    public List<CategoryInfo> getCategories(@PathVariable Integer channelId) {
        return rssService.getCategories(channelId);
    }

    @PostMapping("/channel")
    @Operation(summary = "Fetch channel by url in the payload and saves it to database",
            description = "Feed is parsed and saved to database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Channel name or URL not unique", content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public void fetchChannel(@RequestBody ChannelInfo channelInfo) {
        rssService.fetchChannel(channelInfo);
    }

    @GetMapping("/channel/{id}")
    @Operation(summary = "Fetch channel by url in the payload and saves it to database",
            description = "Used for channel information update and for channel dropdown. Feed articles are parsed and saved to database")
    public List<ChannelInfo> getChannels(@PathVariable Integer id) {
        return rssService.getChannels(id);
    }

    @PutMapping("/channel/{id}")
    @Operation(summary = "Update channel by id",
            description = "Feed articles are deleted from database by resetting database and reloading feed from URL")
    public void updateChannel(@PathVariable Integer id, @RequestBody ChannelInfo channelInfo) {
        rssService.updateChannel(id, channelInfo);
    }

    @DeleteMapping("/channel/{id}")
    @Operation(summary = "Delete channel by id",
            description = "Feed articles are deleted from database by resetting database and fetching remaining feeds")
    public void deleteChannel(@PathVariable Integer id) {
        rssService.deleteChannel(id);
    }

    @DeleteMapping("/reset")
    @Operation(summary = "Reset database to initial state",
            description = "channel table not affected")
    public void emptyAllTablesExceptChannel() {
        rssService.emptyAllTablesExceptChannel();
    }
}