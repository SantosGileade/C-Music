package com.weslley.c_music.dto.musicaDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MusicaResponse {
    private Long id;
    private String titulo;
    private String autor;
    private String letra;
}
