package org.example;

import java.util.List;

public class Config {
    private List<String> fileNames;
    private boolean isAppend;
    private String newPath;
    private String prefix;
    private StatisticMode statisticMode;


    public Config(List<String> fileNames, boolean isAppend, String newPath, String prefix, StatisticMode statisticMode) {
        this.fileNames = fileNames;
        this.isAppend = isAppend;
        this.newPath = newPath;
        this.prefix = prefix;
        this.statisticMode = statisticMode;
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
