package com.weslley.c_music.mapper;

import com.weslley.c_music.dto.musicaPlaylistDto.MusicaPlaylistRequest;
import com.weslley.c_music.dto.musicaPlaylistDto.MusicaPlaylistResponse;
import com.weslley.c_music.entity.MusicaPlaylist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MusicaPlaylistMapper {

    MusicaPlaylistMapper INSTANCE = Mappers.getMapper(MusicaPlaylistMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "musica", ignore = true)
    @Mapping(target = "playlist", ignore = true)
    MusicaPlaylist toEntity(MusicaPlaylistRequest request);

    @Mapping(source = "musica.id", target = "musicaId")
    @Mapping(source = "musica.titulo", target = "musicaTitulo")
    @Mapping(source = "playlist.id", target = "playlistId")
    @Mapping(source = "playlist.title", target = "playlistTitle")
    MusicaPlaylistResponse toResponse(MusicaPlaylist musicaPlaylist);
}