package io.github.antivoland.sfc;

import io.github.antivoland.sfc.io.IO;

import java.nio.file.Path;

public class FileType<DATA> {
    private static final String ARCHIVE_EXTENSION = "tar.gz";

    public final String extension;
    public final IO<DATA> io;

    private FileType(String extension, IO<DATA> io) {
        this.extension = extension;
        this.io = io;
    }

    String id(Path file) {
        return id(name(file));
    }

    String id(String name) {
        return name.substring(0, name.length() - extension.length() - 1);
    }

    String name(Path file) {
        return file.getFileName().toString();
    }

    public String name(String id) {
        return id + "." + extension;
    }

    boolean hasRequiredExtension(Path file) {
        return name(file).toLowerCase().endsWith("." + extension.toLowerCase());
    }

    public static FileType<String> text(String extension) {
        return new FileType<>(extension, IO.text());
    }

    public static <DATA> FileType<DATA> document(String extension, Class<DATA> clazz) {
        return new FileType<>(extension, IO.document(clazz));
    }

    static <DATA> FileType<DATA> archive(FileType<DATA> fileType) {
        return new FileType<>(ARCHIVE_EXTENSION, IO.archive(fileType));
    }
}