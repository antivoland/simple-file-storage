package antivoland.sfs;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface Storage<DATA> {
    default long count() {
        return listIds().count();
    }

    Stream<String> listIds();

    default Stream<DATA> list() {
        return listIds().map(this::load);
    }

    boolean exists(String id);

    DATA load(String id);

    void save(String id, DATA data);

    static <DATA> Storage<DATA> regular(Path directory, String fileExtension, Class<DATA> clazz) {
        return new RegularFileStorage<>(directory, fileExtension, clazz);
    }
}