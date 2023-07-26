package antivoland.file.cache.local;

import antivoland.file.cache.FileCacheException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.String.format;

class RegularLocalFileCache<DATA> extends LocalFileCache<DATA> {
    private final Class<DATA> clazz;

    RegularLocalFileCache(Path directory, String fileExtension, Class<DATA> clazz) {
        super(directory, fileExtension);
        this.clazz = clazz;
    }

    @Override
    public DATA load(String id) {
        Path file = file(id);
        if (!exists(file)) return null;
        try (InputStream in = Files.newInputStream(file)) {
            return LocalFileIO.read(in, clazz);
        } catch (IOException e) {
            throw new FileCacheException(format("Failed to load file '%s'", file), e);
        }
    }

    @Override
    public void save(String id, DATA data) {
        var file = file(id, true);
        try (OutputStream out = Files.newOutputStream(file)) {
            LocalFileIO.write(out, data);
        } catch (IOException e) {
            throw new FileCacheException(format("Failed to save file '%s'", file), e);
        }
    }
}