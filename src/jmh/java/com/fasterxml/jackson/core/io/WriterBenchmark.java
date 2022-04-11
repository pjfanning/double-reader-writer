package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.numberwriter.RyuDouble;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.Random;

public class WriterBenchmark extends BenchmarkLauncher {
    private static final int LEN = 1000;
    private static final double[] DOUBLES = new double[LEN];

    static {
        Random rnd = new Random();
        for (int i = 0; i < LEN; i++) {
            DOUBLES[i] = rnd.nextDouble();
        }
    }

    @Benchmark
    public void ryuIntWriter() {
        for (int i = 0; i < LEN; i++) {
            RyuDouble.doubleToString((double)i);
        }
    }

    @Benchmark
    public void jdkDoubleIntWriter() {
        for (int i = 0; i < LEN; i++) {
            Double.toString((double)i);
        }
    }

    @Benchmark
    public void jdkLongWriter() {
        for (long i = 0; i < LEN; i++) {
            Long.toString(i);
        }
    }

    @Benchmark
    public void ryuDoubleWriter() {
        for (Double d : DOUBLES) {
            RyuDouble.doubleToString(d);
        }
    }

    @Benchmark
    public void jdkDoubleWriter() {
        for (Double d : DOUBLES) {
            Double.toString(d);
        }
    }
}
