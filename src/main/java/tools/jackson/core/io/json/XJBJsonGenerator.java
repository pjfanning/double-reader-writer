package tools.jackson.core.io.json;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.PrettyPrinter;
import tools.jackson.core.SerializableString;
import tools.jackson.core.StreamWriteFeature;
import tools.jackson.core.io.CharacterEscapes;
import tools.jackson.core.io.IOContext;
import tools.jackson.core.io.NumberOutput;
import tools.jackson.core.io.numberwriter.XJBWriter;
import tools.jackson.core.json.JsonWriteFeature;
import tools.jackson.core.json.UTF8JsonGenerator;

import java.io.OutputStream;

/**
 * Subclass of {@link UTF8JsonGenerator} that overrides {@code writeNumber(double)} to write
 * XJB bytes directly into the output buffer, avoiding String allocation.
 */
public class XJBJsonGenerator extends UTF8JsonGenerator {

    private static final int MAX_DOUBLE_BYTES = 26;

    public XJBJsonGenerator(ObjectWriteContext ctxt, IOContext ioCtxt,
            int stdFeatures, int formatFeatures, OutputStream out,
            SerializableString rootValueSeparator, CharacterEscapes charEscapes,
            PrettyPrinter prettyPrinter, int maximumNonEscapedChar, char quoteChar) {
        super(ctxt, ioCtxt, stdFeatures, formatFeatures, out,
                rootValueSeparator, charEscapes, prettyPrinter,
                maximumNonEscapedChar, quoteChar);
    }

    @Override
    public JsonGenerator writeNumber(double d) throws JacksonException {
        boolean useFast = isEnabled(StreamWriteFeature.USE_FAST_DOUBLE_WRITER);
        if (_cfgNumbersAsStrings
                || (NumberOutput.notFinite(d)
                    && isEnabled(JsonWriteFeature.WRITE_NAN_AS_STRINGS))) {
            writeString(NumberOutput.toString(d, useFast));
            return this;
        }
        _verifyValueWrite("write a number");
        if (useFast) {
            if ((_outputTail + MAX_DOUBLE_BYTES) > _outputEnd) {
                _flushBuffer();
            }
            _outputTail = XJBWriter.writeDouble(d, _outputBuffer, _outputTail);
            return this;
        }
        return writeRaw(NumberOutput.toString(d, false));
    }
}
