package tools.jackson.core.io;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import tools.jackson.core.JsonEncoding;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.StreamWriteFeature;
import tools.jackson.core.io.json.PR1657JsonGenerator;
import tools.jackson.core.io.json.XJBJsonGenerator;
import tools.jackson.core.json.JsonFactoryHelper;
import tools.jackson.core.json.UTF8JsonGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class JsonGeneratorBenchmark extends BenchmarkLauncher {
    private static final int NUM_DOUBLES = 100;
    private static final double[] DOUBLES = new double[NUM_DOUBLES];
    private static final JsonFactoryHelper JSON_FACTORY = new JsonFactoryHelper();
    private static final int STD_FEATURES = StreamWriteFeature.collectDefaults()
            | StreamWriteFeature.USE_FAST_DOUBLE_WRITER.getMask();

    static {
        Random rnd = new Random(42);
        for (int i = 0; i < NUM_DOUBLES; i++) {
            DOUBLES[i] = rnd.nextDouble();
        }
    }

    private IOContext createIOContext(ByteArrayOutputStream baos) {
        return JSON_FACTORY.createContext(
                ContentReference.rawReference(baos), false, JsonEncoding.UTF8);
    }

    @Benchmark
    public void baselineWriteDoubleArray(Blackhole bh) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
        IOContext ioCtxt = createIOContext(baos);
        UTF8JsonGenerator gen = JSON_FACTORY.createUtf8Generator(
                ObjectWriteContext.empty(), ioCtxt, STD_FEATURES, 0, baos);
        gen.writeStartArray();
        for (double d : DOUBLES) {
            gen.writeNumber(d);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(baos.size());
    }

    @Benchmark
    public void pr1657WriteDoubleArray(Blackhole bh) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
        IOContext ioCtxt = createIOContext(baos);
        PR1657JsonGenerator gen = JSON_FACTORY.createPR1657Generator(
                ObjectWriteContext.empty(), ioCtxt, STD_FEATURES, 0, baos);
        gen.writeStartArray();
        for (double d : DOUBLES) {
            gen.writeNumber(d);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(baos.size());
    }

    @Benchmark
    public void xjbWriteDoubleArray(Blackhole bh) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
        IOContext ioCtxt = createIOContext(baos);
        XJBJsonGenerator gen = JSON_FACTORY.createXJBGenerator(
                ObjectWriteContext.empty(), ioCtxt, STD_FEATURES, 0, baos);
        gen.writeStartArray();
        for (double d : DOUBLES) {
            gen.writeNumber(d);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(baos.size());
    }
}
