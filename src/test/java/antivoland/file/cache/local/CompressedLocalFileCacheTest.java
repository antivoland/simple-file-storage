package antivoland.file.cache.local;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedLocalFileCacheTest {
    @Test
    void testTexts(@TempDir Path directory) {
        var cache = LocalFileCache.compressed(
                directory.resolve("compressed").resolve("texts"),
                FileType.text("txt"));

        assertThat(directory.resolve("compressed").resolve("texts")).doesNotExist();
        LocalFileCacheTest.load(cache, Map.of(
                "Dracula Mona", """
                                  ____
                                o8%8888,
                              o88%8888888.
                             8'-    -:8888b
                            8'         8888
                           d8.-=. ,==-.:888b
                           >8 `~` :`~' d8888
                           88         ,88888
                           88b. `-~  ':88888
                           888b v=v~ .:88888
                           88888o--:':::8888
                           `88888| :::' 8888b
                           8888^^'       8888b
                          d888           ,%888b.
                         d88%            %%%8--'-.
                        /88:.__ ,       _%-' ---  -
                            '''::===..-'   =  --.  `
                        """,
                "Sinéad O'Mona", """
                                
                                .-----.
                              .'       `.
                             .'..    :. `.
                            .'           |
                            |.-=. ,==-.: |
                            | `~` :`~'   :
                            `.        , :
                             `. `-~  '::|
                              `.~==~ .::|
                                `--:'::::
                                 | :::' `.
                            _.-""'        ~-.
                          .':                `..
                         / .'               .--'-.
                        /  :.__ ,       _%-' ---  -
                            '''::===..-'   =  --.  `
                        """
        ));
        assertThat(directory.resolve("compressed").resolve("texts")).exists().isNotEmptyDirectory();

        assertThat(directory.resolve("compressed").resolve("texts").resolve("Dracula Mona.tar.gz"))
                .exists()
                .isRegularFile()
                .isNotEmptyFile();

        assertThat(cache.load("Dracula Mona"))
                .isEqualTo("""
                                  ____
                                o8%8888,
                              o88%8888888.
                             8'-    -:8888b
                            8'         8888
                           d8.-=. ,==-.:888b
                           >8 `~` :`~' d8888
                           88         ,88888
                           88b. `-~  ':88888
                           888b v=v~ .:88888
                           88888o--:':::8888
                           `88888| :::' 8888b
                           8888^^'       8888b
                          d888           ,%888b.
                         d88%            %%%8--'-.
                        /88:.__ ,       _%-' ---  -
                            '''::===..-'   =  --.  `
                        """);
        assertThat(directory.resolve("compressed").resolve("texts").resolve("Sinéad O'Mona.tar.gz"))
                .exists()
                .isRegularFile()
                .isNotEmptyFile();

        assertThat(cache.load("Sinéad O'Mona"))
                .isEqualTo("""
                                
                                .-----.
                              .'       `.
                             .'..    :. `.
                            .'           |
                            |.-=. ,==-.: |
                            | `~` :`~'   :
                            `.        , :
                             `. `-~  '::|
                              `.~==~ .::|
                                `--:'::::
                                 | :::' `.
                            _.-""'        ~-.
                          .':                `..
                         / .'               .--'-.
                        /  :.__ ,       _%-' ---  -
                            '''::===..-'   =  --.  `
                        """);
    }

    @Test
    void testDocuments(@TempDir Path directory) {
        var cache = LocalFileCache.compressed(
                directory.resolve("compressed").resolve("documents"),
                FileType.document("json", Document.class));

        assertThat(directory.resolve("compressed").resolve("json")).doesNotExist();
        LocalFileCacheTest.load(cache, Map.of(
                "Cigar smoking Mona", Document.create("""
                                   ____
                                 o8%8888,
                               o88%8888888.
                          '   8'-    -:8888b
                         :   8'         8888
                        ` ' d8.-=. ,==-.:888b
                         `  >8 `~` :`~' d8888
                        `:: 88         ,88888
                         `' 88b. `-~  ':88888
                          `eeeeeee==~ .:88888
                            88888o--:':::8888
                            `88888| :::' 8888b
                            8888^^'       8888b
                           d888           ,%888b.
                          d88%            %%%8--'-.
                         /88:.__ ,       _%-' ---  -
                             '''::===..-'   =  --.  `
                        """),
                "Capped Mona", Document.create("""
                                  .------.
                                 /        \\
                               .' /  \\     `.
                        _______|____________|
                         ""\"""8""\"""\"""\""8888
                             d8.-=. ,==-.:888b
                             >8 `~` :`~' d8888
                             88         ,88888
                             88b. `-~  ':88888
                             888b ~==~ .:88888
                             88888o--:':::8888
                             `88888| :::' 8888b
                             8888^^'       8888b
                            d888           ,%888b.
                           d88%            %%%8--'-.
                          /88:.__ ,       _%-' ---  -
                              '''::===..-'   =  --.  `
                        """)));
        assertThat(directory.resolve("compressed").resolve("documents")).exists().isNotEmptyDirectory();

        assertThat(directory.resolve("compressed").resolve("documents").resolve("Cigar smoking Mona.tar.gz"))
                .exists()
                .isRegularFile()
                .isNotEmptyFile();

        assertThat(cache.load("Cigar smoking Mona"))
                .isEqualTo(Document.create("""
                                   ____
                                 o8%8888,
                               o88%8888888.
                          '   8'-    -:8888b
                         :   8'         8888
                        ` ' d8.-=. ,==-.:888b
                         `  >8 `~` :`~' d8888
                        `:: 88         ,88888
                         `' 88b. `-~  ':88888
                          `eeeeeee==~ .:88888
                            88888o--:':::8888
                            `88888| :::' 8888b
                            8888^^'       8888b
                           d888           ,%888b.
                          d88%            %%%8--'-.
                         /88:.__ ,       _%-' ---  -
                             '''::===..-'   =  --.  `
                        """));
        assertThat(directory.resolve("compressed").resolve("documents").resolve("Capped Mona.tar.gz"))
                .exists()
                .isRegularFile()
                .isNotEmptyFile();

        assertThat(cache.load("Capped Mona"))
                .isEqualTo(Document.create("""
                                  .------.
                                 /        \\
                               .' /  \\     `.
                        _______|____________|
                         ""\"""8""\"""\"""\""8888
                             d8.-=. ,==-.:888b
                             >8 `~` :`~' d8888
                             88         ,88888
                             88b. `-~  ':88888
                             888b ~==~ .:88888
                             88888o--:':::8888
                             `88888| :::' 8888b
                             8888^^'       8888b
                            d888           ,%888b.
                           d88%            %%%8--'-.
                          /88:.__ ,       _%-' ---  -
                              '''::===..-'   =  --.  `
                        """));
    }
}