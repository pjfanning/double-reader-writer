package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.numberreader.FastDoubleParser;
import com.fasterxml.jackson.core.io.numberwriter.RyuDouble;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.Random;

public class ReaderBenchmark extends BenchmarkLauncher {
    private static final int LEN = 1000;
    private static final String[] STRINGS = new String[LEN];

    static {
        Random rnd = new Random();
        for (int i = 0; i < LEN; i++) {
            STRINGS[i] = Double.toString(rnd.nextDouble());
        }
    }

    @Benchmark
    public void jdkDoubleReader() {
        for (int i = 0; i < LEN; i++) {
            Double.parseDouble(STRINGS[i]);
        }
    }

    @Benchmark
    public void fastDoubleReader() {
        for (int i = 0; i < LEN; i++) {
            FastDoubleParser.parseDouble(STRINGS[i]);
        }
    }
}
