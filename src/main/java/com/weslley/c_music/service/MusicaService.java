package com.weslley.c_music.service;

import com.weslley.c_music.dto.musicaDto.MusicaRequest;
import com.weslley.c_music.dto.musicaDto.MusicaResponse;
import com.weslley.c_music.entity.Musica;
import com.weslley.c_music.exception.EntityInUseException;
import com.weslley.c_music.mapper.MusicaMapper;
import com.weslley.c_music.repository.MusicaPlaylistRepository;
import com.weslley.c_music.repository.MusicaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MusicaService {
    private final MusicaRepository musicaRepository;
    private final MusicaPlaylistRepository musicaPlaylistRepository;
    public MusicaService(MusicaRepository musicaRepository, MusicaPlaylistRepository musicaPlaylistRepository) {
        this.musicaRepository = musicaRepository;
        this.musicaPlaylistRepository = musicaPlaylistRepository;
    }

    public MusicaResponse save(MusicaRequest musica){
        Musica musicaEntity = MusicaMapper.INSTANCE.toEntity(musica);
        return MusicaMapper.INSTANCE.toResponse(musicaRepository.save(musicaEntity));
    }
    //esse método orElseThrow tira o objeto que está dentro da "caixa" e joga pra fora
    //e qual o objeto de dentro da "caixa"? o objeto Musica. Por isso que agora o código funcionou.
    public void delete(Long id){
        Musica musica = musicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Musica inexistente"));
        try{
            musicaRepository.delete(musica);
        }
        catch(DataIntegrityViolationException e){
            throw new EntityInUseException(String.format("A música selecionada com o id %d náo pode ser excluida pois está atrelada a uma playlist", id));
        }
    }

    public MusicaResponse findById(Long id){
        return MusicaMapper.INSTANCE.toResponse(musicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Musica inexistente")));
    }

    public MusicaResponse update(Long id, MusicaRequest musicaRequest){
        Musica musica = musicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Musica inexistente"));
        musica.setTitulo(musicaRequest.getTitulo());
        musica.setAutor(musicaRequest.getAutor());
        musica.setLetra(musicaRequest.getLetra());
        return MusicaMapper.INSTANCE.toResponse(musicaRepository.save(musica));
    }

    public List<MusicaResponse> findAll() {
        return musicaRepository.findAll().stream()
                .map(MusicaMapper.INSTANCE::toResponse)
                .toList();
    }

    public Page<MusicaResponse> buscarMusicas(String titulo, Pageable pageable) {
        Page<Musica> paginaMusicas;

        if (titulo != null && !titulo.isBlank()) {
            paginaMusicas = musicaRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        } else {
            paginaMusicas = musicaRepository.findAll(pageable);
        }
        return paginaMusicas.map(MusicaMapper.INSTANCE::toResponse);
    }

}
