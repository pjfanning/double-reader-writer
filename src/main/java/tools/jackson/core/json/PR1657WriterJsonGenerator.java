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
import tools.jackson.core.io.schubfach.local.DoubleToDecimal;

import java.io.Writer;

/**
 * Subclass of {@link WriterBasedJsonGenerator} that overrides {@code writeNumber(double)} to
 * write Schubfach chars directly into the output buffer, bypassing {@code writeRaw(String)}.
 * Mirrors the spirit of jackson-core PR #1657 for the writer-based generator.
 */
public class PR1657WriterJsonGenerator extends WriterBasedJsonGenerator {

    public PR1657WriterJsonGenerator(ObjectWriteContext ctxt, IOContext ioCtxt,
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
            // Write Schubfach string directly to char buffer, bypassing writeRaw overhead
            String s = DoubleToDecimal.toString(d);
            int len = s.length();
            if ((_outputTail + len) > _outputEnd) {
                _flushBuffer();
            }
            s.getChars(0, len, _outputBuffer, _outputTail);
            _outputTail += len;
            return this;
        }
        return writeRaw(NumberOutput.toString(d, false));
    }
}
