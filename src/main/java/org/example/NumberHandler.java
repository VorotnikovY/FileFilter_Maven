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

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public long getCount() {
        return count;
    }

    public BigDecimal getAverage() {
        return sum.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP);
    }

    public void printNumberStatistic() {
        if (this.getCount() == 0) return;

        if (this.statisticMode == StatisticMode.SHORT) {
            System.out.printf(Locale.US, "%d elements were written to %sintegers.txt\n",
                    this.getCount(),
                    this.prefix);
        }

        if (this.statisticMode == StatisticMode.FULL) {
            System.out.printf(Locale.US, "%d elements were written to %sintegers.txt, min is %s, max is %s, sum is %s, average is %s.\n",
                    this.getCount(),
                    this.prefix,
                    this.getMin().toPlainString(),
                    this.getMax().toPlainString(),
                    this.getSum().toPlainString(),
                    this.getAverage().toPlainString());
        }
    }
}
