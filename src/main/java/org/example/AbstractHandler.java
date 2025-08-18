package org.example;

import java.io.*;
import java.nio.file.Path;

public abstract class AbstractHandler implements Closeable {
    protected boolean configAppend;
    protected StatisticMode statisticMode;
    protected String prefix;
    protected String fileName = "";
    protected Path path = null;
    protected PrintWriter writer = null;

    public abstract void process(String line);

    protected AbstractHandler(DataType type, Config config) {
        Path configPath = Path.of(config.newPath()).toAbsolutePath();
        if (type == DataType.INTEGER) {
            fileName = "integers.txt";
            path = configPath.resolve(config.prefix() + fileName);
        } else if (type == DataType.FLOAT) {
            fileName = "floats.txt";
            path = configPath.resolve(config.prefix() + fileName);
        } else if (type == DataType.STRING) {
            fileName = "strings.txt";
            path = configPath.resolve(config.prefix() + fileName);
        } else {
            System.out.println("Unknown data type");
        }
        configAppend = config.isAppend();
        statisticMode = config.statisticMode();
        prefix = config.prefix();
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
