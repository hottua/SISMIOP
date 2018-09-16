package com.sismiop.sismiop.Exception;

import com.sismiop.sismiop.configuration.FileStorageProperties;

public class FileStorageException extends RuntimeException{
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable couse) {
        super(message, couse);
    }
}
