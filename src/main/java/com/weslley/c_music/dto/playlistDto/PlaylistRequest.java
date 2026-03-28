package com.weslley.c_music.dto.playlistDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistRequest {

    @NotBlank
    private String title;

    @NotNull
    private Boolean publico;

}