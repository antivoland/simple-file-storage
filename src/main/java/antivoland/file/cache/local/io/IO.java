package antivoland.file.cache.local.io;

import antivoland.file.cache.local.FileType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IO<DATA> {
    DATA read(InputStream i) throws IOException;

    void write(OutputStream o, DATA data) throws IOException;

    static <DATA> DocumentIO<DATA> document(Class<DATA> clazz) {
        return new DocumentIO<>(clazz);
    }

    static TextIO text() {
        return TextIO.INSTANCE;
    }

    static <DATA> ArchiveIO<DATA> archive(FileType<DATA> fileType) {
        return new ArchiveIO<>(fileType);
    }
}