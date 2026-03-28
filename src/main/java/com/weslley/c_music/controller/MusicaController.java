package com.weslley.c_music.controller;

import com.weslley.c_music.dto.musicaDto.MusicaRequest;
import com.weslley.c_music.dto.musicaDto.MusicaResponse;
import com.weslley.c_music.service.MusicaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/musica")
public class MusicaController {

    private final MusicaService musicaService;

    public MusicaController(MusicaService musicaService) {
        this.musicaService = musicaService;
    }

    @PostMapping
    public ResponseEntity<MusicaResponse> criar(@RequestBody @Valid MusicaRequest musica){
        return ResponseEntity.status(HttpStatus.CREATED).body(musicaService.save(musica));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<MusicaResponse>> listar(
            @RequestParam(required = false) String titulo,
            @PageableDefault(sort = "titulo", page = 0, size = 10) Pageable paginacao) {

        return ResponseEntity.ok().body(musicaService.buscarMusicas(titulo, paginacao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicaResponse> update(@PathVariable Long id, @RequestBody @Valid MusicaRequest musica){
        return ResponseEntity.ok().body(musicaService.update(id, musica));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        musicaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicaResponse> buscarById(@PathVariable Long id){
        return ResponseEntity.ok().body(musicaService.findById(id));
    }
}
