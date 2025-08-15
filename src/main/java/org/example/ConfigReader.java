package org.example;

import java.util.ArrayList;
import java.util.List;

public class ConfigReader {
    public static final String MULTIPLE_STATISTIC_PARAMETERS_ERROR_MESSAGE = "Incorrect arguments: both types of statistics are chosen. Exiting program.";
    public static final String NO_PARAMETERS_ERROR_MESSAGE = "Parameters not provided";
    public static final String MULTIPLE_APPEND_ERROR_MESSAGE = "Multiple append parameters are provided. Exiting program.";
    public static final String MULTIPLE_NEW_PATH_ERROR_MESSAGE = "Multiple new path parameters are provided. Exiting program.";
    public static final String MULTIPLE_PREFIX_ERROR_MESSAGE = "Multiple prefix parameters are provided. Exiting program.";
    public static final String NO_NEW_PATH_ERROR_MESSAGE = "Parameter \"-o\" is used, but no new path provided, or path is incorrect. Exiting program.";
    public static final String NO_PREFIX_ERROR_MESSAGE = "Parameter \"-p\" is used, but no prefix provided, or path is incorrect. Exiting program.";
    public static final String NO_FILENAMES_ERROR_MESSAGE = "No filenames provided. Exiting program.";


    public static Config readConfig(String[] args) throws RuntimeException  {


        List<String> fileNames = new ArrayList<>();
        boolean isAppend = false;
        boolean firstAppend = true;
        String newPath = "";
        String prefix = "";
        StatisticMode statisticMode = null;

        if (args.length == 0) {
            throw new IllegalParameterException(NO_PARAMETERS_ERROR_MESSAGE);
        }

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-s":
                    if (statisticMode == null) {
                        statisticMode = StatisticMode.SHORT;
                    } else {
                        throw new IllegalParameterException(MULTIPLE_STATISTIC_PARAMETERS_ERROR_MESSAGE);
                    }
                    break;
                case "-f":
                    if (statisticMode == null) {
                        statisticMode = StatisticMode.FULL;
                    } else {
                        throw new IllegalParameterException(MULTIPLE_STATISTIC_PARAMETERS_ERROR_MESSAGE);
                    }
                    break;
                case "-a":
                    if (firstAppend) {
                        isAppend = true;
                        firstAppend = false;
                    } else {
                        throw new IllegalParameterException(MULTIPLE_APPEND_ERROR_MESSAGE);
                    }
                    break;
                case "-o":
                    if (newPath.isEmpty()) {
                        if (i + 1 >= args.length || args[i + 1].length() <= 2) {
                            throw new IllegalParameterException(NO_NEW_PATH_ERROR_MESSAGE);
                        } else {
                            newPath = args[i + 1];
                            i++;
                        }
                    } else {
                        throw new IllegalParameterException(MULTIPLE_NEW_PATH_ERROR_MESSAGE);
                    }
                    break;
                case "-p":
                    if (prefix.isEmpty()) {
                        if (i + 1 >= args.length || args[i + 1].startsWith("-") || args[i + 1].endsWith(".txt")) {
                            throw new IllegalParameterException(NO_PREFIX_ERROR_MESSAGE);
                        } else {
                            prefix = args[i + 1];
                            i++;
                        }
                    } else {
                        throw new IllegalParameterException(MULTIPLE_PREFIX_ERROR_MESSAGE);
                    }
                    break;
                default:
                    fileNames.add(args[i]);
            }
        }
        if (fileNames.isEmpty())  {
            throw new IllegalParameterException(NO_FILENAMES_ERROR_MESSAGE);
        }
        return new Config(fileNames, isAppend, newPath, prefix, statisticMode);
    }
}
