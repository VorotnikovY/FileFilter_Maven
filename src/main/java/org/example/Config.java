package org.example;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private List<String> fileNames = new ArrayList<>();
    private boolean isAppend = false;
    private boolean firstNewPath = true;
    private boolean firstPrefix = true;
    private boolean firstAppend = true;
    private String newPath = "";
    private String prefix = "";
    private StatisticMode statisticMode = null;

    public Config(String[] args) {
        if (args.length == 0) {
            System.out.println("Parameters not provided");
            return;
        }

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-s":
                    if (statisticMode == null) {
                        statisticMode = StatisticMode.SHORT;
                        break;
                    } else {
                        System.out.println("Incorrect arguments: both types of statistics are chosen. Exiting program.");
                        System.exit(1);
                    }
                case "-f":
                    if (statisticMode == null) {
                        statisticMode = StatisticMode.FULL;
                        break;
                    } else {
                        System.out.println("Incorrect arguments: both types of statistics are chosen. Exiting program.");
                        System.exit(1);
                    }
                case "-a":
                    if (firstAppend) {
                        isAppend = true;
                        firstAppend = false;
                        break;
                    } else {
                        System.out.println("Multiple append parameters are provided. Exiting program.");
                        System.exit(1);
                    }
                case "-o":
                    if (firstNewPath) {
                        if (i + 1 >= args.length || args[i + 1].length() <= 2) {
                            System.out.println("Parameter \"-o\" is used, but no new path provided, or path is incorrect. Exiting program.");
                            System.exit(1);
                        } else {
                            newPath = args[i + 1] + "/";
                            firstNewPath = false;
                            break;
                        }
                    } else {
                        System.out.println("Multiple new path parameters are provided. Exiting program.");
                        System.exit(1);
                    }
                case "-p":
                    if (firstPrefix) {
                        if (i + 1 >= args.length || args[i + 1].startsWith("-") || args[i + 1].endsWith(".txt")) {
                            System.out.println("Parameter \"-p\" is used, but no prefix provided, or path is incorrect. Exiting program.");
                            System.exit(1);
                        } else {
                            prefix = args[i + 1];
                            firstPrefix = false;
                            i++;
                            break;
                        }
                    } else {
                        System.out.println("Multiple prefix parameters are provided. Exiting program.");
                        System.exit(1);
                    }
                default:
                    fileNames.add(args[i]);

            }
        }
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public boolean isAppend() {
        return isAppend;
    }

    public String getNewPath() {
        return newPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public StatisticMode getStatisticMode() {
        return statisticMode;
    }
}
