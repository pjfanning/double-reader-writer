package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.numberwriter.RyuDouble;
import com.fasterxml.jackson.core.io.numberwriter.RyuFloat;
import com.fasterxml.jackson.core.io.schubfach.DoubleToDecimal;
import com.fasterxml.jackson.core.io.schubfach.FloatToDecimal;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriterTest {
    private static final int LEN = 1000;
    private static final double[] DOUBLES = new double[LEN];
    private static final float[] FLOATS = new float[LEN];

    static {
        Random rnd = new Random();
        for (int i = 0; i < LEN; i++) {
            DOUBLES[i] = rnd.nextDouble();
            FLOATS[i] = rnd.nextFloat();
        }
    }

    @Test
    void verifySchubfachDoubles() {
        for (int i = 0; i < LEN; i++) {
            String sf = DoubleToDecimal.toString(DOUBLES[i]);
            assertEquals(DOUBLES[i], Double.parseDouble(sf));
        }
    }

    @Test
    void verifyRyuDoubles() {
        for (int i = 0; i < LEN; i++) {
            String ryu = RyuDouble.doubleToString(DOUBLES[i]);
            assertEquals(DOUBLES[i], Double.parseDouble(ryu));
        }
    }

    @Test
    void verifyJdkDoubles() {
        for (int i = 0; i < LEN; i++) {
            String jdk = Double.toString(DOUBLES[i]);
            assertEquals(DOUBLES[i], Double.parseDouble(jdk));
        }
    }

    @Test
    void verifySchubfachFloats() {
        for (int i = 0; i < LEN; i++) {
            String sf = FloatToDecimal.toString(FLOATS[i]);
            assertEquals(FLOATS[i], Float.parseFloat(sf));
        }
    }

    @Test
    void verifyRyuFloats() {
        for (int i = 0; i < LEN; i++) {
            String ryu = RyuFloat.floatToString(FLOATS[i]);
            assertEquals(FLOATS[i], Float.parseFloat(ryu));
        }
    }

    @Test
    void verifyJdkFloats() {
        for (int i = 0; i < LEN; i++) {
            String ryu = Float.toString(FLOATS[i]);
            assertEquals(FLOATS[i], Float.parseFloat(ryu));
        }
    }

}
