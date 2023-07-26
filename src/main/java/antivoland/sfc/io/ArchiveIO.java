package antivoland.sfc.io;

import antivoland.sfc.FileType;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import java.io.*;

class ArchiveIO<DATA> implements IO<DATA> {
    FileType<DATA> fileType;

    ArchiveIO(FileType<DATA> fileType) {
        this.fileType = fileType;
    }

    @Override
    public DATA read(InputStream i) throws IOException {
        try (var bi = new BufferedInputStream(i);
             var gi = new GzipCompressorInputStream(bi);
             var ti = new TarArchiveInputStream(gi)) {

            ti.getNextEntry();
            return fileType.io.read(ti);
        }
    }

    @Override
    public void write(OutputStream o, DATA data) throws IOException {
        try (var go = new GzipCompressorOutputStream(o);
             var to = new TarArchiveOutputStream(go)) {

            var entry = new TarArchiveEntry(fileType.name("entry"));
            byte[] bytes = bytes(data);
            entry.setSize(bytes.length);
            to.putArchiveEntry(entry);
            to.write(bytes);
            to.closeArchiveEntry();
            to.finish();
        }
    }

    private byte[] bytes(DATA data) throws IOException {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        fileType.io.write(o, data);
        return o.toByteArray();
    }
}