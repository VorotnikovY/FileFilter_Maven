package org.example;

import java.io.*;

public class FileFilter {

    public static void main(String[] args) {
        try {
            init(args);
        } catch (IllegalParameterException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void init(String[] args) throws Exception {
        Config config = ConfigReader.readConfig(args);

        try (NumberHandler integerHandler = new NumberHandler(DataType.INTEGER, config);
             NumberHandler floatHandler = new NumberHandler(DataType.FLOAT, config);
             StringHandler stringHandler = new StringHandler(DataType.STRING, config)) {

            for (String fileName : config.getFileNames()) {
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