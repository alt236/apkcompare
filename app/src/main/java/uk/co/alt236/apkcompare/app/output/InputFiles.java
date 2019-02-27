package uk.co.alt236.apkcompare.app.output;

import uk.co.alt236.apk.Apk;

public class InputFiles {
    private final Apk apk1;
    private final Apk apk2;

    public InputFiles(Apk apk1, Apk apk2) {
        this.apk1 = apk1;
        this.apk2 = apk2;
    }

    public Apk getApk1() {
        return apk1;
    }

    public Apk getApk2() {
        return apk2;
    }
}
