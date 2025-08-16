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
        Throwable thrown = assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args), ConfigReader.NO_PREFIX_ERROR_MESSAGE);
        assertEquals(ConfigReader.NO_PREFIX_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void noPathProvided()    {
        String[] args = {"-f", "-a", "-o", "-p", "sample-", "in1.txt", "in2.txt"};
        Throwable thrown = assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args));
        assertEquals(ConfigReader.NO_NEW_PATH_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void multipleStatParams()    {
        String[] args = {"-f", "-a", "-s", "-p", "sample-", "in1.txt", "in2.txt"};
        Throwable thrown = assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args));
        assertEquals(ConfigReader.MULTIPLE_STATISTIC_PARAMETERS_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void multipleAppendParams()    {
        String[] args = {"-f", "-a", "-a", "-p", "sample-", "in1.txt", "in2.txt"};
        Throwable thrown = assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args));
        assertEquals(ConfigReader.MULTIPLE_APPEND_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void multiplePathParams()    {
        String[] args = {"-f", "-a", "-o", "D:/Work", "-o", "-p", "sample-", "in1.txt", "in2.txt"};
        Throwable thrown = assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args));
        assertEquals(ConfigReader.MULTIPLE_NEW_PATH_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void multiplePrefixParams()    {
        String[] args = {"-f", "-a", "-p", "sample-", "-p", "in1.txt", "in2.txt"};
        Throwable thrown = assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args));
        assertEquals(ConfigReader.MULTIPLE_PREFIX_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void noParams()    {
        String[] args = new String[0];
        Throwable thrown = assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args));
        assertEquals(ConfigReader.NO_PARAMETERS_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void noFiles()    {
        String[] args = {"-f", "-a", "-p", "sample-", "-o", "D:/Work"};
        Throwable thrown = assertThrows(IllegalParameterException.class, () -> ConfigReader.readConfig(args));
        assertEquals(ConfigReader.NO_FILENAMES_ERROR_MESSAGE, thrown.getMessage());
    }
}