package uk.co.alt236.apkcompare;

import uk.co.alt236.apkcompare.apk.Apk;
import uk.co.alt236.apkcompare.cli.CommandLineOptions;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.dex.DexComparator;
import uk.co.alt236.apkcompare.comparators.file.FileComparator;
import uk.co.alt236.apkcompare.comparators.file.FileContentsComparator;
import uk.co.alt236.apkcompare.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.comparators.signature.SignatureComparator;
import uk.co.alt236.apkcompare.output.console.ConsoleResultsPrinter;
import uk.co.alt236.apkcompare.output.html.HtmlResultsPrinter;
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
        final List<ApkComparator> comparatorList = new ArrayList<>();
        setupLogger(null);


        comparatorList.add(new FileComparator());
        comparatorList.add(new SignatureComparator());
        comparatorList.add(new FileContentsComparator());
        comparatorList.add(new DexComparator());

        Logger.get().out("* APK 1: " + apk1.getFile());
        Logger.get().out("* APK 2: " + apk2.getFile());

        final List<ComparisonResult> results = new ArrayList<>();

        for (final ApkComparator comparator : comparatorList) {
            results.addAll(comparator.compare(apk1, apk2));
        }

        apk1.close();
        apk2.close();

        new ConsoleResultsPrinter(fileSizeFormatter, colorizer, verbose).print(results);

        if (isSaveToFileEnabled) {
            final OutputFileNameFactory fileNameFactory = new OutputFileNameFactory();
            final File htmlFile = new File(cli.getOutputFile(), fileNameFactory.getFileNameForHtmlReport(apk1, apk2));

            //noinspection ResultOfMethodCallIgnored
            htmlFile.getParentFile().mkdirs();

            Logger.get().out("* Will save report as " + htmlFile);
            new HtmlResultsPrinter(fileSizeFormatter, verbose).print(results, htmlFile);
        }

        Logger.get().out("--- DONE ---");
    }

    @SuppressWarnings("SameParameterValue")
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
