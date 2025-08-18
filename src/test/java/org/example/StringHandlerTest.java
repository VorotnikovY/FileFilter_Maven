package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringHandlerTest extends AbstractHandlerTest {
    private StringHandler handler;
    private final List<String> fileNames = Arrays.asList("test_in1.txt", "test_in2.txt");

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
    void testProcessStrings(@TempDir Path tempDir) throws IOException {
        Config config = new Config(fileNames,true,tempDir.toString(), "test_", StatisticMode.FULL);
        handler = new StringHandler(DataType.STRING, config);

        handler.process("first");
        handler.process("banana");
        handler.process("cat");
        flushHandler();

        Path outputFile = tempDir.resolve("test_strings.txt");
        assertTrue(Files.exists(outputFile));

        List<String> expectedLines = List.of("first", "banana", "cat");
        List<String> actualLines = Files.readAllLines(outputFile);
        assertLinesMatch(expectedLines, actualLines);

        assertEquals(3, handler.getCount());
        assertEquals(3, handler.getShortString());
        assertEquals(6, handler.getLongString());
    }

    @Test
    public void emptyInputTest(@TempDir Path tempDir)    {
        Config config = new Config(List.of(),true,tempDir.toString(), "test_", StatisticMode.FULL);
        handler = new StringHandler(DataType.STRING, config);


        assertEquals(0, handler.getCount());
        assertEquals(Long.MAX_VALUE, handler.getShortString());
        assertEquals(0, handler.getLongString());
    }

    @Test
    public void appendModeTest(@TempDir Path tempDir) throws IOException {
        Files.writeString(tempDir.resolve("test_strings.txt"), "created\n");

        Config config = new Config(fileNames,true,tempDir.toString(), "test_", StatisticMode.SHORT);
        handler = new StringHandler(DataType.STRING, config);

        handler.process("new");
        flushHandler();

        List<String> expectedLines = List.of("created", "new");
        List<String> actualLines = Files.readAllLines(tempDir.resolve("test_strings.txt"));
        assertLinesMatch(expectedLines, actualLines);
    }

    @Test
    public void shortStatisticTest(@TempDir Path tempDir) {
        Config config = new Config(fileNames,true,tempDir.toString(), "test_", StatisticMode.SHORT);
        handler = new StringHandler(DataType.STRING, config);

        handler.process("first");
        handler.process("banana");
        handler.process("cat");
        flushHandler();

        ByteArrayOutputStream printStatistic = new ByteArrayOutputStream();
        System.setOut(new PrintStream(printStatistic));

        handler.printStringStatistic();

        assertEquals("3 elements were written to test_strings.txt.\n", printStatistic.toString());
    }

    @Test
    public void fullStatisticTest(@TempDir Path tempDir) {
        Config config = new Config(fileNames,true,tempDir.toString(), "test_", StatisticMode.FULL);
        handler = new StringHandler(DataType.STRING, config);

        handler.process("first");
        handler.process("banana");
        handler.process("cat");
        flushHandler();

        ByteArrayOutputStream printStatistic = new ByteArrayOutputStream();
        System.setOut(new PrintStream(printStatistic));

        handler.printStringStatistic();

        assertEquals("3 elements were written to test_strings.txt, the size of the shortest line is 3, the size of the longest line is 6.\n", printStatistic.toString());
    }

    @Test
    public void specialCharTest(@TempDir Path tempDir) throws IOException {
        Config config = new Config(List.of(),true,tempDir.toString(), "test_", StatisticMode.FULL);
        handler = new StringHandler(DataType.STRING, config);

        String testString = "спецсимволы: \t\n\\\"'";
        handler.process(testString);
        flushHandler();

        String content = Files.readString(tempDir.resolve("test_strings.txt"));
        assertTrue(content.contains(testString));
    }
}