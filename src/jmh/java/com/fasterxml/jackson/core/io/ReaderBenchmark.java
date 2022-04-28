package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.doubleparser.FastDoubleParser;
import com.fasterxml.jackson.core.io.doubleparser.FastFloatParser;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.Random;

public class ReaderBenchmark extends BenchmarkLauncher {
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

    @Benchmark
    public void jdkDoubleReader() {
        for (int i = 0; i < LEN; i++) {
            Double.parseDouble(DOUBLE_STRINGS[i]);
        }
    }

    @Benchmark
    public void jdkFloatReader() {
        for (int i = 0; i < LEN; i++) {
            Float.parseFloat(FLOAT_STRINGS[i]);
        }
    }

    @Benchmark
    public void fastDoubleReader() {
        for (int i = 0; i < LEN; i++) {
            FastDoubleParser.parseDouble(DOUBLE_STRINGS[i]);
        }
    }

    @Benchmark
    public void fastFloatReader() {
        for (int i = 0; i < LEN; i++) {
            float f = FastFloatParser.parseFloat(FLOAT_STRINGS[i]);
        }
    }

    @Benchmark
    public void jdkLongReader() {
        for (int i = 0; i < LEN; i++) {
            Long.parseLong(LONG_STRINGS[i]);
        }
    }

    @Benchmark
    public void fastDoubleLongReader() {
        for (int i = 0; i < LEN; i++) {
            FastDoubleParser.parseDouble(LONG_STRINGS[i]);
        }
    }
}
