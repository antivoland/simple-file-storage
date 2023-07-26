package antivoland.file.cache.local;

import antivoland.file.cache.local.io.IO;

public class FileType<DATA> {
    private static final String ARCHIVE_EXTENSION = "tar.gz";

    public final String extension;
    public final IO<DATA> io;

    private FileType(String extension, IO<DATA> io) {
        this.extension = extension;
        this.io = io;
    }

    public static <DOCUMENT> FileType<DOCUMENT> document(String extension, Class<DOCUMENT> clazz) {
        return new FileType<>(extension, IO.document(clazz));
    }

    public static FileType<String> text(String extension) {
        return new FileType<>(extension, IO.text());
    }

    static <DATA> FileType<DATA> archive(FileType<DATA> fileType) {
        return new FileType<>(ARCHIVE_EXTENSION, IO.archive(fileType));
    }
}