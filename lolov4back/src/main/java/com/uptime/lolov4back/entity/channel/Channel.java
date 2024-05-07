package com.uptime.lolov4back.entity.channel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 2559)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 2559)
    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

}