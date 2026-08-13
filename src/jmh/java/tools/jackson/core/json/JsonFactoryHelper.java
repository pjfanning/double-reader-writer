package tools.jackson.core.json;

import tools.jackson.core.JsonEncoding;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.io.ContentReference;
import tools.jackson.core.io.IOContext;

import java.io.OutputStream;

/**
 * Exposes protected {@link JsonFactory} methods for direct {@link UTF8JsonGenerator} creation.
 */
public class JsonFactoryHelper extends JsonFactory {

    public UTF8JsonGenerator createUtf8Generator(OutputStream out) {
        IOContext ioCtxt = _createContext(ContentReference.rawReference(out), false, JsonEncoding.UTF8);
        return (UTF8JsonGenerator) _createUTF8Generator(ObjectWriteContext.empty(), ioCtxt, out);
    }
}
