package uk.co.alt236.apkcompare.output.writer;

public interface Writer {
    void outln(String string);

    void errln(String string);

    void close();
}
