package com.weslley.c_music.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "musicas_playlist",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"musica_id", "playlist_id"})
        }
)
public class MusicaPlaylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "musica_id", nullable = false)
    private Musica musica;

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @Column(nullable = false)
    private Integer ordem;

    @Column(nullable = false)
    private Boolean tocando;
}