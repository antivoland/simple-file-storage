package antivoland.file.cache.local;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LocalFileCacheTest {
    static <DOCUMENT> void load(LocalFileCache<DOCUMENT> cache, Map<String, DOCUMENT> documents) {
        assertThat(documents.size()).isGreaterThan(0);

        assertThat(cache.count()).isEqualTo(0);
        assertThat(cache.listIds()).isEmpty();
        assertThat(cache.list()).isEmpty();

        documents.forEach((id, document) -> {
            assertThat(cache.exists(id)).isFalse();
            assertThat(cache.load(id)).isNull();
            cache.save(id, document);
            assertThat(cache.exists(id)).isTrue();
            assertThat(cache.load(id)).isEqualTo(document);
        });

        assertThat(cache.count()).isEqualTo(documents.size());
        assertThat(cache.listIds()).containsOnlyOnceElementsOf(documents.keySet());
        assertThat(cache.list()).containsOnlyOnceElementsOf(documents.values());
    }
}