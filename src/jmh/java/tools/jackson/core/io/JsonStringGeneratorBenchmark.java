package tools.jackson.core.io;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import tools.jackson.core.JsonEncoding;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.StreamWriteFeature;
import tools.jackson.core.json.JsonFactoryHelper;
import tools.jackson.core.json.PR1682JsonGenerator;
import tools.jackson.core.json.PR1682WriterJsonGenerator;
import tools.jackson.core.json.UTF8JsonGenerator;
import tools.jackson.core.json.WriterBasedJsonGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Random;

/**
 * Compares the {@code writeString} escape-scan fast path of the 3.3.0-SNAPSHOT
 * {@link UTF8JsonGenerator} / {@link WriterBasedJsonGenerator} against the versions
 * modified in jackson-core PR #1682 ({@link PR1682JsonGenerator} / {@link PR1682WriterJsonGenerator}).
 */
@State(Scope.Benchmark)
public class JsonStringGeneratorBenchmark extends BenchmarkLauncher {
    private static final int NUM_STRINGS = 100;
    private static final JsonFactoryHelper JSON_FACTORY = new JsonFactoryHelper();
    private static final int STD_FEATURES = StreamWriteFeature.collectDefaults();

    private static final char[] ASCII_ALPHABET =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 .,-_:/".toCharArray();

    /**
     * <ul>
     * <li>{@code ascii-short}: 8-32 char plain ASCII, no escaping (typical property values)</li>
     * <li>{@code ascii-long}: 512-1024 char plain ASCII, no escaping</li>
     * <li>{@code ascii-escapes}: 512-1024 char ASCII with a {@code "}, {@code \} or {@code \n}
     *     roughly every 40 chars (exercises the slow-path handoff)</li>
     * <li>{@code unicode}: 512-1024 char ASCII with a non-ASCII char roughly every 40 chars
     *     (non-ASCII ends the UTF-8 fast loop; passes through the Writer fast loop)</li>
     * </ul>
     */
    @Param({"ascii-short", "ascii-long", "ascii-escapes", "unicode"})
    public String content;

    private String[] strings;
    private char[][] chars;
    private int outputSize;

    @Setup
    public void setup() {
        Random rnd = new Random(42);
        strings = new String[NUM_STRINGS];
        chars = new char[NUM_STRINGS][];
        int total = 0;
        for (int i = 0; i < NUM_STRINGS; i++) {
            strings[i] = generate(rnd, content);
            chars[i] = strings[i].toCharArray();
            total += strings[i].length();
        }
        // room for quotes, commas, escapes and 3-byte UTF-8 sequences
        outputSize = total * 3 + NUM_STRINGS * 4 + 16;
    }

    private static String generate(Random rnd, String content) {
        final int len;
        final String special;
        switch (content) {
        case "ascii-short":
            len = 8 + rnd.nextInt(25);
            special = null;
            break;
        case "ascii-long":
            len = 512 + rnd.nextInt(513);
            special = null;
            break;
        case "ascii-escapes":
            len = 512 + rnd.nextInt(513);
            special = "\"\\\n";
            break;
        case "unicode":
            len = 512 + rnd.nextInt(513);
            special = "éü日本";
            break;
        default:
            throw new IllegalArgumentException(content);
        }
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            if (special != null && i > 0 && (i % 40) == 0) {
                sb.append(special.charAt(rnd.nextInt(special.length())));
            } else {
                sb.append(ASCII_ALPHABET[rnd.nextInt(ASCII_ALPHABET.length)]);
            }
        }
        return sb.toString();
    }

    private IOContext createIOContext(Object ref) {
        return JSON_FACTORY.createContext(
                ContentReference.rawReference(ref), false, JsonEncoding.UTF8);
    }

    // --- Utf8 (byte-stream) benchmarks ---

    @Benchmark
    public void baselineUtf8WriteStringArray(Blackhole bh) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(outputSize);
        IOContext ioCtxt = createIOContext(baos);
        UTF8JsonGenerator gen = JSON_FACTORY.createUtf8Generator(
                ObjectWriteContext.empty(), ioCtxt, STD_FEATURES, 0, baos);
        gen.writeStartArray();
        for (String s : strings) {
            gen.writeString(s);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(baos.size());
    }

    @Benchmark
    public void pr1682Utf8WriteStringArray(Blackhole bh) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(outputSize);
        IOContext ioCtxt = createIOContext(baos);
        PR1682JsonGenerator gen = JSON_FACTORY.createPR1682Generator(
                ObjectWriteContext.empty(), ioCtxt, STD_FEATURES, 0, baos);
        gen.writeStartArray();
        for (String s : strings) {
            gen.writeString(s);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(baos.size());
    }

    @Benchmark
    public void baselineUtf8WriteCharsArray(Blackhole bh) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(outputSize);
        IOContext ioCtxt = createIOContext(baos);
        UTF8JsonGenerator gen = JSON_FACTORY.createUtf8Generator(
                ObjectWriteContext.empty(), ioCtxt, STD_FEATURES, 0, baos);
        gen.writeStartArray();
        for (char[] c : chars) {
            gen.writeString(c, 0, c.length);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(baos.size());
    }

    @Benchmark
    public void pr1682Utf8WriteCharsArray(Blackhole bh) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(outputSize);
        IOContext ioCtxt = createIOContext(baos);
        PR1682JsonGenerator gen = JSON_FACTORY.createPR1682Generator(
                ObjectWriteContext.empty(), ioCtxt, STD_FEATURES, 0, baos);
        gen.writeStartArray();
        for (char[] c : chars) {
            gen.writeString(c, 0, c.length);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(baos.size());
    }

    // --- Writer (char-stream) benchmarks ---

    @Benchmark
    public void baselineWriterWriteStringArray(Blackhole bh) throws IOException {
        StringWriter sw = new StringWriter(outputSize);
        IOContext ioCtxt = createIOContext(sw);
        WriterBasedJsonGenerator gen = JSON_FACTORY.createWriterGenerator(
                ObjectWriteContext.empty(), ioCtxt, STD_FEATURES, 0, sw);
        gen.writeStartArray();
        for (String s : strings) {
            gen.writeString(s);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(sw.getBuffer().length());
    }

    @Benchmark
    public void pr1682WriterWriteStringArray(Blackhole bh) throws IOException {
        StringWriter sw = new StringWriter(outputSize);
        IOContext ioCtxt = createIOContext(sw);
        PR1682WriterJsonGenerator gen = JSON_FACTORY.createPR1682WriterGenerator(
                ObjectWriteContext.empty(), ioCtxt, STD_FEATURES, 0, sw);
        gen.writeStartArray();
        for (String s : strings) {
            gen.writeString(s);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(sw.getBuffer().length());
    }

    @Benchmark
    public void baselineWriterWriteCharsArray(Blackhole bh) throws IOException {
        StringWriter sw = new StringWriter(outputSize);
        IOContext ioCtxt = createIOContext(sw);
        WriterBasedJsonGenerator gen = JSON_FACTORY.createWriterGenerator(
                ObjectWriteContext.empty(), ioCtxt, STD_FEATURES, 0, sw);
        gen.writeStartArray();
        for (char[] c : chars) {
            gen.writeString(c, 0, c.length);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(sw.getBuffer().length());
    }

    @Benchmark
    public void pr1682WriterWriteCharsArray(Blackhole bh) throws IOException {
        StringWriter sw = new StringWriter(outputSize);
        IOContext ioCtxt = createIOContext(sw);
        PR1682WriterJsonGenerator gen = JSON_FACTORY.createPR1682WriterGenerator(
                ObjectWriteContext.empty(), ioCtxt, STD_FEATURES, 0, sw);
        gen.writeStartArray();
        for (char[] c : chars) {
            gen.writeString(c, 0, c.length);
        }
        gen.writeEndArray();
        gen.close();
        bh.consume(sw.getBuffer().length());
    }
}
