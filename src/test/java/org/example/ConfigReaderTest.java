package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ConfigReaderTest {
    @Test
    public void firstTest() {

        String[] args = {"-f", "-a", "-p", "sample-",  "in1.txt", "in2.txt"};
        Config config = ConfigReader.readConfig(args);

        assertEquals(StatisticMode.FULL, config.getStatisticMode());
        assertTrue(config.isAppend());
        assertEquals(args[3], config.getPrefix());
        assertEquals(args[4], config.getFileNames().get(0));
        assertEquals(args[5], config.getFileNames().get(1));
    }


}