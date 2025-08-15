package org.example;

import java.util.Locale;

public class StringHandler extends AbstractHandler {
    private long longString = 0L;
    private long shortString = Long.MAX_VALUE;
    private long count = 0L;
    private boolean isFirstEntry = true;

    public StringHandler(DataType type, Config config) {
        super(type, config);
    }

    public long getLongString() {
        return longString;
    }

    public long getShortString() {
        return shortString;
    }

    public long getCount() {
        return count;
    }

    @Override
    public void process(String line) {
        if (isFirstEntry) {
            initializePrintWriter(path);
            isFirstEntry = false;
        }
        printToFile(line);
        count++;
        if (line.length() > longString) { longString = line.length(); }
        if (line.length() < shortString) { shortString = line.length(); }
    }

    public void printStringStatistic() {
        if (this.count == 0) return;

        if (this.statisticMode == StatisticMode.SHORT) {
            System.out.printf(Locale.US, "%d elements were written to %s%s.\n",
                    this.count,
                    this.prefix,
                    this.fileName);
        }

        if (this.statisticMode == StatisticMode.FULL) {
            System.out.printf(Locale.US, "%d elements were written to %s%s, the size of the shortest line is %d, the size of the longest line is %d.\n",
                    this.count,
                    this.prefix,
                    this.fileName,
                    this.shortString,
                    this.longString);
        }
    }
}
