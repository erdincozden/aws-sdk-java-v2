package software.amazon.awssdk.core.internal.protocol.restxml.unmarshall;

import static software.amazon.awssdk.core.internal.protocol.json.StringToValueConverter.TO_BIG_DECIMAL;
import static software.amazon.awssdk.core.internal.protocol.json.StringToValueConverter.TO_BOOLEAN;
import static software.amazon.awssdk.core.internal.protocol.json.StringToValueConverter.TO_DOUBLE;
import static software.amazon.awssdk.core.internal.protocol.json.StringToValueConverter.TO_FLOAT;
import static software.amazon.awssdk.core.internal.protocol.json.StringToValueConverter.TO_INSTANT;
import static software.amazon.awssdk.core.internal.protocol.json.StringToValueConverter.TO_INTEGER;
import static software.amazon.awssdk.core.internal.protocol.json.StringToValueConverter.TO_LONG;
import static software.amazon.awssdk.core.internal.protocol.json.StringToValueConverter.TO_SDK_BYTES;
import static software.amazon.awssdk.core.internal.protocol.json.StringToValueConverter.TO_STRING;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.internal.protocol.json.StringToValueConverter;
import software.amazon.awssdk.core.protocol.MarshallLocation;
import software.amazon.awssdk.core.protocol.MarshallingType;
import software.amazon.awssdk.core.protocol.SdkField;
import software.amazon.awssdk.core.protocol.SdkPojo;
import software.amazon.awssdk.core.protocol.traits.ListTrait;
import software.amazon.awssdk.core.protocol.traits.LocationTrait;
import software.amazon.awssdk.core.protocol.traits.MapTrait;
import software.amazon.awssdk.core.runtime.transform.MapEntry;
import software.amazon.awssdk.utils.builder.SdkBuilder;

public class XmlPayloadUnmarshaller {

    public static final XmlUnmarshaller<String> STRING = new SimplePayloadUnmarshaller<String>(TO_STRING);
    public static final XmlUnmarshaller<Integer> INTEGER = new SimplePayloadUnmarshaller<Integer>(TO_INTEGER);
    public static final XmlUnmarshaller<Long> LONG = new SimplePayloadUnmarshaller<Long>(TO_LONG);
    public static final XmlUnmarshaller<Float> FLOAT = new SimplePayloadUnmarshaller<Float>(TO_FLOAT);
    public static final XmlUnmarshaller<Double> DOUBLE = new SimplePayloadUnmarshaller<Double>(TO_DOUBLE);
    public static final XmlUnmarshaller<BigDecimal> BIG_DECIMAL = new SimplePayloadUnmarshaller<BigDecimal>(TO_BIG_DECIMAL);
    public static final XmlUnmarshaller<Boolean> BOOLEAN = new SimplePayloadUnmarshaller<Boolean>(TO_BOOLEAN);
    // TODO Do timestampformat sep
    public static final XmlUnmarshaller<Instant> INSTANT = new SimplePayloadUnmarshaller<Instant>(TimestampUnmarshaller.getInstance());
    public static final XmlUnmarshaller<SdkBytes> SDK_BYTES = new SimplePayloadUnmarshaller<SdkBytes>(TO_SDK_BYTES);

    public static final XmlUnmarshaller<SdkPojo> SDK_POJO = (context, field) -> {
        try {
            return unmarshallSdkPojo(context, field);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    public static Map unmarshallMap(XmlUnmarshallerContext unmarshallerContext, SdkField<Map> mapField) {
        StaxUnmarshallerContext context = (StaxUnmarshallerContext) unmarshallerContext.payloadUnmarshallerContext();

        MapTrait mapTrait = mapField.getTrait(MapTrait.class);
        LocationTrait mapLocationTrait = mapField.getTrait(LocationTrait.class);
        String locationName = mapTrait.isFlattened() ? mapLocationTrait.locationName()
                                                   : mapLocationTrait.locationName() + "/entry";

        Map<String, Object> map = new HashMap<>();

        int originalDepth = context.getCurrentDepth();
        int targetDepth = originalDepth;

        while (true) {
            XMLEvent xmlEvent = context.nextEventUncheckedException();
            if (xmlEvent.isEndDocument()) {
                break;
            }

            if (xmlEvent.isAttribute() || xmlEvent.isStartElement()) {

                if (context.testExpression(locationName, targetDepth)) {

                    Map.Entry<String, Object> mapEntry = unmarshallMapEntry(unmarshallerContext, context, mapTrait);
                    map.put(mapEntry.getKey(), mapEntry.getValue());
                    continue;
                }
            } else if (xmlEvent.isEndElement()) {
                if (context.getCurrentDepth() < originalDepth) {
                    break;
                }
            }
        }

        return map;
    }


    public static Map.Entry unmarshallMapEntry(XmlUnmarshallerContext unmarshallerContext,
                                               StaxUnmarshallerContext context,
                                               MapTrait mapTrait) {


        MapEntry<String, Object> entry = new MapEntry<>();

        String keyLocationName = mapTrait.keyLocationName();
        String valueLocationName = mapTrait.valueLocationName();
        SdkField<?> valueField = mapTrait.valueFieldInfo();

        int originalDepth = context.getCurrentDepth();
        int targetDepth = originalDepth + 1;



        while (true) {
            XMLEvent xmlEvent = context.nextEventUncheckedException();
            if (xmlEvent.isEndDocument())
                break;

            if (xmlEvent.isAttribute() || xmlEvent.isStartElement()) {

                if (context.testExpression(keyLocationName, targetDepth)) {
                    XmlUnmarshaller keyUnmarshaller = unmarshallerContext.getUnmarshaller(MarshallLocation.PAYLOAD,
                                                                                          MarshallingType.STRING);
                    entry.setKey((String) keyUnmarshaller.unmarshall(unmarshallerContext, null));
                    continue;
                }

                //TODO Will this work for flattened list/map values
                if (context.testExpression(valueLocationName, targetDepth)) {
                    XmlUnmarshaller valueUnmarshaller = unmarshallerContext.getUnmarshaller(valueField.location(),
                                                                                            valueField.marshallingType());
                    entry.setValue(valueUnmarshaller.unmarshall(unmarshallerContext, valueField));
                    continue;
                }
            } else if (xmlEvent.isEndElement()) {
                if (context.getCurrentDepth() < originalDepth)
                    break;
            }
        }

        return entry;
    }

    /**
     * If the list is flattned,
     * @param unmarshallerContext
     * @param listField
     * @return
     */
    public static List unmarshallList(XmlUnmarshallerContext unmarshallerContext, SdkField<List> listField) {
        StaxUnmarshallerContext context = unmarshallerContext.payloadUnmarshallerContext();

        ListTrait listTrait = listField.getTrait(ListTrait.class);

        SdkField<?> memberField = listTrait.memberFieldInfo();
        String locationName = listField.locationName() + "/" + listTrait.memberLocationName();
        List<Object> list = new ArrayList<>();

        if (listTrait.isFlattened()) {
            locationName = listField.locationName();
            XmlUnmarshaller unmarshaller = unmarshallerContext.getUnmarshaller(listField.location(),
                                                                               memberField.marshallingType());
            list.add(unmarshaller.unmarshall(unmarshallerContext, memberField));
        }

        int originalDepth = context.getCurrentDepth();
        int targetDepth = originalDepth + 1;

        while (true) {
            XMLEvent xmlEvent = context.nextEventUncheckedException();
            if (xmlEvent.isEndDocument()) {
                break;
            }

            // TODO in first pass, depth not matching in testExpression
            if (xmlEvent.isAttribute() || xmlEvent.isStartElement()) {

                if (context.testExpression(locationName, targetDepth)) {
                    XmlUnmarshaller unmarshaller = unmarshallerContext.getUnmarshaller(memberField.location(),
                                                                                       memberField.marshallingType());
                    list.add(unmarshaller.unmarshall(unmarshallerContext, memberField));
                    continue;
                }
            } else if (xmlEvent.isEndElement()) {
                if (context.getCurrentDepth() < originalDepth) {
                    break;
                }
            }
        }

        return list;
    }

    public static <T extends SdkPojo> T unmarshallSdkPojo(XmlUnmarshallerContext unmarshallerContext,
                                                           SdkField<SdkPojo> pojoField) {
        StaxUnmarshallerContext context = (StaxUnmarshallerContext) unmarshallerContext.payloadUnmarshallerContext();
        SdkPojo sdkPojo = pojoField.constructor().get();
        List<SdkField<?>> memberFields = sdkPojo.sdkFields();

        int originalDepth = context.getCurrentDepth();
        int targetDepth = originalDepth + 1;

        if (context.isStartOfDocumentNoException())
            targetDepth += 1;

        while (true) {
            XMLEvent xmlEvent = context.nextEventUncheckedException();
            if (xmlEvent.isEndDocument()) {
                break;
            }

            if (xmlEvent.isAttribute() || xmlEvent.isStartElement()) {
                String locationName = context.elementAtTopOfStack();
                SdkField currentField = getFieldFromLocation(pojoField, memberFields, locationName);

                if (context.testExpression(locationName, targetDepth)) {
                    XmlUnmarshaller unmarshaller = unmarshallerContext.getUnmarshaller(currentField.location(),
                                                                                       currentField.marshallingType());
                    currentField.set(sdkPojo, unmarshaller.unmarshall(unmarshallerContext, currentField));
                    continue;
                }
            } else if (xmlEvent.isEndElement()) {
                if (context.getCurrentDepth() < originalDepth) {
                    break;
                }
            }
        }

        return ((SdkBuilder<?, T>) sdkPojo).build();
    }

    private static SdkField getFieldFromLocation(SdkField<SdkPojo> parent, List<SdkField<?>> memberFields, String locationName) {
        return memberFields.stream()
                           .filter(f -> f.locationName().equals(locationName))
                           .findAny()
                           .orElseThrow(() -> SdkClientException.create(
                               String.format("SdkPojo %s does not have a field with location name %s",
                                             parent.locationName(), locationName)));
    }

    /**
     * Base payload unmarshaller for simple types
     * @param <T> Type to unmarshall
     */
    private static class SimplePayloadUnmarshaller<T> implements XmlUnmarshaller<T> {
        private final StringToValueConverter.StringToValue<T> converter;

        private SimplePayloadUnmarshaller(StringToValueConverter.StringToValue<T> converter) {
            this.converter = converter;
        }

        @Override
        public T unmarshall(XmlUnmarshallerContext context, SdkField<T> field) {
            StaxUnmarshallerContext staxContext = (StaxUnmarshallerContext) context.payloadUnmarshallerContext();

            String s = null;
            try {
                s = staxContext.readText();
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            }
            return s != null ? converter.convert(s, field) : null;
        }
    }
}
