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

package software.amazon.awssdk.awscore.protocol.xml;

import java.util.function.Function;
import java.util.function.Supplier;
import software.amazon.awssdk.annotations.SdkProtectedApi;
import software.amazon.awssdk.awscore.AwsRequest;
import software.amazon.awssdk.core.Request;
import software.amazon.awssdk.core.http.HttpResponseHandler;
import software.amazon.awssdk.core.internal.protocol.restxml.SdkXmlGenerator;
import software.amazon.awssdk.core.internal.protocol.restxml.XmlGenerator;
import software.amazon.awssdk.core.internal.protocol.restxml.XmlProtocolMarshallerBuilder;
import software.amazon.awssdk.core.internal.protocol.restxml.unmarshall.StaxOperationMetadata;
import software.amazon.awssdk.core.internal.protocol.restxml.unmarshall.XmlProtocolUnmarshaller;
import software.amazon.awssdk.core.internal.protocol.restxml.unmarshall.XmlResponseHandler;
import software.amazon.awssdk.core.protocol.OperationInfo;
import software.amazon.awssdk.core.protocol.ProtocolMarshaller;
import software.amazon.awssdk.core.protocol.SdkPojo;
import software.amazon.awssdk.http.SdkHttpFullResponse;

/**
 * Factory to generate the various XML protocol handlers and generators
 * to be used for communicating with the service.
 */
@SdkProtectedApi
public final class AwsXmlProtocolFactory {

    private AwsXmlProtocolFactory(Builder builder) {
    }


    public <T extends AwsRequest> ProtocolMarshaller<Request<T>> createProtocolMarshaller(OperationInfo operationInfo,
                                                                                          T origRequest,
                                                                                          String xmlNameSpaceUri) {
        return XmlProtocolMarshallerBuilder.<T>standard()
            .xmlGenerator(createGenerator(operationInfo, xmlNameSpaceUri))
            .originalRequest(origRequest)
            .operationInfo(operationInfo)
            .build();
    }

    /**
     * Creates a new response handler with the given {@link StaxOperationMetadata} and a supplier of the POJO response
     * type.
     *
     * @param operationMetadata Metadata about operation being unmarshalled.
     * @param pojoSupplier {@link Supplier} of the POJO response type.
     * @param <T> Type being unmarshalled.
     * @return HttpResponseHandler that will handle the HTTP response and unmarshall into a POJO.
     */
    public <T extends SdkPojo> HttpResponseHandler<T> createResponseHandler(StaxOperationMetadata operationMetadata,
                                                                            Supplier<SdkPojo> pojoSupplier) {
        return createResponseHandler(operationMetadata, r -> pojoSupplier.get());
    }

    // TODO Not done
    public <T extends SdkPojo> HttpResponseHandler<T> createResponseHandler(StaxOperationMetadata operationMetadata,
                                                                            Function<SdkHttpFullResponse, SdkPojo> pojoSupplier) {
        XmlProtocolUnmarshaller<T> unmarshaller = new XmlProtocolUnmarshaller<>();
        return new XmlResponseHandler<>(unmarshaller, pojoSupplier, operationMetadata);
    }

    private XmlGenerator createGenerator(OperationInfo operationInfo, String xmlNameSpaceUri) {
        return operationInfo.hasPayloadMembers() ? SdkXmlGenerator.create(xmlNameSpaceUri) : null;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link AwsXmlProtocolFactory}.
     */
    public static final class Builder {

        public AwsXmlProtocolFactory build() {
            return new AwsXmlProtocolFactory(this);
        }
    }
}
