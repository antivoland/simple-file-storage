package antivoland.sfs;

public class StorageException extends RuntimeException {
    StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}