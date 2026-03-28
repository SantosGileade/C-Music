package com.weslley.c_music.dto.musicaDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicaRequest {

    @NotNull
    @NotBlank
    public String titulo;


    public String autor;

    @NotNull
    @NotBlank
    public String letra;
}
