package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfigReaderTest {

    @Test
    public void correctParamsTest() {

        String[] args = {"-f", "-a", "-p", "sample-", "-o", "D:/Work", "in1.txt", "in2.txt"};
        Config config = ConfigReader.readConfig(args);

        assertEquals(StatisticMode.FULL, config.getStatisticMode());
        assertTrue(config.isAppend());
        assertEquals(args[3], config.getPrefix());
        assertEquals(args[5], config.getNewPath());
        assertEquals(args[6], config.getFileNames().get(0));
        assertEquals(args[7], config.getFileNames().get(1));
    }

    @Test
    public void noPrefixProvided()    {
        String[] args = {"-f", "-a", "-p", "in1.txt", "in2.txt"};
        assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args), "Parameter \"-p\" is used, but no prefix provided, or path is incorrect. Exiting program.");
    }

    @Test
    public void noPathProvided()    {
        String[] args = {"-f", "-a", "-o", "-p", "sample-", "in1.txt", "in2.txt"};
        assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args), "Parameter \"-o\" is used, but no new path provided, or path is incorrect. Exiting program.");
    }

    @Test
    public void multipleStatParams()    {
        String[] args = {"-f", "-a", "-s", "-p", "sample-", "in1.txt", "in2.txt"};
        assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args), "Incorrect arguments: both types of statistics are chosen. Exiting program.");
    }

    @Test
    public void multipleAppendParams()    {
        String[] args = {"-f", "-a", "-a", "-p", "sample-", "in1.txt", "in2.txt"};
        assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args), "Multiple append parameters are provided. Exiting program.");
    }

    @Test
    public void multiplePathParams()    {
        String[] args = {"-f", "-a", "-o", "D:/Work", "-o", "-p", "sample-", "in1.txt", "in2.txt"};
        assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args), "Multiple new path parameters are provided. Exiting program.");
    }

    @Test
    public void multiplePrefixParams()    {
        String[] args = {"-f", "-a", "-p", "sample-", "-p", "in1.txt", "in2.txt"};
        assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args), "Multiple prefix parameters are provided. Exiting program.");
    }

    @Test
    public void noParams()    {
        String[] args = new String[0];
        assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args), "Parameters not provided");
    }
}