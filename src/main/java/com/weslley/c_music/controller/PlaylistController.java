package com.weslley.c_music.controller;

import com.weslley.c_music.dto.playlistDto.PlaylistRequest;
import com.weslley.c_music.dto.playlistDto.PlaylistResponse;
import com.weslley.c_music.service.PlaylistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<PlaylistResponse> criar(@RequestBody @Valid PlaylistRequest playlistRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playlistService.save(playlistRequest));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PlaylistResponse>> listar() {
        return ResponseEntity.ok().body(playlistService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponse> update(@PathVariable Long id,
                                                   @RequestBody @Valid PlaylistRequest playlistRequest) {
        return ResponseEntity.ok().body(playlistService.update(id, playlistRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        playlistService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponse> buscarById(@PathVariable Long id) {
        return ResponseEntity.ok().body(playlistService.findById(id));
    }

    @GetMapping("/listar/{title}")
    public ResponseEntity<List<PlaylistResponse>> listarByTitle(@PathVariable String title) {
        return ResponseEntity.ok().body(playlistService.findAllByTitle(title));
    }
}