package org.example;

import java.util.ArrayList;
import java.util.List;

public class ConfigReader {

    public static Config readConfig(String[] args) throws RuntimeException  {

        List<String> fileNames = new ArrayList<>();
        boolean isAppend = false;
        boolean firstNewPath = true;
        boolean firstPrefix = true;
        boolean firstAppend = true;
        String newPath = "";
        String prefix = "";
        StatisticMode statisticMode = null;

        if (args.length == 0) {
            throw new IllegalParameterException("Parameters not provided");
        }

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-s":
                    if (statisticMode == null) {
                        statisticMode = StatisticMode.SHORT;
                    } else {
                        throw new IllegalParameterException("Incorrect arguments: both types of statistics are chosen. Exiting program.");
                    }
                    break;
                case "-f":
                    if (statisticMode == null) {
                        statisticMode = StatisticMode.FULL;
                    } else {
                        throw new IllegalParameterException("Incorrect arguments: both types of statistics are chosen. Exiting program.");
                    }
                    break;
                case "-a":
                    if (firstAppend) {
                        isAppend = true;
                        firstAppend = false;
                    } else {
                        throw new IllegalParameterException("Multiple append parameters are provided. Exiting program.");
                    }
                    break;
                case "-o":
                    if (firstNewPath) {
                        if (i + 1 >= args.length || args[i + 1].length() <= 2) {
                            throw new IllegalParameterException("Parameter \"-o\" is used, but no new path provided, or path is incorrect. Exiting program.");
                        } else {
                            newPath = args[i + 1];
                            firstNewPath = false;
                            i++;
                        }
                    } else {
                        throw new IllegalParameterException("Multiple new path parameters are provided. Exiting program.");
                    }
                    break;
                case "-p":
                    if (firstPrefix) {
                        if (i + 1 >= args.length || args[i + 1].startsWith("-") || args[i + 1].endsWith(".txt")) {
                            throw new IllegalParameterException("Parameter \"-p\" is used, but no prefix provided, or path is incorrect. Exiting program.");
                        } else {
                            prefix = args[i + 1];
                            firstPrefix = false;
                            i++;
                        }
                    } else {
                        throw new IllegalParameterException("Multiple prefix parameters are provided. Exiting program.");
                    }
                    break;
                default:
                    fileNames.add(args[i]);
            }
        }
        return new Config(fileNames, isAppend, newPath, prefix, statisticMode);
    }
}
