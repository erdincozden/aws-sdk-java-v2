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

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.internal.protocol.json.ValueToStringConverter;
import software.amazon.awssdk.core.protocol.MarshallLocation;
import software.amazon.awssdk.core.protocol.MarshallingType;
import software.amazon.awssdk.core.protocol.SdkField;
import software.amazon.awssdk.core.protocol.traits.ListTrait;

@SdkInternalApi
public class HeaderMarshaller {

    public static final XmlMarshaller<String> STRING = new SimpleHeaderMarshaller<>(ValueToStringConverter.FROM_STRING);

    public static final XmlMarshaller<Integer> INTEGER = new SimpleHeaderMarshaller<>(ValueToStringConverter.FROM_INTEGER);

    public static final XmlMarshaller<Long> LONG = new SimpleHeaderMarshaller<>(ValueToStringConverter.FROM_LONG);

    public static final XmlMarshaller<Double> DOUBLE = new SimpleHeaderMarshaller<>(ValueToStringConverter.FROM_DOUBLE);

    public static final XmlMarshaller<Float> FLOAT = new SimpleHeaderMarshaller<>(ValueToStringConverter.FROM_FLOAT);

    public static final XmlMarshaller<Boolean> BOOLEAN = new SimpleHeaderMarshaller<>(ValueToStringConverter.FROM_BOOLEAN);

    public static final XmlMarshaller<Instant> INSTANT = new SimpleHeaderMarshaller<>(ValueToStringConverter.FROM_INSTANT);

    // TODO Is it required? No shape uses this. Fix casting
    // See line 8 in MarshalHeaderMembersMacro.ftl
    public static final XmlMarshaller<List> LiST = new SimpleHeaderMarshaller<List>(null) {
        @Override
        public void marshall(List list, XmlMarshallerContext context, String paramName, SdkField<List> sdkField) {
            if (!shouldEmit(list)) {
                return;
            }

            ListTrait listTrait = sdkField
                .getOptionalTrait(ListTrait.class)
                .orElseThrow(() -> new IllegalStateException("SdkField of list type is missing List trait"));
            MarshallingType marshallingType = listTrait.memberFieldInfo().marshallingType();

            SimpleHeaderMarshaller memberMarshaller = (SimpleHeaderMarshaller)
                context.marshallerRegistry()
                       .getMarshaller(MarshallLocation.HEADER, marshallingType);

            ValueToStringConverter.ValueToString simpleTypeConverter = memberMarshaller.getConverter();

            String value = (String) list.stream()
                               .map(val -> simpleTypeConverter.convert(val, null))
                               .collect(Collectors.joining(","));

            context.request().addHeader(paramName, value);
        }

        @Override
        protected boolean shouldEmit(List list) {
            return list != null && !list.isEmpty();
        }
    };

    public static final XmlMarshaller<Map> MAP = new SimpleHeaderMarshaller<Map>(null) {
        @Override
        public void marshall(Map map, XmlMarshallerContext context, String paramName, SdkField<Map> sdkField) {
            if (!shouldEmit(map)) {
                return;
            }

            for (Map.Entry<String, String> entry : ((Map<String, String>) map).entrySet()) {
                if (entry.getKey().startsWith(paramName)) {
                    context.request().addHeader(entry.getKey(), entry.getValue());
                } else {
                    context.request().addHeader(paramName + entry.getKey(), entry.getValue());
                }
            }
        }

        @Override
        protected boolean shouldEmit(Map map) {
            return map != null && !map.isEmpty();
        }
    };


    private HeaderMarshaller() {
    }

    private static class SimpleHeaderMarshaller<T> implements XmlMarshaller<T> {
        private final ValueToStringConverter.ValueToString<T> converter;

        private SimpleHeaderMarshaller(ValueToStringConverter.ValueToString<T> converter) {
            this.converter = converter;
        }

        public ValueToStringConverter.ValueToString<T> getConverter() {
            return converter;
        }

        @Override
        public void marshall(T val, XmlMarshallerContext context, String paramName, SdkField<T> sdkField) {
            if (!shouldEmit(val)) {
                return;
            }

            context.request().addHeader(paramName, converter.convert(val, sdkField));
        }

        protected boolean shouldEmit(T val) {
            return val != null;
        }
    }
}
