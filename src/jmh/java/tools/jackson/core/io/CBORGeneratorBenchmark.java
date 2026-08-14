package tools.jackson.core.io;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.StreamWriteFeature;
import tools.jackson.core.io.IOContext;
import tools.jackson.dataformat.cbor.CBORFactory;
import tools.jackson.dataformat.cbor.CBORGenerator;
import tools.jackson.dataformat.cbor.CBORWriteFeature;
import tools.jackson.dataformat.cbor.pr752.PR752CBORGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class CBORGeneratorBenchmark extends BenchmarkLauncher {
    private static final int NUM_DOUBLES = 100;
    private static final double[] DOUBLES = new double[NUM_DOUBLES];
    private static final CBORFactory CBOR_FACTORY = new CBORFactory();
    private static final int STD_FEATURES = StreamWriteFeature.collectDefaults();
    private static final int FORMAT_FEATURES = CBORWriteFeature.collectDefaults();

    private static final ObjectWriteContext BASELINE_CTXT = new ObjectWriteContext.Base() {
        @Override
        public int getStreamWriteFeatures(int defaults) {
            return STD_FEATURES;
        }
    };

    // Factory that creates PR752CBORGenerator instead of CBORGenerator
    private static final CBORFactory PR752_CBOR_FACTORY = new CBORFactory() {
        @Override
        protected JsonGenerator _createGenerator(ObjectWriteContext writeCtxt,
                IOContext ioCtxt, OutputStream out) {
            return new PR752CBORGenerator(writeCtxt, ioCtxt,
                    writeCtxt.getStreamWriteFeatures(_streamWriteFeatures),
                    writeCtxt.getFormatWriteFeatures(_formatWriteFeatures),
                    out);
        }
    };

    static {
        Random rnd = new Random(42);
        for (int i = 0; i < NUM_DOUBLES; i++) {
            DOUBLES[i] = rnd.nextDouble();
        }
    }

    @Benchmark
    public void cborBaselineWriteDoubleArray(Blackhole bh) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
        CBORGenerator gen = (CBORGenerator) CBOR_FACTORY.createGenerator(
                BASELINE_CTXT, baos);
        gen.writeStartArray();
        for (double d : DOUBLES) {
            gen.writeNumber(d);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(baos.size());
    }

    @Benchmark
    public void pr752CborWriteDoubleArray(Blackhole bh) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
        PR752CBORGenerator gen = (PR752CBORGenerator) PR752_CBOR_FACTORY.createGenerator(
                BASELINE_CTXT, baos);
        gen.writeStartArray();
        for (double d : DOUBLES) {
            gen.writeNumber(d);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(baos.size());
    }

    @Benchmark
    public void pr752CborMinimalDoubleArray(Blackhole bh) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
        PR752CBORGenerator gen = (PR752CBORGenerator) PR752_CBOR_FACTORY.createGenerator(
                BASELINE_CTXT, baos);
        gen.writeStartArray();
        for (double d : DOUBLES) {
            gen.writeNumber(d);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(baos.size());
    }
}
