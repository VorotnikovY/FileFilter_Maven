package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractHandlerTest {
    private final List<String> fileNames = Arrays.asList("test_in1.txt", "test_in2.txt");
    private AbstractHandler handler;

    @TempDir
    Path tempDir;

    @AfterEach
    public void closeHandler() {
        if (handler != null) {
            handler.close();
        }
    }

    public void flushHandler() {
        if (handler.writer != null) {
            handler.writer.flush();
        }
    }

    public static class MockHandler extends AbstractHandler {
        public MockHandler(DataType type, Config config) {
            super(type, config);
        }

        @Override
        public void process(String line) {
        }
    }

    @Test
    public void constructorTest() {
        Config config = new Config(fileNames, true, tempDir.toString(), "test_", StatisticMode.FULL);
        handler = new MockHandler(DataType.INTEGER, config);

        assertEquals("integers.txt", handler.fileName);
        assertEquals(tempDir.resolve("test_integers.txt"), handler.path);
        assertTrue(handler.configAppend);
        assertEquals(StatisticMode.FULL, handler.statisticMode);
        assertEquals("test_", handler.prefix);
    }

    @Test
    public void initializePrintWriterTest() {
        Config config = new Config(fileNames, true, tempDir.toString(), "test_", StatisticMode.FULL);
        handler = new MockHandler(DataType.INTEGER, config);

        handler.initializePrintWriter(handler.path);
        assertNotNull(handler.writer);
    }

    @Test
    public void writeLineTest() throws IOException {
        Config config = new Config(fileNames, true, tempDir.toString(), "test_", StatisticMode.FULL);
        handler = new MockHandler(DataType.STRING, config);
        handler.initializePrintWriter(handler.path);

        handler.printToFile("Test line");
        flushHandler();

        String fileContent = Files.readString(handler.path);
        assertTrue(fileContent.contains("Test line"));
    }
}