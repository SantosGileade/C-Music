package com.weslley.c_music.exception;

public class EntityInUseException extends RuntimeException {
    public EntityInUseException(String message) {
        super(message);
    }
}

