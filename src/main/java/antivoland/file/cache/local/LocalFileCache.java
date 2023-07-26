package antivoland.file.cache.local;

import antivoland.file.cache.FileCache;
import antivoland.file.cache.FileCacheException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.lang.String.format;

public abstract class LocalFileCache<DATA> implements FileCache<DATA> {
    private final Path directory;
    private final String fileExtension;

    protected LocalFileCache(Path directory, String fileExtension) {
        this.directory = directory;
        this.fileExtension = fileExtension;
    }

    @Override
    public final Stream<String> listIds() {
        if (!Files.exists(directory)) {
            return Stream.empty();
        }
        try (var files = Files.walk(directory).filter(Files::isRegularFile).filter(this::hasRequiredFileExtension)) {
            return files.map(this::fileId).toList().stream();
        } catch (IOException e) {
            throw new FileCacheException(format("Failed to traverse files in directory '%s'", directory), e);
        }
    }

    @Override
    public final boolean exists(String id) {
        return exists(file(id));
    }

    protected final boolean exists(Path file) {
        return Files.exists(file);
    }

    protected final Path file(String id) {
        return file(id, false);
    }

    protected final Path file(String id, boolean provideDirectory) {
        return (provideDirectory ? provideDirectory(directory) : directory).resolve(fileName(id, fileExtension));
    }

    private boolean hasRequiredFileExtension(Path file) {
        return fileName(file).toLowerCase().endsWith("." + fileExtension.toLowerCase());
    }

    private String fileId(Path file) {
        return fileId(fileName(file));
    }

    private String fileId(String fileName) {
        return fileName.substring(0, fileName.length() - fileExtension.length() - 1);
    }

    private static String fileName(Path file) {
        return file.getFileName().toString();
    }

    protected static String fileName(String id, String fileExtension) {
        return id + "." + fileExtension;
    }

    private static Path provideDirectory(Path directory) {
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new FileCacheException(format("Failed to create directory '%s'", directory), e);
            }
        }
        return directory;
    }

    public static <DATA> LocalFileCache<DATA> regular(Path directory, String fileExtension, Class<DATA> clazz) {
        return new RegularLocalFileCache<>(directory, fileExtension, clazz);
    }

    public static <DATA> LocalFileCache<DATA> compressed(Path directory, String fileExtension, Class<DATA> clazz) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}