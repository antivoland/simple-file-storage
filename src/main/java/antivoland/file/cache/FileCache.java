package antivoland.file.cache;

import java.util.stream.Stream;

public interface FileCache<DATA> {
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
}