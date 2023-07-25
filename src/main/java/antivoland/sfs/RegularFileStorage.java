package antivoland.sfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.String.format;

class RegularFileStorage<DATA> extends FileStorage<DATA> {
    private final Class<DATA> clazz;

    RegularFileStorage(Path directory, String fileExtension, Class<DATA> clazz) {
        super(directory, fileExtension);
        this.clazz = clazz;
    }

    @Override
    public DATA load(String id) {
        Path file = file(id);
        if (!exists(file)) return null;
        try (InputStream in = Files.newInputStream(file)) {
            return DataIO.read(in, clazz);
        } catch (IOException e) {
            throw new StorageException(format("Failed to load file '%s'", file), e);
        }
    }

    @Override
    public void save(String id, DATA data) {
        var file = file(id, true);
        try (OutputStream out = Files.newOutputStream(file)) {
            DataIO.write(out, data);
        } catch (IOException e) {
            throw new StorageException(format("Failed to save file '%s'", file), e);
        }
    }
}