package antivoland.file.cache.local;

import antivoland.file.cache.FileCacheException;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.String.format;

class CompressedLocalFileCache<DATA> extends LocalFileCache<DATA> {
    private static final String ARCHIVE_EXTENSION = "tar.gz";

    private final String entryExtension;
    private final Class<DATA> clazz;

    CompressedLocalFileCache(Path directory, String fileExtension, Class<DATA> clazz) {
        super(directory, ARCHIVE_EXTENSION);
        this.entryExtension = fileExtension;
        this.clazz = clazz;
    }

    @Override
    public DATA load(String id) {
        var file = file(id);
        if (!exists(file)) return null;
        try (var fi = Files.newInputStream(file);
             var bi = new BufferedInputStream(fi);
             var gzi = new GzipCompressorInputStream(bi);
             var i = new TarArchiveInputStream(gzi)) {

            i.getNextEntry();
            return LocalFileIO.read(i, clazz);
        } catch (IOException e) {
            throw new FileCacheException(format("Failed to decompress file %s", file), e);
        }
    }

    @Override
    public void save(String id, DATA data) {
        var file = file(id, true);
        try (var fo = Files.newOutputStream(file);
             var gzo = new GzipCompressorOutputStream(fo);
             var o = new TarArchiveOutputStream(gzo)) {

            var entry = new TarArchiveEntry(fileName("entry", entryExtension));
            byte[] bytes = bytes(data);
            entry.setSize(bytes.length);
            o.putArchiveEntry(entry);
            o.write(bytes);
            o.closeArchiveEntry();
            o.finish();
        } catch (IOException e) {
            throw new FileCacheException(format("Failed to compress file %s", file), e);
        }
    }

    private byte[] bytes(DATA data) throws IOException {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        LocalFileIO.write(o, data);
        return o.toByteArray();
    }
}