package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class NumberHandlerTest {
    private NumberHandler handler;
    private List<String> fileNames = Arrays.asList("test_in1.txt", "test_in2.txt");

    @AfterEach
    public void closeHandler() {
        if (handler != null) {
            handler.close();
        }
    }

    public void flushHandler()  {
        if (handler.writer != null) {
            handler.writer.flush();
        }
    }

    @Test
    public void processIntegerTest(@TempDir Path tempDir) throws IOException {

        Config config = new Config(fileNames,true,tempDir.toString(), "test_", StatisticMode.FULL);
        handler = new NumberHandler(DataType.INTEGER, config);

        handler.process("10");
        handler.process("20");
        handler.process("30");
        flushHandler();

        Path outputFile = tempDir.resolve("test_integers.txt");
        assertTrue(Files.exists(outputFile));

        List<String> expectedLines = List.of("10", "20", "30");
        List<String> actualLines = Files.readAllLines(tempDir.resolve("test_integers.txt"));
        assertLinesMatch(expectedLines, actualLines);

        assertEquals(new BigDecimal("10"), handler.getMin());
        assertEquals(new BigDecimal("30"), handler.getMax());
        assertEquals(new BigDecimal("60"), handler.getSum());
        assertEquals(3, handler.getCount());
        assertEquals(new BigDecimal("20"), handler.getAverage());
    }

    @Test
    public void processFloatTest(@TempDir Path tempDir) throws IOException {
        Config config = new Config(fileNames,true,tempDir.toString(), "test_", StatisticMode.FULL);
        handler = new NumberHandler(DataType.FLOAT, config);

        handler.process("1.5");
        handler.process("2.5");
        handler.process("3.5");
        flushHandler();

        Path outputFile = tempDir.resolve("test_floats.txt");
        assertTrue(Files.exists(outputFile));

        List<String> expectedLines = List.of("1.5", "2.5", "3.5");
        List<String> actualLines = Files.readAllLines(tempDir.resolve("test_floats.txt"));
        assertLinesMatch(expectedLines, actualLines);


        assertEquals(new BigDecimal("1.5"), handler.getMin());
        assertEquals(new BigDecimal("3.5"), handler.getMax());
        assertEquals(new BigDecimal("7.5"), handler.getSum());
        assertEquals(3, handler.getCount());
        assertEquals(new BigDecimal("2.5"), handler.getAverage());
    }

    @Test
    public void emptyInputTest(@TempDir Path tempDir)    {
        Config config = new Config(List.of(),true,tempDir.toString(), "test_", StatisticMode.FULL);
        handler = new NumberHandler(DataType.INTEGER, config);


        assertNull(handler.getMin());
        assertNull(handler.getMax());
        assertEquals(BigDecimal.ZERO, handler.getSum());
        assertEquals(0, handler.getCount());
    }

    @Test
    public void appendModeTest(@TempDir Path tempDir) throws IOException {
        Files.writeString(tempDir.resolve("test_integers.txt"), "created\n");

        Config config = new Config(fileNames,true,tempDir.toString(), "test_", StatisticMode.SHORT);
        handler = new NumberHandler(DataType.INTEGER, config);

        handler.process("10");
        flushHandler();

        List<String> expectedLines = List.of("created", "10");
        List<String> actualLines = Files.readAllLines(tempDir.resolve("test_integers.txt"));
        assertLinesMatch(expectedLines, actualLines);
    }

    @Test
    public void shortStatisticTest(@TempDir Path tempDir) {
        Config config = new Config(fileNames,true,tempDir.toString(), "test_", StatisticMode.SHORT);
        handler = new NumberHandler(DataType.INTEGER, config);

        handler.process("10");
        handler.process("20");
        handler.process("30");
        flushHandler();

        ByteArrayOutputStream printStatistic = new ByteArrayOutputStream();
        System.setOut(new PrintStream(printStatistic));

        handler.printNumberStatistic();

        assertEquals("3 elements were written to test_integers.txt\n", printStatistic.toString());
    }

    @Test
    public void fullStatisticTest(@TempDir Path tempDir) {
        Config config = new Config(fileNames,true,tempDir.toString(), "test_", StatisticMode.FULL);
        handler = new NumberHandler(DataType.INTEGER, config);

        handler.process("10");
        handler.process("20");
        handler.process("30");
        flushHandler();

        ByteArrayOutputStream printStatistic = new ByteArrayOutputStream();
        System.setOut(new PrintStream(printStatistic));

        handler.printNumberStatistic();

        assertEquals("3 elements were written to test_integers.txt, min is 10, max is 30, sum is 60, average is 20.\n", printStatistic.toString());
    }
}