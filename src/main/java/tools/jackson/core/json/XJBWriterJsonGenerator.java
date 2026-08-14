package tools.jackson.core.json;

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

import java.io.Writer;

/**
 * Subclass of {@link WriterBasedJsonGenerator} that overrides {@code writeNumber(double)} to
 * write XJB chars directly into the output buffer, bypassing {@code writeRaw(String)}.
 */
public class XJBWriterJsonGenerator extends WriterBasedJsonGenerator {

    public XJBWriterJsonGenerator(ObjectWriteContext ctxt, IOContext ioCtxt,
                                  int stdFeatures, int formatFeatures, Writer out,
                                  SerializableString rootValueSeparator, PrettyPrinter prettyPrinter,
                                  CharacterEscapes charEscapes, int maximumNonEscapedChar, char quoteChar) {
        super(ctxt, ioCtxt, stdFeatures, formatFeatures, out,
                rootValueSeparator, prettyPrinter, charEscapes,
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
            // Write XJB chars directly into output buffer, avoiding String allocation
            if ((_outputTail + XJBWriter.MAX_DOUBLE_CHARS) > _outputEnd) {
                _flushBuffer();
            }
            _outputTail = XJBWriter.writeDouble(d, _outputBuffer, _outputTail);
            return this;
        }
        return writeRaw(NumberOutput.toString(d, false));
    }
}
