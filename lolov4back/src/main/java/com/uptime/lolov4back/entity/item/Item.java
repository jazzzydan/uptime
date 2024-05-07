package com.uptime.lolov4back.entity.item;

import com.uptime.lolov4back.entity.channel.Channel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @Size(max = 2559)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 2559)
    @NotNull
    @Column(name = "link", nullable = false)
    private String link;

    @Size(max = 2559)
    @Column(name = "guid")
    private String guid;

    @NotNull
    @Column(name = "pub_date", nullable = false)
    private LocalDateTime pubDate;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Size(max = 2559)
    @NotNull
    @Column(name = "source", nullable = false)
    private String source;

    @Size(max = 2559)
    @NotNull
    @Column(name = "author", nullable = false)
    private String author;

    @Size(max = 2559)
    @Column(name = "response")
    private String response;

}