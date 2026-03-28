package com.weslley.c_music.repository;

import com.weslley.c_music.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByPublicoTrue();

    List<Playlist> findByTitleContainingIgnoreCase(String title);

}