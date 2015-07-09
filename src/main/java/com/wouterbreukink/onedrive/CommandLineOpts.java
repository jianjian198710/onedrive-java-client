package com.wouterbreukink.onedrive;

import org.apache.commons.cli.*;

public class CommandLineOpts {

    private final Options opts;
    private final String[] args;

    private String direction;
    private String localPath;
    private String remotePath;
    private boolean help = false;
    private int threads = 5;

    public CommandLineOpts(String[] args) {
        this.opts = buildOptions();
        this.args = args;
    }

    public boolean parse() {

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine line = parser.parse(opts, args);

            this.help = line.hasOption("help");

            if (line.hasOption("local")) {
                this.localPath = line.getOptionValue("local");
            }

            if (line.hasOption("remote")) {
                this.remotePath = line.getOptionValue("remote");
            }

            if (line.hasOption("direction")) {
                this.direction = line.getOptionValue("direction");
            }

            if (line.hasOption("threads")) {
                this.threads = Integer.parseInt(line.getOptionValue("threads"));
            }

            //TODO: Parse other command line options here

            return true;
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    private Options buildOptions() {
        Option hash = Option.builder("c")
                .longOpt("hash-compare")
                .desc("compare files by hash")
                .build();

        Option direction = Option.builder()
                .longOpt("direction")
                .hasArg()
                .argName("up|down|sync")
                .desc("direction of synchronisation.")
                .required()
                .build();

        Option help = Option.builder("h")
                .longOpt("help")
                .desc("print this message")
                .build();

        Option keyFile = Option.builder("k")
                .longOpt("keyfile")
                .hasArg()
                .argName("file")
                .desc("key file to use")
                .build();

        Option logLevel = Option.builder("L")
                .longOpt("log-level")
                .hasArg()
                .argName("level (1-7)")
                .desc("controls the verbosity of logging")
                .build();

        Option localPath = Option.builder()
                .longOpt("local")
                .hasArg()
                .argName("path")
                .desc("the local path")
                .required()
                .build();

        Option logFile = Option.builder()
                .longOpt("logfile")
                .hasArg()
                .argName("file")
                .desc("log to file")
                .build();

        Option maxSize = Option.builder("M")
                .longOpt("max-size")
                .hasArg()
                .argName("size")
                .desc("only process files smaller than <size>")
                .build();

        Option minSize = Option.builder("m")
                .longOpt("max-size")
                .hasArg()
                .argName("size")
                .desc("only process files larger than <size>")
                .build();

        Option dryRun = Option.builder("n")
                .longOpt("dry-run")
                .desc("only do a dry run without making changes")
                .build();

        Option recursive = Option.builder("r")
                .longOpt("recursive")
                .desc("recurse into directories")
                .build();

        Option remotePath = Option.builder()
                .longOpt("remote")
                .hasArg()
                .argName("path")
                .desc("the remote path on OneDrive")
                .required()
                .build();

        Option threads = Option.builder("t")
                .longOpt("threads")
                .hasArg()
                .argName("count")
                .desc("number of threads to use")
                .build();

        Option version = Option.builder("v")
                .longOpt("version")
                .desc("print the version information and exit")
                .build();

        Option conflict = Option.builder("x")
                .longOpt("conflict")
                .hasArg()
                .argName("L|R|B|S")
                .desc("conflict resolution by Local file, Remote file, Both files or Skipping the file.")
                .build();

        Option retries = Option.builder("y")
                .longOpt("retries")
                .hasArg()
                .argName("count")
                .desc("retry each service request <count> times")
                .build();

        return new Options()
                .addOption(hash)
                .addOption(direction)
                .addOption(help)
                .addOption(keyFile)
                .addOption(logLevel)
                .addOption(localPath)
                .addOption(logFile)
                .addOption(maxSize)
                .addOption(minSize)
                .addOption(dryRun)
                .addOption(recursive)
                .addOption(remotePath)
                .addOption(threads)
                .addOption(version)
                .addOption(conflict)
                .addOption(retries);
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("onedrive-java-syncer", opts);
    }

    public String getDirection() {
        return direction;
    }

    public String getLocalPath() {
        return localPath;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public boolean isHelp() {
        return help;
    }

    public int getThreads() {
        return threads;
    }
}