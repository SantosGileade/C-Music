package com.weslley.c_music.repository;

import com.weslley.c_music.entity.Musica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
    Page<Musica> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
}
