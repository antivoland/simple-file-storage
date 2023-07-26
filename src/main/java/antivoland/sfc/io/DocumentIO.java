package antivoland.sfc.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class DocumentIO<DATA> implements IO<DATA> {
    private static final ObjectMapper MAPPER = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    private final Class<DATA> clazz;

    DocumentIO(Class<DATA> clazz) {
        this.clazz = clazz;
    }

    @Override
    public DATA read(InputStream i) throws IOException {
        return MAPPER.readValue(i, clazz);
    }

    @Override
    public void write(OutputStream o, DATA data) throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(o, data);
    }
}