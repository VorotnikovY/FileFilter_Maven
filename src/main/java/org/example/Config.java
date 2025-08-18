package org.example;

import java.util.List;

public record Config(List<String> fileNames, boolean isAppend, String newPath, String prefix,
                     StatisticMode statisticMode) {
}
