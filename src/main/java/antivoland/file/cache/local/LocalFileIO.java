package antivoland.file.cache.local;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class LocalFileIO {
    private static final ObjectMapper MAPPER = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    static <DATA> DATA read(InputStream in, Class<DATA> clazz) throws IOException {
        return MAPPER.readValue(in, clazz);
    }

    static <DATA> void write(OutputStream out, DATA data) throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(out, data);
    }
}