package uk.co.alt236.apk.util;

import java.io.Closeable;
import java.io.IOException;

public final class StreamUtils {
    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
