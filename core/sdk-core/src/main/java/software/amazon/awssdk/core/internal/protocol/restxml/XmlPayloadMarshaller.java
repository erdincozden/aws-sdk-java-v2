/*
 * Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.core.internal.protocol.restxml;

import static software.amazon.awssdk.core.internal.protocol.json.ValueToStringConverter.FROM_BIG_DECIMAL;
import static software.amazon.awssdk.core.internal.protocol.json.ValueToStringConverter.FROM_BOOLEAN;
import static software.amazon.awssdk.core.internal.protocol.json.ValueToStringConverter.FROM_DOUBLE;
import static software.amazon.awssdk.core.internal.protocol.json.ValueToStringConverter.FROM_FLOAT;
import static software.amazon.awssdk.core.internal.protocol.json.ValueToStringConverter.FROM_INSTANT;
import static software.amazon.awssdk.core.internal.protocol.json.ValueToStringConverter.FROM_INTEGER;
import static software.amazon.awssdk.core.internal.protocol.json.ValueToStringConverter.FROM_LONG;
import static software.amazon.awssdk.core.internal.protocol.json.ValueToStringConverter.FROM_STRING;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.internal.protocol.json.ValueToStringConverter;
import software.amazon.awssdk.core.protocol.MarshallLocation;
import software.amazon.awssdk.core.protocol.SdkField;
import software.amazon.awssdk.core.protocol.SdkPojo;
import software.amazon.awssdk.core.protocol.traits.ListTrait;
import software.amazon.awssdk.core.protocol.traits.LocationTrait;
import software.amazon.awssdk.core.protocol.traits.MapTrait;
import software.amazon.awssdk.core.util.SdkAutoConstructList;
import software.amazon.awssdk.core.util.SdkAutoConstructMap;

@SdkInternalApi
public class XmlPayloadMarshaller {

    public static final XmlMarshaller<String> STRING = new BasePayloadMarshaller<String>(FROM_STRING) {
        @Override
        public void marshall(String val, XmlMarshallerContext context, String paramName,
                                SdkField<String> sdkField, ValueToStringConverter.ValueToString<String> converter) {
            context.xmlGenerator().xmlWriter().startElement(paramName).value(val).endElement();
        }
    };

    public static final XmlMarshaller<Integer> INTEGER =  new BasePayloadMarshaller<Integer>(FROM_INTEGER) {
        @Override
        public void marshall(Integer val, XmlMarshallerContext context, String paramName,
                             SdkField<Integer> sdkField, ValueToStringConverter.ValueToString<Integer> converter) {
            context.xmlGenerator().xmlWriter()
                   .startElement(paramName)
                   .value(converter.convert(val, sdkField))
                   .endElement();
        }
    };

    public static final XmlMarshaller<Long> LONG = new BasePayloadMarshaller<Long>(FROM_LONG) {

        @Override
        public void marshall(Long val, XmlMarshallerContext context, String paramName,
                             SdkField<Long> sdkField, ValueToStringConverter.ValueToString<Long> converter) {
            context.xmlGenerator().xmlWriter()
                   .startElement(paramName)
                   .value(converter.convert(val, sdkField))
                   .endElement();
        }
    };

    public static final XmlMarshaller<Float> FLOAT = new BasePayloadMarshaller<Float>(FROM_FLOAT) {

        @Override
        public void marshall(Float val, XmlMarshallerContext context, String paramName,
                             SdkField<Float> sdkField, ValueToStringConverter.ValueToString<Float> converter) {
            context.xmlGenerator().xmlWriter()
                   .startElement(paramName)
                   .value(converter.convert(val, sdkField))
                   .endElement();
        }
    };

    public static final XmlMarshaller<Double> DOUBLE = new BasePayloadMarshaller<Double>(FROM_DOUBLE) {

        @Override
        public void marshall(Double val, XmlMarshallerContext context, String paramName,
                             SdkField<Double> sdkField, ValueToStringConverter.ValueToString<Double> converter) {
            context.xmlGenerator().xmlWriter()
                   .startElement(paramName)
                   .value(converter.convert(val, sdkField))
                   .endElement();
        }
    };

    public static final XmlMarshaller<BigDecimal> BIG_DECIMAL = new BasePayloadMarshaller<BigDecimal>(FROM_BIG_DECIMAL) {

        @Override
        public void marshall(BigDecimal val, XmlMarshallerContext context, String paramName,
                             SdkField<BigDecimal> sdkField, ValueToStringConverter.ValueToString<BigDecimal> converter) {
            context.xmlGenerator().xmlWriter()
                   .startElement(paramName)
                   .value(converter.convert(val, sdkField))
                   .endElement();
        }
    };

    public static final XmlMarshaller<Boolean> BOOLEAN = new BasePayloadMarshaller<Boolean>(FROM_BOOLEAN) {

        @Override
        public void marshall(Boolean val, XmlMarshallerContext context, String paramName,
                             SdkField<Boolean> sdkField, ValueToStringConverter.ValueToString<Boolean> converter) {
            context.xmlGenerator().xmlWriter()
                   .startElement(paramName)
                   .value(converter.convert(val, sdkField))
                   .endElement();
        }
    };

    public static final XmlMarshaller<Instant> INSTANT = new BasePayloadMarshaller<Instant>(FROM_INSTANT) {

        @Override
        public void marshall(Instant val, XmlMarshallerContext context, String paramName,
                             SdkField<Instant> sdkField, ValueToStringConverter.ValueToString<Instant> converter) {
            context.xmlGenerator().xmlWriter()
                   .startElement(paramName)
                   .value(converter.convert(val, sdkField))
                   .endElement();
        }
    };

    public static final XmlMarshaller<SdkBytes> SDK_BYTES = new BasePayloadMarshaller<SdkBytes>(null) {
        @Override
        public void marshall(SdkBytes val, XmlMarshallerContext context, String paramName, SdkField<SdkBytes> sdkField,
                             ValueToStringConverter.ValueToString<SdkBytes> converter) {
            context.xmlGenerator().xmlWriter()
                   .startElement(paramName)
                   .value(val.asByteBuffer())
                   .endElement();
        }
    };

    public static final XmlMarshaller<SdkPojo> SDK_POJO = new BasePayloadMarshaller<SdkPojo>(null) {
        @Override
        public void marshall(SdkPojo val, XmlMarshallerContext context, String paramName,
                             SdkField<SdkPojo> sdkField, ValueToStringConverter.ValueToString<SdkPojo> converter) {
            context.xmlGenerator().startElement(paramName);
            context.protocolMarshaller().doMarshall(val);
            context.xmlGenerator().endElement();
        }
    };

    public static final XmlMarshaller<List> LIST = new BasePayloadMarshaller<List>(null) {
        @Override
        public void marshall(List list, XmlMarshallerContext context, String paramName,
                             SdkField<List> sdkField, ValueToStringConverter.ValueToString<List> converter) {
            ListTrait listTrait = sdkField
                .getOptionalTrait(ListTrait.class)
                .orElseThrow(() -> new IllegalStateException("SdkField of list type is missing List trait"));

            if (!listTrait.isFlattened()) {
                context.xmlGenerator().startElement(paramName);
            }

            final SdkField memberField = listTrait.memberFieldInfo();
            final LocationTrait memberLocationTrait = (LocationTrait) memberField.getTrait(LocationTrait.class);
            final String memberLocationName = listMemberLocationName(listTrait, paramName);

            for (Object listMember : list) {
                context.marshall(memberLocationTrait.location(), listMember, memberLocationName, memberField);
            }

            if (!listTrait.isFlattened()) {
                context.xmlGenerator().endElement();
            }
        }

        private String listMemberLocationName(ListTrait listTrait, String listLocationName) {
            String locationName = listTrait.memberLocationName();

            if (locationName == null) {
                locationName = listTrait.isFlattened() ? listLocationName : "member";
            }

            return locationName;
        }

        @Override
        protected boolean shouldEmit(List list, String paramName) {
            return super.shouldEmit(list, paramName) &&
                   (!list.isEmpty() || !(list instanceof SdkAutoConstructList));
        }
    };

    public static final XmlMarshaller<Map> MAP = new BasePayloadMarshaller<Map>(null) {

        @Override
        public void marshall(Map map, XmlMarshallerContext context, String paramName,
                             SdkField<Map> sdkField, ValueToStringConverter.ValueToString<Map> converter) {
            context.xmlGenerator().startElement(paramName);

            MapTrait mapTrait = sdkField
                .getOptionalTrait(MapTrait.class)
                .orElseThrow(() -> new IllegalStateException("SdkField of map type is missing MapTrait"));
            for (Map.Entry<String, ?> entry : ((Map<String, ?>) map).entrySet()) {
                context.xmlGenerator().startElement("entry");
                context.marshall(MarshallLocation.PAYLOAD, entry.getKey(), mapTrait.keyLocationName(), null);
                context.marshall(MarshallLocation.PAYLOAD, entry.getValue(),
                                 mapTrait.valueLocationName(), mapTrait.valueFieldInfo());
                context.xmlGenerator().endElement();
            }

            context.xmlGenerator().endElement();
        }

        @Override
        protected boolean shouldEmit(Map map, String paramName) {
            return super.shouldEmit(map, paramName) &&
                   (!map.isEmpty() || !(map instanceof SdkAutoConstructMap));
        }
    };

    private XmlPayloadMarshaller() {
    }

    private abstract static class BasePayloadMarshaller<T> implements XmlMarshaller<T> {

        private final ValueToStringConverter.ValueToString<T> converter;

        private BasePayloadMarshaller(ValueToStringConverter.ValueToString<T> converter) {
            this.converter = converter;
        }

        @Override
        public void marshall(T val, XmlMarshallerContext context, String paramName, SdkField<T> sdkField) {
            if (!shouldEmit(val, paramName)) {
                return;
            }

            marshall(val, context, paramName, sdkField, converter);
        }

        public abstract void marshall(T val, XmlMarshallerContext context, String paramName,
                                         SdkField<T> sdkField, ValueToStringConverter.ValueToString<T> converter);

        protected boolean shouldEmit(T val, String paramName) {
            return val != null && paramName != null;
        }
    }
}
