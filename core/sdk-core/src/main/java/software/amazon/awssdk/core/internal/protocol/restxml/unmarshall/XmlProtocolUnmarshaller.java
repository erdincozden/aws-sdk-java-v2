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

package software.amazon.awssdk.core.internal.protocol.restxml.unmarshall;

import java.util.List;
import java.util.stream.Collectors;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.protocol.MarshallLocation;
import software.amazon.awssdk.core.protocol.MarshallingType;
import software.amazon.awssdk.core.protocol.SdkField;
import software.amazon.awssdk.core.protocol.SdkPojo;
import software.amazon.awssdk.http.SdkHttpFullResponse;
import software.amazon.awssdk.utils.builder.SdkBuilder;

@SdkInternalApi
// CHECKSTYLE:OFF
//TODO WIP
public final class XmlProtocolUnmarshaller<T extends SdkPojo> {

    private static final UnmarshallerRegistry REGISTRY = createUnmarshallerRegistry();

    public T unmarshall(SdkPojo sdkPojo,
                        SdkHttpFullResponse response,
                        StaxUnmarshallerContext staxUnmarshallerContext) throws Exception {
        return unmarshall(sdkPojo, XmlUnmarshallerContext.builder()
                                                                   .response(response)
                                                                   .unmarshallerRegistry(REGISTRY)
                                                                   .xmlPayloadUnmarshallerContext(staxUnmarshallerContext)
                                                                   .build());
    }


    public T unmarshall(SdkPojo sdkPojo,
                        XmlUnmarshallerContext context) throws Exception {

        // set status code field
        sdkPojo.sdkFields().stream().filter(f -> f.location() == MarshallLocation.STATUS_CODE)
               .forEach(f -> f.set(sdkPojo, context.getUnmarshaller(MarshallLocation.STATUS_CODE, f.marshallingType())
                                                   .unmarshall(context, (SdkField) f)));

        // Set headers
        sdkPojo.sdkFields().stream().filter(f -> f.location() == MarshallLocation.HEADER)
               .forEach(f -> f.set(sdkPojo, context.getUnmarshaller(MarshallLocation.HEADER, f.marshallingType())
                                                   .unmarshall(context, (SdkField) f)));


        // Set payload members
        List<SdkField<?>> payloadFields = sdkPojo.sdkFields().stream()
                                                 .filter(f -> f.location() == MarshallLocation.PAYLOAD)
                                                 .collect(Collectors.toList());

        unmarshallPayload(sdkPojo, context, payloadFields);


        return ((SdkBuilder<?, T>) sdkPojo).build();
    }

    private void unmarshallPayload(SdkPojo sdkPojo, XmlUnmarshallerContext context, List<SdkField<?>> payloadFields)
        throws XMLStreamException {
        StaxUnmarshallerContext staxContext = (StaxUnmarshallerContext) context.payloadUnmarshallerContext();

        while (true) {
            XMLEvent xmlEvent = staxContext.nextEvent();

            if (xmlEvent.isEndDocument()) {
                break;
            }

            if (xmlEvent.isAttribute() || xmlEvent.isStartElement()) {

            }

        }

    }


    private static UnmarshallerRegistry createUnmarshallerRegistry() {
        return UnmarshallerRegistry
            .builder()
            .statusCodeUnmarshaller(MarshallingType.INTEGER, (context, field) -> context.response().statusCode())
            .headerUnmarshaller(MarshallingType.STRING, HeaderUnmarshaller.STRING)
            .headerUnmarshaller(MarshallingType.INTEGER, HeaderUnmarshaller.INTEGER)
            .headerUnmarshaller(MarshallingType.LONG, HeaderUnmarshaller.LONG)
            .headerUnmarshaller(MarshallingType.DOUBLE, HeaderUnmarshaller.DOUBLE)
            .headerUnmarshaller(MarshallingType.BOOLEAN, HeaderUnmarshaller.BOOLEAN)
            .headerUnmarshaller(MarshallingType.INSTANT, HeaderUnmarshaller.INSTANT)
            .headerUnmarshaller(MarshallingType.FLOAT, HeaderUnmarshaller.FLOAT)
            .headerUnmarshaller(MarshallingType.MAP, HeaderUnmarshaller.MAP)



//            .payloadUnmarshaller(MarshallingType.STRING, new SimpleTypeJsonUnmarshaller<>(StringToValueConverter.TO_STRING))
//            .payloadUnmarshaller(MarshallingType.INTEGER, new SimpleTypeJsonUnmarshaller<>(StringToValueConverter.TO_INTEGER))
//            .payloadUnmarshaller(MarshallingType.LONG, new SimpleTypeJsonUnmarshaller<>(StringToValueConverter.TO_LONG))
//            .payloadUnmarshaller(MarshallingType.DOUBLE, new SimpleTypeJsonUnmarshaller<>(StringToValueConverter.TO_DOUBLE))
//            .payloadUnmarshaller(MarshallingType.BIG_DECIMAL, new SimpleTypeJsonUnmarshaller<>(StringToValueConverter.TO_BIG_DECIMAL))
//            .payloadUnmarshaller(MarshallingType.BOOLEAN, new SimpleTypeJsonUnmarshaller<>(StringToValueConverter.TO_BOOLEAN))
//            .payloadUnmarshaller(MarshallingType.FLOAT, new SimpleTypeJsonUnmarshaller<>(StringToValueConverter.TO_FLOAT))
//            .payloadUnmarshaller(MarshallingType.SDK_BYTES, new SimpleTypeJsonUnmarshaller<>(StringToValueConverter.TO_SDK_BYTES))
//            .payloadUnmarshaller(MarshallingType.INSTANT, new SimpleTypeJsonUnmarshaller<>(StringToValueConverter.TO_INSTANT))
//            .payloadUnmarshaller(MarshallingType.SDK_POJO, JsonProtocolUnmarshaller::unmarshallStructured)
//            .payloadUnmarshaller(MarshallingType.LIST, JsonProtocolUnmarshaller::unmarshallList)
//            .payloadUnmarshaller(MarshallingType.MAP, JsonProtocolUnmarshaller::unmarshallMap)
            .build();
    }

}
