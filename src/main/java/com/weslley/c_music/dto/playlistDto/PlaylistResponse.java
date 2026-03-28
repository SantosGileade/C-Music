package com.weslley.c_music.dto.playlistDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponse {

    private Long id;
    private String title;
    private Boolean publico;
}