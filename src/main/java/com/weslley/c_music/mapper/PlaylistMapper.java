package com.weslley.c_music.mapper;

import com.weslley.c_music.dto.playlistDto.PlaylistRequest;
import com.weslley.c_music.dto.playlistDto.PlaylistResponse;
import com.weslley.c_music.entity.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlaylistMapper {
    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    Playlist toEntity(PlaylistRequest playlistRequest);

    PlaylistResponse toResponse(Playlist playlist);
}