package com.weslley.c_music.mapper;

import com.weslley.c_music.dto.musicaDto.MusicaRequest;
import com.weslley.c_music.dto.musicaDto.MusicaResponse;
import com.weslley.c_music.entity.Musica;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MusicaMapper{
    MusicaMapper INSTANCE = Mappers.getMapper(MusicaMapper.class);

    Musica toEntity(MusicaRequest musicaRequest);

    MusicaResponse toResponse(Musica musica);

}
