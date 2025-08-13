package org.example;

import java.io.*;
import java.nio.file.Path;

public abstract class AbstractHandler implements Closeable {
    protected boolean configAppend;
    protected StatisticMode statisticMode;
    protected String prefix;
    protected Path path = null;
    protected PrintWriter writer = null;

    public abstract void process(String line);

    protected AbstractHandler(DataType type, Config config) {
        if (type == DataType.INTEGER) {
            path = Path.of(config.getNewPath() + config.getPrefix() + "integers.txt");
        } else if (type == DataType.FLOAT) {
            path = Path.of(config.getNewPath() + config.getPrefix() + "float.txt");
        } else if (type == DataType.STRING) {
            path = Path.of(config.getNewPath() + config.getPrefix() + "strings.txt");
        } else {
            System.out.println("Unknown data type");
        }
        configAppend = config.isAppend();
        statisticMode = config.getStatisticMode();
        prefix = config.getPrefix();
    }

    public void initializePrintWriter(Path path) {
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(path.toFile(), configAppend)));
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            writer = null;
        }
    }

    public void printToFile(String line) {
        if (writer == null) {
            System.out.println("PrintWriter не инициализирован для " + this.getClass().getSimpleName());
            return;
        }
        writer.println(line);
    }

    @Override
    public void close() {
        if (writer != null) { writer.close(); }
    }
}
