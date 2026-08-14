package tools.jackson.core.io;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import tools.jackson.core.JsonEncoding;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.StreamWriteFeature;
import tools.jackson.core.json.PR1657JsonGenerator;
import tools.jackson.core.json.PR1657WriterJsonGenerator;
import tools.jackson.core.json.XJBJsonGenerator;
import tools.jackson.core.json.JsonFactoryHelper;
import tools.jackson.core.json.UTF8JsonGenerator;
import tools.jackson.core.json.WriterBasedJsonGenerator;
import tools.jackson.core.json.XJBWriterJsonGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Random;

public class JsonGeneratorBenchmark extends BenchmarkLauncher {
    private static final int NUM_DOUBLES = 100;
    private static final double[] DOUBLES = new double[NUM_DOUBLES];
    private static final JsonFactoryHelper JSON_FACTORY = new JsonFactoryHelper();
    private static final int STD_FEATURES = StreamWriteFeature.collectDefaults();
    private static final int FAST_STD_FEATURES = STD_FEATURES
            | StreamWriteFeature.USE_FAST_DOUBLE_WRITER.getMask();

    static {
        Random rnd = new Random(42);
        for (int i = 0; i < NUM_DOUBLES; i++) {
            DOUBLES[i] = rnd.nextDouble();
        }
    }

    private IOContext createIOContext(Object ref) {
        return JSON_FACTORY.createContext(
                ContentReference.rawReference(ref), false, JsonEncoding.UTF8);
    }

    // --- Utf8 (byte-stream) benchmarks ---

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
    public void schubfachWriteDoubleArray(Blackhole bh) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
        IOContext ioCtxt = createIOContext(baos);
        UTF8JsonGenerator gen = JSON_FACTORY.createUtf8Generator(
                ObjectWriteContext.empty(), ioCtxt, FAST_STD_FEATURES, 0, baos);
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
                ObjectWriteContext.empty(), ioCtxt, FAST_STD_FEATURES, 0, baos);
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
                ObjectWriteContext.empty(), ioCtxt, FAST_STD_FEATURES, 0, baos);
        gen.writeStartArray();
        for (double d : DOUBLES) {
            gen.writeNumber(d);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(baos.size());
    }

    // --- Writer (char-stream) benchmarks ---

    @Benchmark
    public void baselineWriterWriteDoubleArray(Blackhole bh) throws IOException {
        StringWriter sw = new StringWriter(4096);
        IOContext ioCtxt = createIOContext(sw);
        WriterBasedJsonGenerator gen = JSON_FACTORY.createWriterGenerator(
                ObjectWriteContext.empty(), ioCtxt, STD_FEATURES, 0, sw);
        gen.writeStartArray();
        for (double d : DOUBLES) {
            gen.writeNumber(d);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(sw.getBuffer().length());
    }

    @Benchmark
    public void schubfachWriterWriteDoubleArray(Blackhole bh) throws IOException {
        StringWriter sw = new StringWriter(4096);
        IOContext ioCtxt = createIOContext(sw);
        WriterBasedJsonGenerator gen = JSON_FACTORY.createWriterGenerator(
                ObjectWriteContext.empty(), ioCtxt, FAST_STD_FEATURES, 0, sw);
        gen.writeStartArray();
        for (double d : DOUBLES) {
            gen.writeNumber(d);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(sw.getBuffer().length());
    }

    @Benchmark
    public void pr1657WriterWriteDoubleArray(Blackhole bh) throws IOException {
        StringWriter sw = new StringWriter(4096);
        IOContext ioCtxt = createIOContext(sw);
        PR1657WriterJsonGenerator gen = JSON_FACTORY.createPR1657WriterGenerator(
                ObjectWriteContext.empty(), ioCtxt, FAST_STD_FEATURES, 0, sw);
        gen.writeStartArray();
        for (double d : DOUBLES) {
            gen.writeNumber(d);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(sw.getBuffer().length());
    }

    @Benchmark
    public void xjbWriterWriteDoubleArray(Blackhole bh) throws IOException {
        StringWriter sw = new StringWriter(4096);
        IOContext ioCtxt = createIOContext(sw);
        XJBWriterJsonGenerator gen = JSON_FACTORY.createXJBWriterGenerator(
                ObjectWriteContext.empty(), ioCtxt, FAST_STD_FEATURES, 0, sw);
        gen.writeStartArray();
        for (double d : DOUBLES) {
            gen.writeNumber(d);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(sw.getBuffer().length());
    }
}
