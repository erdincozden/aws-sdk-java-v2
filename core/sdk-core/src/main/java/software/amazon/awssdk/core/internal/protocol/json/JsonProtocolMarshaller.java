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

package software.amazon.awssdk.core.internal.protocol.json;

import static software.amazon.awssdk.http.Header.CONTENT_LENGTH;
import static software.amazon.awssdk.http.Header.CONTENT_TYPE;

import java.io.ByteArrayInputStream;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.DefaultRequest;
import software.amazon.awssdk.core.Request;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.protocol.MarshallingType;
import software.amazon.awssdk.core.protocol.OperationInfo;
import software.amazon.awssdk.core.protocol.ProtocolRequestMarshaller;
import software.amazon.awssdk.core.protocol.SdkField;
import software.amazon.awssdk.core.protocol.SdkPojo;
import software.amazon.awssdk.core.protocol.json.StructuredJsonGenerator;
import software.amazon.awssdk.core.protocol.traits.DefaultValueTrait;
import software.amazon.awssdk.core.protocol.traits.PayloadTrait;
import software.amazon.awssdk.core.util.UriResourcePathUtils;

/**
 * Implementation of {@link ProtocolRequestMarshaller} for JSON based services. This includes JSON-RPC and REST-JSON.
 *
 * @param <OrigRequestT> Type of the original request object.
 */
@SdkInternalApi
public class JsonProtocolMarshaller<OrigRequestT> implements ProtocolRequestMarshaller<OrigRequestT> {

    private static final MarshallerRegistry MARSHALLER_REGISTRY = createMarshallerRegistry();

    private final StructuredJsonGenerator jsonGenerator;
    private final Request<OrigRequestT> request;
    private final String contentType;
    private final boolean hasExplicitPayloadMember;
    private final boolean hasStreamingInput;

    private final JsonMarshallerContext marshallerContext;

    public JsonProtocolMarshaller(StructuredJsonGenerator jsonGenerator,
                                  String contentType,
                                  OperationInfo operationInfo,
                                  OrigRequestT originalRequest) {
        this.jsonGenerator = jsonGenerator;
        this.contentType = contentType;
        this.hasExplicitPayloadMember = operationInfo.hasExplicitPayloadMember();
        this.hasStreamingInput = operationInfo.hasStreamingInput();
        this.request = fillBasicRequestParams(operationInfo, originalRequest);
        this.marshallerContext = JsonMarshallerContext.builder()
                                                      .jsonGenerator(jsonGenerator)
                                                      .marshallerRegistry(MARSHALLER_REGISTRY)
                                                      .protocolHandler(this)
                                                      .request(request)
                                                      .build();
    }

    private Request<OrigRequestT> fillBasicRequestParams(OperationInfo operationInfo, OrigRequestT originalRequest) {
        Request<OrigRequestT> request = createRequest(operationInfo, originalRequest);
        request.setHttpMethod(operationInfo.httpMethodName());
        request.setResourcePath(UriResourcePathUtils.addStaticQueryParametersToRequest(request, operationInfo.requestUri()));
        if (operationInfo.operationIdentifier() != null) {
            request.addHeader("X-Amz-Target", operationInfo.operationIdentifier());
        }
        return request;
    }

    private DefaultRequest<OrigRequestT> createRequest(OperationInfo operationInfo, OrigRequestT originalRequest) {
        return new DefaultRequest<>(originalRequest, operationInfo.serviceName());
    }

    private static MarshallerRegistry createMarshallerRegistry() {
        return MarshallerRegistry
            .builder()
            .payloadMarshaller(MarshallingType.STRING, SimpleTypeJsonMarshaller.STRING)
            .payloadMarshaller(MarshallingType.INTEGER, SimpleTypeJsonMarshaller.INTEGER)
            .payloadMarshaller(MarshallingType.LONG, SimpleTypeJsonMarshaller.LONG)
            .payloadMarshaller(MarshallingType.DOUBLE, SimpleTypeJsonMarshaller.DOUBLE)
            .payloadMarshaller(MarshallingType.FLOAT, SimpleTypeJsonMarshaller.FLOAT)
            .payloadMarshaller(MarshallingType.BIG_DECIMAL, SimpleTypeJsonMarshaller.BIG_DECIMAL)
            .payloadMarshaller(MarshallingType.BOOLEAN, SimpleTypeJsonMarshaller.BOOLEAN)
            .payloadMarshaller(MarshallingType.INSTANT, SimpleTypeJsonMarshaller.INSTANT)
            .payloadMarshaller(MarshallingType.SDK_BYTES, SimpleTypeJsonMarshaller.SDK_BYTES)
            .payloadMarshaller(MarshallingType.SDK_POJO, SimpleTypeJsonMarshaller.SDK_POJO)
            .payloadMarshaller(MarshallingType.LIST, SimpleTypeJsonMarshaller.LIST)
            .payloadMarshaller(MarshallingType.MAP, SimpleTypeJsonMarshaller.MAP)
            .payloadMarshaller(MarshallingType.NULL, SimpleTypeJsonMarshaller.NULL)

            .headerMarshaller(MarshallingType.STRING, HeaderMarshaller.STRING)
            .headerMarshaller(MarshallingType.INTEGER, HeaderMarshaller.INTEGER)
            .headerMarshaller(MarshallingType.LONG, HeaderMarshaller.LONG)
            .headerMarshaller(MarshallingType.DOUBLE, HeaderMarshaller.DOUBLE)
            .headerMarshaller(MarshallingType.FLOAT, HeaderMarshaller.FLOAT)
            .headerMarshaller(MarshallingType.BOOLEAN, HeaderMarshaller.BOOLEAN)
            .headerMarshaller(MarshallingType.INSTANT, HeaderMarshaller.INSTANT)
            .headerMarshaller(MarshallingType.NULL, JsonMarshaller.NULL)

            .queryParamMarshaller(MarshallingType.STRING, QueryParamMarshaller.STRING)
            .queryParamMarshaller(MarshallingType.INTEGER, QueryParamMarshaller.INTEGER)
            .queryParamMarshaller(MarshallingType.LONG, QueryParamMarshaller.LONG)
            .queryParamMarshaller(MarshallingType.DOUBLE, QueryParamMarshaller.DOUBLE)
            .queryParamMarshaller(MarshallingType.FLOAT, QueryParamMarshaller.FLOAT)
            .queryParamMarshaller(MarshallingType.BOOLEAN, QueryParamMarshaller.BOOLEAN)
            .queryParamMarshaller(MarshallingType.INSTANT, QueryParamMarshaller.INSTANT)
            .queryParamMarshaller(MarshallingType.LIST, QueryParamMarshaller.LIST)
            .queryParamMarshaller(MarshallingType.MAP, QueryParamMarshaller.MAP)
            .queryParamMarshaller(MarshallingType.NULL, JsonMarshaller.NULL)

            .pathParamMarshaller(MarshallingType.STRING, SimpleTypePathMarshaller.STRING)
            .pathParamMarshaller(MarshallingType.INTEGER, SimpleTypePathMarshaller.INTEGER)
            .pathParamMarshaller(MarshallingType.LONG, SimpleTypePathMarshaller.LONG)
            .pathParamMarshaller(MarshallingType.NULL, SimpleTypePathMarshaller.NULL)

            .greedyPathParamMarshaller(MarshallingType.STRING, SimpleTypePathMarshaller.GREEDY_STRING)
            .greedyPathParamMarshaller(MarshallingType.NULL, SimpleTypePathMarshaller.NULL)
            .build();
    }

    /**
     * If there is not an explicit payload member then we need to start the implicit JSON request object. All
     * members bound to the payload will be added as fields to this object.
     */
    private void startMarshalling() {
        if (!hasExplicitPayloadMember) {
            jsonGenerator.writeStartObject();
        }
    }

    public void doMarshall(SdkPojo pojo) {
        for (SdkField<?> field : pojo.sdkFields()) {
            Object val = resolveValue(field.get(pojo), field);
            if (isBinary(field, val)) {
                request.setContentProvider(((SdkBytes) val)::asInputStream);
            } else {
                if (val != null && field.containsTrait(PayloadTrait.class)) {
                    jsonGenerator.writeStartObject();
                    doMarshall((SdkPojo) val);
                    jsonGenerator.writeEndObject();
                } else {
                    MARSHALLER_REGISTRY.getMarshaller(field.location(), field.marshallingType(), val)
                                       .marshall(val, marshallerContext, field.locationName(), (SdkField<Object>) field);
                }
            }
        }
    }

    private boolean isBinary(SdkField<?> field, Object val) {
        return isExplicitPayloadMember(field) && val instanceof SdkBytes;
    }

    private boolean isExplicitPayloadMember(SdkField<?> field) {
        return field.containsTrait(PayloadTrait.class);
    }

    @Override
    public Request<OrigRequestT> marshall(SdkPojo pojo) {
        startMarshalling();
        doMarshall(pojo);
        return finishMarshalling();
    }

    private Object resolveValue(Object val, SdkField<?> marshallingInfo) {
        DefaultValueTrait trait = marshallingInfo.getTrait(DefaultValueTrait.class);
        return trait == null ? val : trait.resolveValue(val);
    }

    private Request<OrigRequestT> finishMarshalling() {
        // Content may already be set if the payload is binary data.
        if (!request.getContentStreamProvider().isPresent()) {
            // End the implicit request object if needed.
            if (!hasExplicitPayloadMember) {
                jsonGenerator.writeEndObject();
            }

            byte[] content = jsonGenerator.getBytes();
            request.setContentProvider(() -> new ByteArrayInputStream(content));
            if (content.length > 0) {
                request.addHeader(CONTENT_LENGTH, Integer.toString(content.length));
            }
        }

        // We skip setting the default content type if the request is streaming as
        // content-type is determined based on the body of the stream
        if (!request.getHeaders().containsKey(CONTENT_TYPE) && contentType != null && !hasStreamingInput) {
            request.addHeader(CONTENT_TYPE, contentType);
        }
        return request;
    }

}
