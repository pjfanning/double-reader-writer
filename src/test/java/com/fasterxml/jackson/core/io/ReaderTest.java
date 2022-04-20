package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.numberreader.FastDoubleParser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReaderTest {
    private static final int LEN = 1000;
    private static final String[] DOUBLE_STRINGS = new String[LEN];
    private static final String[] FLOAT_STRINGS = new String[LEN];
    private static final String[] LONG_STRINGS = new String[LEN];

    static {
        Random rnd = new Random();
        for (int i = 0; i < LEN; i++) {
            DOUBLE_STRINGS[i] = Double.toString(rnd.nextDouble());
            FLOAT_STRINGS[i] = Float.toString(rnd.nextFloat());
            LONG_STRINGS[i] = Long.toString(rnd.nextLong());
        }
    }

    @Test
    void verifyDoubles() {
        for (int i = 0; i < LEN; i++) {
            double fd = FastDoubleParser.parseDouble(DOUBLE_STRINGS[i]);
            double jd = Double.parseDouble(DOUBLE_STRINGS[i]);
            assertEquals(jd, fd);
        }
    }

    @Test
    void verifyFloats() {
        for (int i = 0; i < LEN; i++) {
            double fd = FastDoubleParser.parseDouble(FLOAT_STRINGS[i]);
            double jd = Double.parseDouble(FLOAT_STRINGS[i]);
            assertEquals(jd, fd);
        }
    }

    @Disabled("this proves that parsing longs as doubles is a bad idea because of precision loss with large absolute values")
    @Test
    void verifyLongs() {
        for (int i = 0; i < LEN; i++) {
            long fl = (long)FastDoubleParser.parseDouble(LONG_STRINGS[i]);
            long jl = Long.parseLong(LONG_STRINGS[i]);
            assertEquals(jl, fl, "expected " + LONG_STRINGS[i]);
        }
    }

}
