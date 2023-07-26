package antivoland.file.cache.local;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RegularLocalFileCacheTest {
    @Test
    void testTexts(@TempDir Path directory) {
        var cache = LocalFileCache.regular(
                directory.resolve("regular").resolve("texts"),
                FileType.text("txt"));

        assertThat(directory.resolve("regular").resolve("texts")).doesNotExist();
        LocalFileCacheTest.load(cache, Map.of(
                "Normal Mona", """
                                  ____
                                o8%8888,
                              o88%8888888.
                             8'-    -:8888b
                            8'         8888
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
                        """,
                "Depressed Mona", """
                                  ____
                                o8%8888,
                              o88%8888888.
                             8'-    -:8888b
                            8'         8888
                           d8.-=. ,==-.:888b
                           >8 `=` :`=' d8888
                           88         ,88888
                           88b` `--  ':88888
                           888b -==- .:88888
                           88888o--:':::8888
                           `88888| :::' 8888b
                           8888^^'       8888b
                          d888           ,%888b.
                         d88%            %%%8--'-.
                        /88:.__ ,       _%-' ---  -
                            '''::===..-'   =  --.  `
                        """
        ));
        assertThat(directory.resolve("regular").resolve("texts")).exists().isNotEmptyDirectory();

        assertThat(directory.resolve("regular").resolve("texts").resolve("Normal Mona.txt"))
                .exists()
                .isRegularFile()
                .isNotEmptyFile()
                .hasContent("""
                                  ____
                                o8%8888,
                              o88%8888888.
                             8'-    -:8888b
                            8'         8888
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
                        """);
        assertThat(directory.resolve("regular").resolve("texts").resolve("Depressed Mona.txt"))
                .exists()
                .isRegularFile()
                .isNotEmptyFile()
                .hasContent("""
                                  ____
                                o8%8888,
                              o88%8888888.
                             8'-    -:8888b
                            8'         8888
                           d8.-=. ,==-.:888b
                           >8 `=` :`=' d8888
                           88         ,88888
                           88b` `--  ':88888
                           888b -==- .:88888
                           88888o--:':::8888
                           `88888| :::' 8888b
                           8888^^'       8888b
                          d888           ,%888b.
                         d88%            %%%8--'-.
                        /88:.__ ,       _%-' ---  -
                            '''::===..-'   =  --.  `
                        """);
    }

    @Test
    void testDocuments(@TempDir Path directory) {
        var cache = LocalFileCache.regular(
                directory.resolve("regular").resolve("documents"),
                FileType.document("json", Document.class));

        assertThat(directory.resolve("regular").resolve("json")).doesNotExist();
        LocalFileCacheTest.load(cache, Map.of(
                "Cool Mona", Document.create("""
                                  ____
                                o8%8888,
                              o88%8888888.
                             8'-    -:8888b
                            8'         8888
                           d8.-=. ,==-.:888b
                           >8`88P''88P'd8888
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
                        """),
                "Pouting Mona", Document.create("""
                                  ____
                                o8%8888,
                              o88%8888888.
                             8'-    -:8888b
                            8'         8888
                           d8.-=. ,==-.:888b
                           >8 `~` :`~' d8888
                           88         ,88888
                           88b. `-~  ':88888
                           888b .==. .:88888
                           88888o--:':::8888
                           `88888| :::' 8888b
                           8888^^'       8888b
                          d888           ,%888b.
                         d88%            %%%8--'-.
                        /88:.__ ,       _%-' ---  -
                            '''::===..-'   =  --.  `
                        """)));
        assertThat(directory.resolve("regular").resolve("documents")).exists().isNotEmptyDirectory();

        assertThat(directory.resolve("regular").resolve("documents").resolve("Cool Mona.json"))
                .exists()
                .isRegularFile()
                .isNotEmptyFile()
                .hasContent("""
                        {
                          "lines" : {
                            "01" : "          ____",
                            "02" : "        o8%8888,",
                            "03" : "      o88%8888888.",
                            "04" : "     8'-    -:8888b",
                            "05" : "    8'         8888",
                            "06" : "   d8.-=. ,==-.:888b",
                            "07" : "   >8`88P''88P'd8888",
                            "08" : "   88         ,88888",
                            "09" : "   88b. `-~  ':88888",
                            "10" : "   888b ~==~ .:88888",
                            "11" : "   88888o--:':::8888",
                            "12" : "   `88888| :::' 8888b",
                            "13" : "   8888^^'       8888b",
                            "14" : "  d888           ,%888b.",
                            "15" : " d88%            %%%8--'-.",
                            "16" : "/88:.__ ,       _%-' ---  -",
                            "17" : "    '''::===..-'   =  --.  `"
                          }
                        }
                        """);
        assertThat(directory.resolve("regular").resolve("documents").resolve("Pouting Mona.json"))
                .exists()
                .isRegularFile()
                .isNotEmptyFile()
                .hasContent("""
                        {
                          "lines" : {
                            "01" : "          ____",
                            "02" : "        o8%8888,",
                            "03" : "      o88%8888888.",
                            "04" : "     8'-    -:8888b",
                            "05" : "    8'         8888",
                            "06" : "   d8.-=. ,==-.:888b",
                            "07" : "   >8 `~` :`~' d8888",
                            "08" : "   88         ,88888",
                            "09" : "   88b. `-~  ':88888",
                            "10" : "   888b .==. .:88888",
                            "11" : "   88888o--:':::8888",
                            "12" : "   `88888| :::' 8888b",
                            "13" : "   8888^^'       8888b",
                            "14" : "  d888           ,%888b.",
                            "15" : " d88%            %%%8--'-.",
                            "16" : "/88:.__ ,       _%-' ---  -",
                            "17" : "    '''::===..-'   =  --.  `"
                          }
                        }
                        """);
    }
}