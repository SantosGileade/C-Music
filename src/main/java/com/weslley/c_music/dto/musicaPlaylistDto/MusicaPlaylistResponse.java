package com.weslley.c_music.dto.musicaPlaylistDto;

public class MusicaPlaylistResponse {

    private Long id;
    private Long musicaId;
    private String musicaTitulo;
    private Long playlistId;
    private String playlistTitle;
    private Integer ordem;
    private Boolean tocando;

    public MusicaPlaylistResponse() {
    }

    public MusicaPlaylistResponse(Long id, Long musicaId, String musicaTitulo,
                                  Long playlistId, String playlistTitle,
                                  Integer ordem, Boolean tocando) {
        this.id = id;
        this.musicaId = musicaId;
        this.musicaTitulo = musicaTitulo;
        this.playlistId = playlistId;
        this.playlistTitle = playlistTitle;
        this.ordem = ordem;
        this.tocando = tocando;
    }

    public Long getId() {
        return id;
    }

    public Long getMusicaId() {
        return musicaId;
    }

    public String getMusicaTitulo() {
        return musicaTitulo;
    }

    public Long getPlaylistId() {
        return playlistId;
    }

    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public Boolean getTocando() {
        return tocando;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMusicaId(Long musicaId) {
        this.musicaId = musicaId;
    }

    public void setMusicaTitulo(String musicaTitulo) {
        this.musicaTitulo = musicaTitulo;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public void setPlaylistTitle(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public void setTocando(Boolean tocando) {
        this.tocando = tocando;
    }
}