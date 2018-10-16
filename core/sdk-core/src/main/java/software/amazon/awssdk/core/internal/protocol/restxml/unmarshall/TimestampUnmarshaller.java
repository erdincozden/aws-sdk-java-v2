package software.amazon.awssdk.core.internal.protocol.restxml.unmarshall;


import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.internal.protocol.json.StringToValueConverter;
import software.amazon.awssdk.core.internal.util.AwsDateUtils;
import software.amazon.awssdk.core.protocol.MarshallLocation;
import software.amazon.awssdk.core.protocol.SdkField;
import software.amazon.awssdk.core.protocol.traits.TimestampFormatTrait;
import software.amazon.awssdk.utils.DateUtils;

/**
 * Unmarshaller for timestamp members. Takes into account the {@link TimestampFormatTrait}.
 */
@SdkInternalApi
public final class TimestampUnmarshaller implements StringToValueConverter.StringToValue<Instant> {

    private static final TimestampUnmarshaller INSTANCE = new TimestampUnmarshaller();

    /**
     * Default formats for the given location.
     */
    private static final Map<MarshallLocation, TimestampFormatTrait.Format> DEFAULT_FORMATS;

    static {
        Map<MarshallLocation, TimestampFormatTrait.Format> formats = new HashMap<>();
        formats.put(MarshallLocation.HEADER, TimestampFormatTrait.Format.RFC_822);
        formats.put(MarshallLocation.PAYLOAD, TimestampFormatTrait.Format.ISO_8601);
        DEFAULT_FORMATS = Collections.unmodifiableMap(formats);
    }

    private TimestampUnmarshaller() {
    }

    @Override
    public Instant convert(String value, SdkField<Instant> field) {
        if (value == null) {
            return null;
        }
        TimestampFormatTrait.Format format = resolveTimestampFormat(field);
        switch (format) {
            case ISO_8601:
                return DateUtils.parseIso8601Date(value);
            case UNIX_TIMESTAMP:
                return AwsDateUtils.parseServiceSpecificInstant(value);
            case RFC_822:
                return DateUtils.parseRfc1123Date(value);
            default:
                throw SdkClientException.create("Unrecognized timestamp format - " + format);
        }
    }

    private TimestampFormatTrait.Format resolveTimestampFormat(SdkField<Instant> field) {
        TimestampFormatTrait trait = field.getTrait(TimestampFormatTrait.class);
        if (trait == null) {
            TimestampFormatTrait.Format format = DEFAULT_FORMATS.get(field.location());
            if (format == null) {
                throw SdkClientException.create(
                    String.format("Timestamps are not supported for this location (%s)", field.location()));
            }
            return format;
        } else {
            return trait.format();
        }
    }

    public static TimestampUnmarshaller getInstance() {
        return INSTANCE;
    }
}
