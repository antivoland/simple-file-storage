package antivoland.sfc.io;

import antivoland.sfc.FileType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IO<DATA> {
    DATA read(InputStream i) throws IOException;

    void write(OutputStream o, DATA data) throws IOException;

    static IO<String> text() {
        return TextIO.INSTANCE;
    }

    static <DATA> IO<DATA> document(Class<DATA> clazz) {
        return new DocumentIO<>(clazz);
    }

    static <DATA> IO<DATA> archive(FileType<DATA> fileType) {
        return new ArchiveIO<>(fileType);
    }
}