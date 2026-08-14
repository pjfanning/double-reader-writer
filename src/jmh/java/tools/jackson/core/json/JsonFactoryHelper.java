package tools.jackson.core.json;

import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.io.IOContext;

import java.io.OutputStream;
import java.io.Writer;

/**
 * Exposes protected {@link JsonFactory} methods for direct {@link UTF8JsonGenerator} creation,
 * and can produce {@link PR1657JsonGenerator} and {@link XJBJsonGenerator} instances for benchmarking.
 */
public class JsonFactoryHelper extends JsonFactory {

    public IOContext createContext(tools.jackson.core.io.ContentReference ref, boolean resourceManaged,
            tools.jackson.core.JsonEncoding encoding) {
        return _createContext(ref, resourceManaged, encoding);
    }

    public UTF8JsonGenerator createUtf8Generator(ObjectWriteContext ctxt, IOContext ioCtxt,
            int stdFeatures, int formatFeatures, OutputStream out) {
        return new UTF8JsonGenerator(ctxt, ioCtxt, stdFeatures, formatFeatures, out,
                _rootValueSeparator, _characterEscapes, null,
                _maximumNonEscapedChar, _quoteChar);
    }

    public PR1657JsonGenerator createPR1657Generator(ObjectWriteContext ctxt, IOContext ioCtxt,
            int stdFeatures, int formatFeatures, OutputStream out) {
        return new PR1657JsonGenerator(ctxt, ioCtxt, stdFeatures, formatFeatures, out,
                _rootValueSeparator, _characterEscapes, null,
                _maximumNonEscapedChar, _quoteChar);
    }

    public XJBJsonGenerator createXJBGenerator(ObjectWriteContext ctxt, IOContext ioCtxt,
            int stdFeatures, int formatFeatures, OutputStream out) {
        return new XJBJsonGenerator(ctxt, ioCtxt, stdFeatures, formatFeatures, out,
                _rootValueSeparator, _characterEscapes, null,
                _maximumNonEscapedChar, _quoteChar);
    }

    public WriterBasedJsonGenerator createWriterGenerator(ObjectWriteContext ctxt, IOContext ioCtxt,
            int stdFeatures, int formatFeatures, Writer out) {
        return new WriterBasedJsonGenerator(ctxt, ioCtxt, stdFeatures, formatFeatures, out,
                _rootValueSeparator, null, _characterEscapes,
                _maximumNonEscapedChar, _quoteChar);
    }

    public PR1657WriterJsonGenerator createPR1657WriterGenerator(ObjectWriteContext ctxt, IOContext ioCtxt,
            int stdFeatures, int formatFeatures, Writer out) {
        return new PR1657WriterJsonGenerator(ctxt, ioCtxt, stdFeatures, formatFeatures, out,
                _rootValueSeparator, null, _characterEscapes,
                _maximumNonEscapedChar, _quoteChar);
    }

    public XJBWriterJsonGenerator createXJBWriterGenerator(ObjectWriteContext ctxt, IOContext ioCtxt,
            int stdFeatures, int formatFeatures, Writer out) {
        return new XJBWriterJsonGenerator(ctxt, ioCtxt, stdFeatures, formatFeatures, out,
                _rootValueSeparator, null, _characterEscapes,
                _maximumNonEscapedChar, _quoteChar);
    }

}
