package tools.jackson.core.io;

import ch.randelshofer.fastdoubleparser.JavaDoubleParser;
import ch.randelshofer.fastdoubleparser.JavaFloatParser;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

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
    public void jdkDoubleReader(Blackhole bh) {
        for (int i = 0; i < LEN; i++) {
            bh.consume(Double.parseDouble(DOUBLE_STRINGS[i]));
        }
    }

    @Benchmark
    public void jdkFloatReader(Blackhole bh) {
        for (int i = 0; i < LEN; i++) {
            bh.consume(Float.parseFloat(FLOAT_STRINGS[i]));
        }
    }

    @Benchmark
    public void fastDoubleReader(Blackhole bh) {
        for (int i = 0; i < LEN; i++) {
            bh.consume(JavaDoubleParser.parseDouble(DOUBLE_STRINGS[i]));
        }
    }

    @Benchmark
    public void fastFloatReader(Blackhole bh) {
        for (int i = 0; i < LEN; i++) {
            bh.consume(JavaFloatParser.parseFloat(FLOAT_STRINGS[i]));
        }
    }

    @Benchmark
    public void jdkLongReader(Blackhole bh) {
        for (int i = 0; i < LEN; i++) {
            bh.consume(Long.parseLong(LONG_STRINGS[i]));
        }
    }

    @Benchmark
    public void fastDoubleLongReader(Blackhole bh) {
        for (int i = 0; i < LEN; i++) {
            bh.consume(JavaDoubleParser.parseDouble(LONG_STRINGS[i]));
        }
    }
}
