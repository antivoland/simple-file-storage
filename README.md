# Simple file cache

[![build](https://github.com/antivoland/simple-file-storage/workflows/build/badge.svg)](https://github.com/antivoland/simple-file-storage/actions/workflows/build.yaml)
[![publish](https://github.com/antivoland/simple-file-storage/workflows/publish/badge.svg)](https://github.com/antivoland/simple-file-storage/actions/workflows/publish.yaml)

Java library for easy management of local file cache. Best used with any kind of HTML scrapers. Check out the tests for a better understanding. Use the following Maven dependency:

```xml
<dependency>
    <groupId>io.github.antivoland</groupId>
    <artifactId>simple-file-cache</artifactId>
    <version>0.1.1-SNAPSHOT</version>
</dependency>
```

Currently only snapshots are available, so you will need to add the repository to your project as follows:

```xml
<repositories>
    <repository>
        <id>ossrh</id>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
</repositories>
```