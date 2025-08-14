package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

public class NumberHandler extends AbstractHandler {
    private BigDecimal max = null;
    private BigDecimal min = null;
    private BigDecimal sum = BigDecimal.ZERO;
    private long count = 0L;
    private boolean isFirstEntry = true;


    public NumberHandler(DataType type, Config config) {
        super(type, config);
    }

    @Override
    public void process(String line) {
        //в рамках задачи BigDecimal на целых числах работает аналогично BigInteger
        //поэтому обработка всех видов чисел объединена в одном методе
        BigDecimal parsed = new BigDecimal(line);
        if (isFirstEntry) {
            initializePrintWriter(path);
            max = parsed;
            min = parsed;
            isFirstEntry = false;
        }
        printToFile(line);
        count++;
        sum = sum.add(parsed);
        if (parsed.compareTo(max) > 0) { max = parsed; }
        if (parsed.compareTo(min) < 0) { min = parsed; }
    }

    public BigDecimal getAverage() {
        return sum.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP);
    }

    public void printNumberStatistic() {
        if (this.count == 0) return;

        if (this.statisticMode == StatisticMode.SHORT) {
            System.out.printf(Locale.US, "%d elements were written to %s%s\n",
                    this.count,
                    this.prefix,
                    this.fileName);
        }

        if (this.statisticMode == StatisticMode.FULL) {
            System.out.printf(Locale.US, "%d elements were written to %s%s, min is %s, max is %s, sum is %s, average is %s.\n",
                    this.count,
                    this.prefix,
                    this.fileName,
                    this.min.toPlainString(),
                    this.max.toPlainString(),
                    this.sum.toPlainString(),
                    this.getAverage().toPlainString());
        }
    }
}
