package uk.co.alt236.apkcompare.app.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import uk.co.alt236.apkcompare.app.resources.Strings;

public class OptionsBuilder {

    /*package*/ static final String ARG_FILE_1_LONG = "file1";
    /*package*/ static final String ARG_FILE_2_LONG = "file2";

    /*package*/ static final String ARG_OUTPUT = "o";
    /*package*/ static final String ARG_OUTPUT_LONG = "output";

    /*package*/ static final String ARG_VERBOSE = "v";
    /*package*/ static final String ARG_VERBOSE_LONG = "verbose";

    /*package*/ static final String ARG_HUMAN_READABLE_SIZES = "h";
    /*package*/ static final String ARG_HUMAN_READABLE_SIZES_LONG = "human";

    /*package*/ static final String ARG_PRETTY_HTML = "pretty-html";

    private final Strings strings;

    public OptionsBuilder(Strings strings) {
        this.strings = strings;
    }

    public Options compileOptions() {
        final Options options = new Options();

        options.addOption(createOptionFile1());
        options.addOption(createOptionFile2());
        options.addOption(createOptionOutput());
        options.addOption(createOptionVerbose());
        options.addOption(createOptionHumanReadableSizes());
        options.addOption(createOptionUglyHtml());

        return options;
    }

    private Option createOptionFile1() {
        final String desc = strings.getString("cli_cmd_file1");
        return Option.builder()
                .longOpt(ARG_FILE_1_LONG)
                .hasArg()
                .required(true)
                .desc(desc)
                .build();
    }

    private Option createOptionFile2() {
        final String desc = strings.getString("cli_cmd_file2");
        return Option.builder()
                .longOpt(ARG_FILE_2_LONG)
                .hasArg()
                .required(true)
                .desc(desc)
                .build();
    }

    private Option createOptionOutput() {
        final String desc = strings.getString("cli_cmd_output");
        return Option.builder(ARG_OUTPUT)
                .longOpt(ARG_OUTPUT_LONG)
                .hasArg()
                .required(false)
                .desc(desc)
                .build();
    }

    private Option createOptionVerbose() {
        final String desc = strings.getString("cli_cmd_input_verbose");
        return Option.builder(ARG_VERBOSE)
                .longOpt(ARG_VERBOSE_LONG)
                .hasArg(false)
                .required(false)
                .desc(desc)
                .build();
    }

    private Option createOptionHumanReadableSizes() {
        final String desc = strings.getString("cli_cmd_human_readable_sizes");
        return Option.builder(ARG_HUMAN_READABLE_SIZES)
                .longOpt(ARG_HUMAN_READABLE_SIZES_LONG)
                .hasArg(false)
                .required(false)
                .desc(desc)
                .build();
    }

    private Option createOptionUglyHtml() {
        final String desc = strings.getString("cli_cmd_pretty_html");
        return Option.builder()
                .longOpt(ARG_PRETTY_HTML)
                .hasArg(false)
                .required(false)
                .desc(desc)
                .build();
    }
}
