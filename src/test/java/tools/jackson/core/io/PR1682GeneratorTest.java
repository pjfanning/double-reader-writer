package tools.jackson.core.io;

import org.junit.jupiter.api.Test;
import tools.jackson.core.JsonEncoding;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.StreamWriteFeature;
import tools.jackson.core.json.JsonFactoryHelper;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies the PR #1682 generator copies produce identical output to the
 * snapshot generators for the kinds of content the benchmark writes.
 */
public class PR1682GeneratorTest {
    private static final JsonFactoryHelper F = new JsonFactoryHelper();
    private static final int STD = StreamWriteFeature.collectDefaults();

    private static String[] samples() {
        Random rnd = new Random(7);
        String[] s = new String[200];
        String alphabet = "abcXYZ019 .,-\"\\\n\téü日本😀/<>&";
        for (int i = 0; i < s.length; i++) {
            int len = rnd.nextInt(3000);
            StringBuilder sb = new StringBuilder(len);
            for (int j = 0; j < len; j++) {
                sb.append(alphabet.charAt(rnd.nextInt(alphabet.length())));
            }
            s[i] = sb.toString();
        }
        return s;
    }

    private static void writeAll(JsonGenerator g, String[] samples) {
        g.writeStartArray();
        for (String s : samples) {
            g.writeString(s);
            char[] c = s.toCharArray();
            g.writeString(c, 0, c.length);
            g.writeStartObject();
            g.writeName(s);
            g.writeString(s);
            g.writeEndObject();
        }
        g.writeEndArray();
        g.close();
    }

    @Test
    public void utf8OutputMatches() {
        String[] samples = samples();
        ByteArrayOutputStream a = new ByteArrayOutputStream();
        writeAll(F.createUtf8Generator(ObjectWriteContext.empty(),
                F.createContext(ContentReference.rawReference(a), false, JsonEncoding.UTF8), STD, 0, a), samples);
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        writeAll(F.createPR1682Generator(ObjectWriteContext.empty(),
                F.createContext(ContentReference.rawReference(b), false, JsonEncoding.UTF8), STD, 0, b), samples);
        assertEquals(new String(a.toByteArray(), StandardCharsets.UTF_8),
                new String(b.toByteArray(), StandardCharsets.UTF_8));
    }

    @Test
    public void writerOutputMatches() {
        String[] samples = samples();
        StringWriter a = new StringWriter();
        writeAll(F.createWriterGenerator(ObjectWriteContext.empty(),
                F.createContext(ContentReference.rawReference(a), false, JsonEncoding.UTF8), STD, 0, a), samples);
        StringWriter b = new StringWriter();
        writeAll(F.createPR1682WriterGenerator(ObjectWriteContext.empty(),
                F.createContext(ContentReference.rawReference(b), false, JsonEncoding.UTF8), STD, 0, b), samples);
        assertEquals(a.toString(), b.toString());
    }
}
