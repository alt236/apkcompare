package uk.co.alt236.apkcompare.app.output.writer;

public interface Writer {
    void outln(String string);

    void errln(String string);

    void close();
}
