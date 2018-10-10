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
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.internal.protocol.json.ValueToStringConverter;
import software.amazon.awssdk.core.protocol.MarshallLocation;
import software.amazon.awssdk.core.protocol.SdkField;
import software.amazon.awssdk.core.protocol.traits.ListTrait;
import software.amazon.awssdk.core.protocol.traits.MapTrait;

@SdkInternalApi
public final class QueryParamMarshaller {

    public static final XmlMarshaller<String> STRING = new SimpleQueryParamMarshaller<>(ValueToStringConverter.FROM_STRING);

    public static final XmlMarshaller<Integer> INTEGER = new SimpleQueryParamMarshaller<>(ValueToStringConverter.FROM_INTEGER);

    public static final XmlMarshaller<Long> LONG = new SimpleQueryParamMarshaller<>(ValueToStringConverter.FROM_LONG);

    public static final XmlMarshaller<Double> DOUBLE = new SimpleQueryParamMarshaller<>(ValueToStringConverter.FROM_DOUBLE);

    public static final XmlMarshaller<Float> FLOAT = new SimpleQueryParamMarshaller<>(ValueToStringConverter.FROM_FLOAT);

    public static final XmlMarshaller<Boolean> BOOLEAN = new SimpleQueryParamMarshaller<>(ValueToStringConverter.FROM_BOOLEAN);

    public static final XmlMarshaller<Instant> INSTANT = new SimpleQueryParamMarshaller<>(ValueToStringConverter.FROM_INSTANT);

    public static final XmlMarshaller<List> LIST = (list, context, paramName, sdkField) -> {
        if (list == null || list.isEmpty()) {
            return;
        }

        for (Object member : list) {
            context.marshall(MarshallLocation.QUERY_PARAM, member, paramName, null);
        }
    };

    public static final XmlMarshaller<Map> MAP = (map, context, paramName, sdkField) -> {
        if (map == null || map.isEmpty()) {
            return;
        }

        final MapTrait mapTrait = sdkField
            .getOptionalTrait(MapTrait.class)
            .orElseThrow(() -> new IllegalStateException("SdkField of list type is missing List trait"));
        final SdkField valueField = mapTrait.valueFieldInfo();

        for (Map.Entry<?, ?> mapEntry : ((Map<?, ?>) map).entrySet()) {
            SimpleQueryParamMarshaller keyMarshaller = (SimpleQueryParamMarshaller)
                context.marshallerRegistry().getMarshaller(MarshallLocation.QUERY_PARAM, mapEntry.getKey());

            if (valueField.containsTrait(ListTrait.class)) {
                context.request().addParameters(keyMarshaller.convert(mapEntry.getKey(), null),
                                                (List<String>) mapEntry.getValue());
            } else {
                SimpleQueryParamMarshaller valueMarshaller = (SimpleQueryParamMarshaller)
                    context.marshallerRegistry().getMarshaller(MarshallLocation.QUERY_PARAM, mapEntry.getValue());

                context.request().addParameter(keyMarshaller.convert(mapEntry.getKey(), null),
                                               valueMarshaller.convert(mapEntry.getValue(), null));
            }
        }
    };

    private QueryParamMarshaller() {
    }

    private static class SimpleQueryParamMarshaller<T> implements XmlMarshaller<T> {

        private final ValueToStringConverter.ValueToString<T> converter;

        private SimpleQueryParamMarshaller(ValueToStringConverter.ValueToString<T> converter) {
            this.converter = converter;
        }

        @Override
        public void marshall(T val, XmlMarshallerContext context, String paramName, SdkField<T> sdkField) {
            context.request().addParameter(paramName, converter.convert(val, sdkField));
        }

        public String convert(T val, SdkField<T> sdkField) {
            return converter.convert(val, sdkField);
        }
    }
}
