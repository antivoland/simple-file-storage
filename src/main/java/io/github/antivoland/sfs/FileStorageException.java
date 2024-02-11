package io.github.antivoland.sfs;

public class FileStorageException extends RuntimeException {
    FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}