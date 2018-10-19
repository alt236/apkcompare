package uk.co.alt236.apkcompare.cli;

import org.apache.commons.cli.CommandLine;

public class CommandLineOptions {

    private final CommandLine commandLine;

    public CommandLineOptions(final CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public boolean isVerbose() {
        return commandLine.hasOption(OptionsBuilder.ARG_VERBOSE_LONG);
    }

    public boolean isHumanReadableFileSizes() {
        return commandLine.hasOption(OptionsBuilder.ARG_HUMAN_READABLE_SIZES_LONG);
    }

    public String getInputFile1() {
        return commandLine.getOptionValue(OptionsBuilder.ARG_FILE_1_LONG);
    }

    public String getInputFile2() {
        return commandLine.getOptionValue(OptionsBuilder.ARG_FILE_2_LONG);
    }

    public String getOutputFile() {
        return commandLine.getOptionValue(OptionsBuilder.ARG_OUTPUT_LONG);
    }

}
