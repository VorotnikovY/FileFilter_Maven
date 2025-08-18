package org.example;

import java.io.*;

public class FileFilter {

    public static void main(String[] args) {
        try {
            runProgram(args);
        } catch (IllegalParameterException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            e.printStackTrace(System.err);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public static void runProgram(String[] args) throws Exception {
        Config config = ConfigReaderUtil.readConfig(args);

        try (NumberHandler integerHandler = new NumberHandler(DataType.INTEGER, config);
             NumberHandler floatHandler = new NumberHandler(DataType.FLOAT, config);
             StringHandler stringHandler = new StringHandler(DataType.STRING, config)) {

            for (String fileName : config.fileNames()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        DataType type = DataTypeUtil.defineDataType(line);
                        if (type == DataType.INTEGER) {
                            integerHandler.process(line);
                        } else if (type == DataType.FLOAT) {
                            floatHandler.process(line);
                        } else {
                            stringHandler.process(line);
                        }
                    }
                }
            }

            integerHandler.printNumberStatistic();
            floatHandler.printNumberStatistic();
            stringHandler.printStringStatistic();
        }
    }
}