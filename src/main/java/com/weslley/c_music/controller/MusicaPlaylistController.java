package com.weslley.c_music.controller;

import com.weslley.c_music.dto.musicaPlaylistDto.MusicaPlaylistRequest;
import com.weslley.c_music.dto.musicaPlaylistDto.MusicaPlaylistResponse;
import com.weslley.c_music.service.MusicaPlaylistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/musica-playlist")
public class MusicaPlaylistController {

    private final MusicaPlaylistService musicaPlaylistService;

    public MusicaPlaylistController(MusicaPlaylistService musicaPlaylistService) {
        this.musicaPlaylistService = musicaPlaylistService;
    }

    @PostMapping
    public ResponseEntity<MusicaPlaylistResponse> criar(@RequestBody @Valid MusicaPlaylistRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(musicaPlaylistService.save(request));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MusicaPlaylistResponse>> listar() {
        return ResponseEntity.ok().body(musicaPlaylistService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicaPlaylistResponse> buscarById(@PathVariable Long id) {
        return ResponseEntity.ok().body(musicaPlaylistService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicaPlaylistResponse> update(@PathVariable Long id,
                                                         @RequestBody @Valid MusicaPlaylistRequest request) {
        return ResponseEntity.ok().body(musicaPlaylistService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        musicaPlaylistService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<List<MusicaPlaylistResponse>> listarPorPlaylist(@PathVariable Long playlistId) {
        return ResponseEntity.ok().body(musicaPlaylistService.findByPlaylistId(playlistId));
    }
}