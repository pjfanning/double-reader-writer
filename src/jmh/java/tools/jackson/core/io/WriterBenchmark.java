package tools.jackson.core.io;

import org.openjdk.jmh.infra.Blackhole;
import tools.jackson.core.io.numberwriter.RyuDouble;
import tools.jackson.core.io.schubfach.DoubleToDecimal;
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
    public void ryuIntWriter(Blackhole bh) {
        for (int i = 0; i < LEN; i++) {
            bh.consume(RyuDouble.doubleToString((double)i));
        }
    }

    @Benchmark
    public void schubfachIntWriter(Blackhole bh) {
        for (int i = 0; i < LEN; i++) {
            bh.consume(DoubleToDecimal.toString((double)i));
        }
    }

    @Benchmark
    public void jdkDoubleIntWriter(Blackhole bh) {
        for (int i = 0; i < LEN; i++) {
            bh.consume(Double.toString((double)i));
        }
    }

    @Benchmark
    public void jdkLongWriter(Blackhole bh) {
        for (long i = 0; i < LEN; i++) {
            bh.consume(Long.toString(i));
        }
    }

    @Benchmark
    public void ryuDoubleWriter(Blackhole bh) {
        for (Double d : DOUBLES) {
            bh.consume(RyuDouble.doubleToString(d));
        }
    }

    @Benchmark
    public void schubfachDoubleWriter(Blackhole bh) {
        for (Double d : DOUBLES) {
            bh.consume(DoubleToDecimal.toString(d));
        }
    }

    @Benchmark
    public void jdkDoubleWriter(Blackhole bh) {
        for (Double d : DOUBLES) {
            bh.consume(Double.toString(d));
        }
    }
}
