package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfigReaderUtilTest {

    @Test
    public void correctParamsTest() {

        String[] args = {"-f", "-a", "-p", "sample-", "-o", "D:/Work", "in1.txt", "in2.txt"};
        Config config = ConfigReaderUtil.readConfig(args);

        assertEquals(StatisticMode.FULL, config.statisticMode());
        assertTrue(config.isAppend());
        assertEquals(args[3], config.prefix());
        assertEquals(args[5], config.newPath());
        assertEquals(args[6], config.fileNames().get(0));
        assertEquals(args[7], config.fileNames().get(1));
    }

    @Test
    public void noPrefixProvided()    {
        String[] args = {"-f", "-a", "-p", "in1.txt", "in2.txt"};
        IllegalParameterException thrown = assertThrows(IllegalParameterException.class, () -> ConfigReaderUtil.readConfig(args), ConfigReaderUtil.NO_PREFIX_ERROR_MESSAGE);
        assertEquals(ConfigReaderUtil.NO_PREFIX_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void noPathProvided()    {
        String[] args = {"-f", "-a", "-o", "-p", "sample-", "in1.txt", "in2.txt"};
        IllegalParameterException thrown = assertThrows(IllegalParameterException.class, () -> ConfigReaderUtil.readConfig(args));
        assertEquals(ConfigReaderUtil.NO_NEW_PATH_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void multipleStatParams()    {
        String[] args = {"-f", "-a", "-s", "-p", "sample-", "in1.txt", "in2.txt"};
        IllegalParameterException thrown = assertThrows(IllegalParameterException.class, () -> ConfigReaderUtil.readConfig(args));
        assertEquals(ConfigReaderUtil.MULTIPLE_STATISTIC_PARAMETERS_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void multipleAppendParams()    {
        String[] args = {"-f", "-a", "-a", "-p", "sample-", "in1.txt", "in2.txt"};
        IllegalParameterException thrown = assertThrows(IllegalParameterException.class, () -> ConfigReaderUtil.readConfig(args));
        assertEquals(ConfigReaderUtil.MULTIPLE_APPEND_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void multiplePathParams()    {
        String[] args = {"-f", "-a", "-o", "D:/Work", "-o", "-p", "sample-", "in1.txt", "in2.txt"};
        IllegalParameterException thrown = assertThrows(IllegalParameterException.class, () -> ConfigReaderUtil.readConfig(args));
        assertEquals(ConfigReaderUtil.MULTIPLE_NEW_PATH_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void multiplePrefixParams()    {
        String[] args = {"-f", "-a", "-p", "sample-", "-p", "in1.txt", "in2.txt"};
        IllegalParameterException thrown = assertThrows(IllegalParameterException.class, () -> ConfigReaderUtil.readConfig(args));
        assertEquals(ConfigReaderUtil.MULTIPLE_PREFIX_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void noParams()    {
        String[] args = new String[0];
        IllegalParameterException thrown = assertThrows(IllegalParameterException.class, () -> ConfigReaderUtil.readConfig(args));
        assertEquals(ConfigReaderUtil.NO_PARAMETERS_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void noFiles()    {
        String[] args = {"-f", "-a", "-p", "sample-", "-o", "D:/Work"};
        IllegalParameterException thrown = assertThrows(IllegalParameterException.class, () -> ConfigReaderUtil.readConfig(args));
        assertEquals(ConfigReaderUtil.NO_FILENAMES_ERROR_MESSAGE, thrown.getMessage());
    }
}