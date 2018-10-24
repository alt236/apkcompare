package uk.co.alt236.apkcompare;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.cli.CommandLineOptions;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.ResultSection;
import uk.co.alt236.apkcompare.comparators.signature.SignatureComparator;
import uk.co.alt236.apkcompare.output.ResultsPrinter;
import uk.co.alt236.apkcompare.output.logging.Logger;
import uk.co.alt236.apkcompare.output.writer.FileWriter;
import uk.co.alt236.apkcompare.util.Colorizer;
import uk.co.alt236.apkcompare.util.FileSizeFormatter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class ApkCompare {

    void compare(CommandLineOptions cli, Apk apk1, Apk apk2) {
        final boolean isSaveToFileEnabled = cli.getOutputFile() != null;
        final boolean verbose = cli.isVerbose();
        final boolean humanReadableFileSizes = cli.isHumanReadableFileSizes();
        final FileSizeFormatter fileSizeFormatter = new FileSizeFormatter(humanReadableFileSizes);
        final Colorizer colorizer = new Colorizer(!isSaveToFileEnabled);

        if (isSaveToFileEnabled) {
            setupLogger(new File(cli.getOutputFile()));
        }

        final List<ApkComparator> comparatorList = new ArrayList<>();

        //comparatorList.add(new FileComparator(fileSizeFormatter, colorizer, verbose));
        comparatorList.add(new SignatureComparator(fileSizeFormatter, colorizer, verbose));


        Logger.get().out("APK 1: " + apk1);
        Logger.get().out("APK 2: " + apk2);

        final List<ResultSection> results = new ArrayList<>();

        for (final ApkComparator comparator : comparatorList) {
            results.addAll(comparator.compare(apk1, apk2));
        }

        apk1.close();
        apk2.close();

        new ResultsPrinter(fileSizeFormatter, colorizer, verbose).print(results);
    }

    private void setupLogger(final File outputFile) {
        if (outputFile == null) {
            new Logger.Builder()
                    .build();
        } else {
            new Logger.Builder()
                    .withFileWriter(new FileWriter(outputFile))
                    .build();
        }
    }
}
