package com.snapstore.SnapStore.ExceptionHandler;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
