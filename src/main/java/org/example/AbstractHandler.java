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
        Path configPath = Path.of(config.newPath());
        if (type == DataType.INTEGER) {
            path = configPath.resolve(config.prefix() + "integers.txt");
            fileName = "integers.txt";
        } else if (type == DataType.FLOAT) {
            path = configPath.resolve(config.prefix() + "floats.txt");
            fileName = "float.txt";
        } else if (type == DataType.STRING) {
            path = configPath.resolve(config.prefix() + "strings.txt");
            fileName = "strings.txt";
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
