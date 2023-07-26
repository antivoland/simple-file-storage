package io.github.antivoland.sfc;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;

record Document(Map<String, String> lines) {
    static Document create(String mona) {
        AtomicInteger no = new AtomicInteger(0);
        Map<String, String> lines = new TreeMap<>();
        mona.lines().forEach(line -> lines.put(format("%02d", no.incrementAndGet()), line));
        return new Document(lines);
    }
}