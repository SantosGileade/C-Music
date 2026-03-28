package com.weslley.c_music.service;

import com.weslley.c_music.dto.playlistDto.PlaylistRequest;
import com.weslley.c_music.dto.playlistDto.PlaylistResponse;
import com.weslley.c_music.entity.Playlist;
import com.weslley.c_music.exception.EntityInUseException;
import com.weslley.c_music.mapper.MusicaPlaylistMapper;
import com.weslley.c_music.mapper.PlaylistMapper;
import com.weslley.c_music.repository.PlaylistRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public PlaylistResponse save(PlaylistRequest playlistRequest) {
        if (Boolean.TRUE.equals(playlistRequest.getPublico())) {
            desativarPlaylistsPublicas();
        }

        Playlist playlist = PlaylistMapper.INSTANCE.toEntity(playlistRequest);
        return PlaylistMapper.INSTANCE.toResponse(playlistRepository.save(playlist));
    }

    public void delete(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist inexistente"));
        try{
            playlistRepository.delete(playlist);
        }
        catch(DataIntegrityViolationException e){
            throw new EntityInUseException(String.format("A playlist selecionada com o id %d náo pode ser excluida pois tem música atrelada a ela!", id));
        }
    }

    public PlaylistResponse findById(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist inexistente"));
        return PlaylistMapper.INSTANCE.toResponse(playlist);
    }

    public List<PlaylistResponse> findAllByTitle(String title) {
        return playlistRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(PlaylistMapper.INSTANCE::toResponse)
                .toList();
    }

    public PlaylistResponse update(Long id, PlaylistRequest playlistRequest) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist inexistente"));

        if (Boolean.TRUE.equals(playlistRequest.getPublico())) {
            desativarPlaylistsPublicas();
        }

        playlist.setTitle(playlistRequest.getTitle());
        playlist.setPublico(playlistRequest.getPublico());

        return PlaylistMapper.INSTANCE.toResponse(playlistRepository.save(playlist));
    }

    public List<PlaylistResponse> findAll() {
        return playlistRepository.findAll().stream()
                .map(PlaylistMapper.INSTANCE::toResponse)
                .toList();
    }

    private void desativarPlaylistsPublicas() {
        List<Playlist> playlistsPublicas = playlistRepository.findByPublicoTrue();

        for (Playlist playlist : playlistsPublicas) {
            playlist.setPublico(false);
        }

        playlistRepository.saveAll(playlistsPublicas);
    }
}