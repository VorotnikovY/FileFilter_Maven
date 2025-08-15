package org.example;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class DataTypeUtilTest {

    @Test
    public void integerTypeTest() {
        Random random = new Random();
        float number = (random.nextBoolean() ? 1 : -1) * random.nextFloat() * Float.MAX_VALUE;
        String line = number + "";
        assertEquals(DataType.FLOAT, DataTypeUtil.defineDataType(line));
    }

    @Test
    public void floatTypeTest() {
        Random random = new Random();
        int number = (random.nextBoolean() ? 1 : -1) * random.nextInt() * Integer.MAX_VALUE;
        String line = number + "";
        assertEquals(DataType.INTEGER, DataTypeUtil.defineDataType(line));
    }

    @Test
    public void stringTypeTest() {
        Random random = new Random();
        int length = random.nextInt(100);

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(128));
            sb.append(randomChar);
        }
        assertEquals(DataType.STRING, DataTypeUtil.defineDataType(sb.toString()));
    }
}