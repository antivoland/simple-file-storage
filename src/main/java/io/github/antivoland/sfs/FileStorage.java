package io.github.antivoland.sfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.lang.String.format;

public class FileStorage<DATA> {
    private final Path directory;
    private final FileType<DATA> fileType;

    private FileStorage(Path directory, FileType<DATA> fileType) {
        this.directory = directory;
        this.fileType = fileType;
    }

    public long count() {
        return listIds().count();
    }

    public Stream<String> listIds() {
        if (!Files.exists(directory)) {
            return Stream.empty();
        }
        try (var files = Files.walk(directory).filter(Files::isRegularFile).filter(fileType::hasRequiredExtension)) {
            return files.map(fileType::id).toList().stream();
        } catch (IOException e) {
            throw new FileStorageException(format("Failed to traverse files in directory '%s'", directory), e);
        }
    }

    public Stream<DATA> list() {
        return listIds().map(this::load);
    }

    public boolean exists(String id) {
        return exists(file(id));
    }

    public DATA load(String id) {
        Path file = file(id);
        if (!exists(file)) return null;
        try (InputStream i = Files.newInputStream(file)) {
            return fileType.io.read(i);
        } catch (IOException e) {
            throw new FileStorageException(format("Failed to load file '%s'", file), e);
        }
    }

    public void save(String id, DATA data) {
        var file = file(id, true);
        try (OutputStream o = Files.newOutputStream(file)) {
            fileType.io.write(o, data);
        } catch (IOException e) {
            throw new FileStorageException(format("Failed to save file '%s'", file), e);
        }
    }

    private boolean exists(Path file) {
        return Files.exists(file);
    }

    private Path file(String id) {
        return file(id, false);
    }

    private Path file(String id, boolean provideDirectory) {
        return (provideDirectory ? provideDirectory(directory) : directory).resolve(fileType.name(id));
    }

    private static Path provideDirectory(Path directory) {
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new FileStorageException(format("Failed to create directory '%s'", directory), e);
            }
        }
        return directory;
    }

    public static <DATA> FileStorage<DATA> regular(Path directory, FileType<DATA> fileType) {
        return new FileStorage<>(directory, fileType);
    }

    public static <DATA> FileStorage<DATA> compressed(Path directory, FileType<DATA> fileType) {
        return new FileStorage<>(directory, FileType.archive(fileType));
    }
}