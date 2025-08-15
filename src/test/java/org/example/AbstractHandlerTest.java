package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AbstractHandlerTest {

    @TempDir
    Path tempDir;

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
        List<String> fileNames = Arrays.asList("test_in1.txt", "test_in2.txt");
        Config config = new Config(fileNames,true,tempDir.toString(), "test_", StatisticMode.FULL);
        AbstractHandler handler = new MockHandler(DataType.INTEGER, config);

        assertEquals("integers.txt", handler.fileName);
        assertEquals(tempDir.resolve("test_integers.txt"), handler.path);
        assertTrue(handler.configAppend);
        assertEquals(StatisticMode.FULL, handler.statisticMode);
        assertEquals("test_", handler.prefix);

        handler.close();
    }

    @Test
    public void initializePrintWriterTest() {
        List<String> fileNames = Arrays.asList("test_in1.txt", "test_in2.txt");
        Config config = new Config(fileNames,true,tempDir.toString(), "test_", StatisticMode.FULL);
        AbstractHandler handler = new MockHandler(DataType.INTEGER, config);

        handler.initializePrintWriter(handler.path);
        assertNotNull(handler.writer);

        handler.close();
    }

    @Test
    public void writeLineTest() throws IOException {
        List<String> fileNames = Arrays.asList("test_in1.txt", "test_in2.txt");
        Config config = new Config(fileNames,true,tempDir.toString(), "test_", StatisticMode.FULL);
        AbstractHandler handler = new MockHandler(DataType.STRING, config);
        handler.initializePrintWriter(handler.path);

        handler.printToFile("Test line");
        handler.close();

        String fileContent = Files.readString(handler.path);
        assertTrue(fileContent.contains("Test line"));
    }
}