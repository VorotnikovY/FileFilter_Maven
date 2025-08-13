package org.example;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DataTypeUtil {

    public static DataType defineDataType(String line) {
        if (line.isEmpty()) return DataType.STRING;

        try {
            new BigInteger(line);
            return DataType.INTEGER;
        } catch (NumberFormatException e) {
            try {
                new BigDecimal(line);
                return DataType.FLOAT;
            } catch (NumberFormatException e1) {
                return DataType.STRING;
            }
        }
    }
}
