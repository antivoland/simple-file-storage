package io.github.antivoland.sfs.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

class TextIO implements IO<String> {
    static final TextIO INSTANCE = new TextIO();

    private TextIO() {}

    @Override
    public String read(InputStream i) throws IOException {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int l; (l = i.read(buffer)) != -1; ) {
            o.write(buffer, 0, l);
        }
        return o.toString(StandardCharsets.UTF_8);
    }

    @Override
    public void write(OutputStream o, String text) throws IOException {
        try (Writer w = new OutputStreamWriter(o, StandardCharsets.UTF_8)) {
            w.write(text);
        }
    }
}