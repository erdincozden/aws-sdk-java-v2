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

import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.SdkRequest;
import software.amazon.awssdk.core.protocol.OperationInfo;
import software.amazon.awssdk.core.protocol.ProtocolRequestMarshaller;

/**
 * Builder to create an appropriate implementation of {@link ProtocolRequestMarshaller} for JSON based services.
 *
 * @param <T> Type of the original request object.
 */
@SdkInternalApi
public class XmlProtocolMarshallerBuilder<T extends SdkRequest>  {

    private XmlGenerator xmlGenerator;
    private T originalRequest;
    private OperationInfo operationInfo;

    public static <T extends SdkRequest> XmlProtocolMarshallerBuilder<T> standard() {
        return new XmlProtocolMarshallerBuilder<T>();
    }

    public XmlProtocolMarshaller<T> build() {
        return new XmlProtocolMarshaller<T>(xmlGenerator, originalRequest, operationInfo);
    }

    public XmlProtocolMarshallerBuilder<T> xmlGenerator(XmlGenerator xmlGenerator) {
        this.xmlGenerator = xmlGenerator;
        return this;
    }

    public XmlProtocolMarshallerBuilder<T> originalRequest(T originalRequest) {
        this.originalRequest = originalRequest;
        return this;
    }

    public XmlProtocolMarshallerBuilder<T> operationInfo(OperationInfo operationInfo) {
        this.operationInfo = operationInfo;
        return this;
    }
}
