package com.weslley.c_music.repository;

import com.weslley.c_music.entity.MusicaPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicaPlaylistRepository extends JpaRepository<MusicaPlaylist, Long> {

    boolean existsByMusicaIdAndPlaylistId(Long musicaId, Long playlistId);

    boolean existsByPlaylistIdAndOrdem(Long playlistId, Integer ordem);

    boolean existsByPlaylistIdAndOrdemAndIdNot(Long playlistId, Integer ordem, Long id);

    List<MusicaPlaylist> findByPlaylistIdOrderByOrdemAsc(Long playlistId);

    List<MusicaPlaylist> findByPlaylistIdAndTocandoTrue(Long playlistId);
}