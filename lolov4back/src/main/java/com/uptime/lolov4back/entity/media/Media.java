package com.uptime.lolov4back.entity.media;

import com.uptime.lolov4back.entity.item.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Size(max = 2559)
    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @Size(max = 255)
    @Column(name = "medium")
    private String medium;

    @Size(max = 255)
    @Column(name = "type")
    private String type;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

}