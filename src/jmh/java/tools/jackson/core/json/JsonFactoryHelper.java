package tools.jackson.core.json;

import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.io.IOContext;
import tools.jackson.core.io.json.PR1657JsonGenerator;
import tools.jackson.core.io.json.XJBJsonGenerator;

import java.io.OutputStream;

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
}
