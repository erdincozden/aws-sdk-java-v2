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
import java.util.Optional;
import java.util.stream.Collectors;
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
                        XmlUnmarshallerContext context) {

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

    private void unmarshallPayload(SdkPojo sdkPojo, XmlUnmarshallerContext unmarshallerContext,
                                   List<SdkField<?>> payloadFields) {

        if (payloadFields.isEmpty())
            return;

        StaxUnmarshallerContext context = unmarshallerContext.payloadUnmarshallerContext();

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
                SdkField<?> currentField = getFieldFromLocation(payloadFields, locationName);

                if (currentField != null && context.testExpression(locationName, targetDepth)) {
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
    }

    private static SdkField<?> getFieldFromLocation(List<SdkField<?>> payloadFields, String locationName) {
        return payloadFields.stream()
                           .filter(f -> f.locationName().equals(locationName))
                           .findAny()
                            .orElse(null);
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

            .payloadUnmarshaller(MarshallingType.STRING, XmlPayloadUnmarshaller.STRING)
            .payloadUnmarshaller(MarshallingType.INTEGER, XmlPayloadUnmarshaller.INTEGER)
            .payloadUnmarshaller(MarshallingType.LONG, XmlPayloadUnmarshaller.LONG)
            .payloadUnmarshaller(MarshallingType.FLOAT, XmlPayloadUnmarshaller.FLOAT)
            .payloadUnmarshaller(MarshallingType.DOUBLE,XmlPayloadUnmarshaller.DOUBLE)
            .payloadUnmarshaller(MarshallingType.BIG_DECIMAL, XmlPayloadUnmarshaller.BIG_DECIMAL)
            .payloadUnmarshaller(MarshallingType.BOOLEAN, XmlPayloadUnmarshaller.BOOLEAN)
            .payloadUnmarshaller(MarshallingType.INSTANT, XmlPayloadUnmarshaller.INSTANT)
            .payloadUnmarshaller(MarshallingType.SDK_BYTES, XmlPayloadUnmarshaller.SDK_BYTES)
            //.payloadUnmarshaller(MarshallingType.SDK_POJO, XmlPayloadUnmarshaller.SDK_POJO)
            .payloadUnmarshaller(MarshallingType.SDK_POJO, XmlPayloadUnmarshaller::unmarshallSdkPojo)
            .payloadUnmarshaller(MarshallingType.LIST, XmlPayloadUnmarshaller::unmarshallList)
            .payloadUnmarshaller(MarshallingType.MAP, XmlPayloadUnmarshaller::unmarshallMap)
            .build();
    }

}
