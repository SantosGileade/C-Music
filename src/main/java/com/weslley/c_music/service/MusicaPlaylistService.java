package com.weslley.c_music.service;

import com.weslley.c_music.dto.musicaPlaylistDto.MusicaPlaylistRequest;
import com.weslley.c_music.dto.musicaPlaylistDto.MusicaPlaylistResponse;
import com.weslley.c_music.entity.Musica;
import com.weslley.c_music.entity.MusicaPlaylist;
import com.weslley.c_music.entity.Playlist;
import com.weslley.c_music.mapper.MusicaPlaylistMapper;
import com.weslley.c_music.repository.MusicaPlaylistRepository;
import com.weslley.c_music.repository.MusicaRepository;
import com.weslley.c_music.repository.PlaylistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MusicaPlaylistService {

    private final MusicaPlaylistRepository musicaPlaylistRepository;
    private final MusicaRepository musicaRepository;
    private final PlaylistRepository playlistRepository;

    public MusicaPlaylistService(MusicaPlaylistRepository musicaPlaylistRepository,
                                 MusicaRepository musicaRepository,
                                 PlaylistRepository playlistRepository) {
        this.musicaPlaylistRepository = musicaPlaylistRepository;
        this.musicaRepository = musicaRepository;
        this.playlistRepository = playlistRepository;
    }

    @Transactional
    public MusicaPlaylistResponse save(MusicaPlaylistRequest request) {
        if (musicaPlaylistRepository.existsByMusicaIdAndPlaylistId(request.getMusicaId(), request.getPlaylistId())) {
            throw new RuntimeException("Essa música já está nessa playlist");
        }

        if (musicaPlaylistRepository.existsByPlaylistIdAndOrdem(request.getPlaylistId(), request.getOrdem())) {
            throw new RuntimeException("Já existe uma música nessa ordem dentro da playlist");
        }

        Musica musica = musicaRepository.findById(request.getMusicaId())
                .orElseThrow(() -> new RuntimeException("Música inexistente"));

        Playlist playlist = playlistRepository.findById(request.getPlaylistId())
                .orElseThrow(() -> new RuntimeException("Playlist inexistente"));

        if (Boolean.TRUE.equals(request.getTocando())) {
            desativarMusicasTocandoDaPlaylist(request.getPlaylistId());
        }

        MusicaPlaylist musicaPlaylist = MusicaPlaylistMapper.INSTANCE.toEntity(request);
        musicaPlaylist.setMusica(musica);
        musicaPlaylist.setPlaylist(playlist);

        return MusicaPlaylistMapper.INSTANCE.toResponse(musicaPlaylistRepository.save(musicaPlaylist));
    }

    public void delete(Long id) {
        MusicaPlaylist musicaPlaylist = musicaPlaylistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro inexistente"));

        musicaPlaylistRepository.delete(musicaPlaylist);
    }

    public MusicaPlaylistResponse findById(Long id) {
        MusicaPlaylist musicaPlaylist = musicaPlaylistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro inexistente"));

        return MusicaPlaylistMapper.INSTANCE.toResponse(musicaPlaylist);
    }

    @Transactional
    public MusicaPlaylistResponse update(Long id, MusicaPlaylistRequest request) {
        MusicaPlaylist musicaPlaylist = musicaPlaylistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro inexistente"));

        if ((!musicaPlaylist.getMusica().getId().equals(request.getMusicaId()) ||
                !musicaPlaylist.getPlaylist().getId().equals(request.getPlaylistId())) &&
                musicaPlaylistRepository.existsByMusicaIdAndPlaylistId(request.getMusicaId(), request.getPlaylistId())) {
            throw new RuntimeException("Essa música já está nessa playlist");
        }

        if (musicaPlaylistRepository.existsByPlaylistIdAndOrdemAndIdNot(request.getPlaylistId(), request.getOrdem(), id)) {
            throw new RuntimeException("Já existe uma música nessa ordem dentro da playlist");
        }

        Musica musica = musicaRepository.findById(request.getMusicaId())
                .orElseThrow(() -> new RuntimeException("Música inexistente"));

        Playlist playlist = playlistRepository.findById(request.getPlaylistId())
                .orElseThrow(() -> new RuntimeException("Playlist inexistente"));

        if (Boolean.TRUE.equals(request.getTocando())) {
            desativarMusicasTocandoDaPlaylist(request.getPlaylistId());
        }

        musicaPlaylist.setMusica(musica);
        musicaPlaylist.setPlaylist(playlist);
        musicaPlaylist.setOrdem(request.getOrdem());
        musicaPlaylist.setTocando(request.getTocando());

        return MusicaPlaylistMapper.INSTANCE.toResponse(musicaPlaylistRepository.save(musicaPlaylist));
    }

    public List<MusicaPlaylistResponse> findAll() {
        return musicaPlaylistRepository.findAll().stream()
                .map(MusicaPlaylistMapper.INSTANCE::toResponse)
                .toList();
    }

    public List<MusicaPlaylistResponse> findByPlaylistId(Long playlistId) {
        return musicaPlaylistRepository.findByPlaylistIdOrderByOrdemAsc(playlistId).stream()
                .map(MusicaPlaylistMapper.INSTANCE::toResponse)
                .toList();
    }

    private void desativarMusicasTocandoDaPlaylist(Long playlistId) {
        List<MusicaPlaylist> musicasTocando = musicaPlaylistRepository.findByPlaylistIdAndTocandoTrue(playlistId);

        for (MusicaPlaylist musicaPlaylist : musicasTocando) {
            musicaPlaylist.setTocando(false);
        }

        musicaPlaylistRepository.saveAll(musicasTocando);
    }
}