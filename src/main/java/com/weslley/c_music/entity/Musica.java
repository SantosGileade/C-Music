package com.weslley.c_music.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "musica")
public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String titulo;

    @Column(length = 60)
    private String autor;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String letra;

    @OneToMany(mappedBy = "musica")
    private List<MusicaPlaylist> playlists;
}
