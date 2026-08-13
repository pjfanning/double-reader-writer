package tools.jackson.core.io;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import tools.jackson.core.json.JsonFactoryHelper;
import tools.jackson.core.json.UTF8JsonGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class JsonGeneratorBenchmark extends BenchmarkLauncher {
    private static final int NUM_DOUBLES = 100;
    private static final double[] DOUBLES = new double[NUM_DOUBLES];
    private static final JsonFactoryHelper JSON_FACTORY = new JsonFactoryHelper();

    static {
        Random rnd = new Random(42);
        for (int i = 0; i < NUM_DOUBLES; i++) {
            DOUBLES[i] = rnd.nextDouble();
        }
    }

    @Benchmark
    public void writeDoubleArray(Blackhole bh) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
        UTF8JsonGenerator gen = JSON_FACTORY.createUtf8Generator(baos);
        gen.writeStartArray();
        for (double d : DOUBLES) {
            gen.writeNumber(d);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(baos.size());
    }
}
