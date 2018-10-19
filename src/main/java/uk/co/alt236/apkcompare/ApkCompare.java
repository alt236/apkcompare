package uk.co.alt236.apkcompare;

import uk.co.alt236.apkcompare.cli.CommandLineOptions;
import uk.co.alt236.apkcompare.comparators.ApkComparator;
import uk.co.alt236.apkcompare.comparators.FileComparator;
import uk.co.alt236.apkcompare.comparators.SignatureComparator;
import uk.co.alt236.apkcompare.output.logging.Logger;
import uk.co.alt236.apkcompare.output.writer.FileWriter;
import uk.co.alt236.apkcompare.util.Colorizer;
import uk.co.alt236.apkcompare.util.FileSizeFormatter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ApkCompare {
    public void compare(CommandLineOptions cli,
                        File file1,
                        File file2) {

        final boolean isSaveToFileEnabled = cli.getOutputFile() != null;
        final boolean verbose = cli.isVerbose();
        final boolean humanReadableFileSizes = cli.isHumanReadableFileSizes();
        final FileSizeFormatter fileSizeFormatter = new FileSizeFormatter(humanReadableFileSizes);
        final Colorizer colorizer = new Colorizer(!isSaveToFileEnabled);

        if (isSaveToFileEnabled) {
            setupLogger(new File(cli.getOutputFile()));
        }

        final List<ApkComparator> comparatorList = new ArrayList<>();

        comparatorList.add(new FileComparator(fileSizeFormatter, colorizer, verbose));
        comparatorList.add(new SignatureComparator(fileSizeFormatter, colorizer, verbose));


        Logger.get().out("File 1: " + file1);
        Logger.get().out("File 2: " + file2);

        for (final ApkComparator comparator : comparatorList) {
            comparator.compare(file1, file2);
        }

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
