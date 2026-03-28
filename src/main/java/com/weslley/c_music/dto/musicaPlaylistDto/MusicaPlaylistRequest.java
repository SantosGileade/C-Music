package com.weslley.c_music.dto.musicaPlaylistDto;

import jakarta.validation.constraints.NotNull;

public class MusicaPlaylistRequest {

    @NotNull
    private Long musicaId;

    @NotNull
    private Long playlistId;

    @NotNull
    private Integer ordem;

    @NotNull
    private Boolean tocando;

    public MusicaPlaylistRequest() {
    }

    public MusicaPlaylistRequest(Long musicaId, Long playlistId, Integer ordem, Boolean tocando) {
        this.musicaId = musicaId;
        this.playlistId = playlistId;
        this.ordem = ordem;
        this.tocando = tocando;
    }

    public Long getMusicaId() {
        return musicaId;
    }

    public void setMusicaId(Long musicaId) {
        this.musicaId = musicaId;
    }

    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getTocando() {
        return tocando;
    }

    public void setTocando(Boolean tocando) {
        this.tocando = tocando;
    }
}