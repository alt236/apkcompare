package uk.co.alt236.apkcompare.zip.common;

import java.util.Objects;
import java.util.zip.ZipEntry;

public class Entry {
    private final String name;
    private final boolean directory;
    private final long fileSize;

    public Entry(ZipEntry zipEntry) {
        name = zipEntry.getName();
        directory = zipEntry.isDirectory();
        fileSize = zipEntry.getSize();
    }

    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return directory;
    }

    public long getFileSize() {
        return fileSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return directory == entry.directory &&
                fileSize == entry.fileSize &&
                Objects.equals(name, entry.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, directory, fileSize);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "name='" + name + '\'' +
                ", directory=" + directory +
                ", fileSize=" + fileSize +
                '}';
    }
}