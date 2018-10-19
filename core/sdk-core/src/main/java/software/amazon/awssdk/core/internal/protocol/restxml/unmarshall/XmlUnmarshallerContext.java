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

import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.protocol.MarshallLocation;
import software.amazon.awssdk.core.protocol.MarshallingType;
import software.amazon.awssdk.http.SdkHttpFullResponse;

@SdkInternalApi
public class XmlUnmarshallerContext {
    private final SdkHttpFullResponse response;
    private final UnmarshallerRegistry unmarshallerRegistry;
    private final StaxUnmarshallerContext payloadUnmarshallerContext;

    private XmlUnmarshallerContext(Builder builder) {
        this.response = builder.response;
        this.unmarshallerRegistry = builder.unmarshallerRegistry;
        this.payloadUnmarshallerContext = builder.payloadUnmarshallerContext;
    }

    /**
     * @return The {@link SdkHttpFullResponse} of the API call.
     */
    public SdkHttpFullResponse response() {
        return response;
    }

    /**
     * Lookup the marshaller for the given location andtype.
     *
     * @param location {@link MarshallLocation} of member.
     * @param marshallingType {@link MarshallingType} of member.
     * @return Unmarshaller implementation.
     * @throws SdkClientException if no unmarshaller is found.
     */
    public XmlUnmarshaller<Object> getUnmarshaller(MarshallLocation location, MarshallingType<?> marshallingType) {
        XmlUnmarshaller<Object> unmarshaller = unmarshallerRegistry.getUnmarshaller(location, marshallingType);
        if (unmarshaller == null) {
            throw SdkClientException.create(String.format("No unmarshaller registered for type %s at location %s",
                                                          marshallingType, location));
        }
        return unmarshaller;
    }

    public StaxUnmarshallerContext payloadUnmarshallerContext() {
        return payloadUnmarshallerContext;
    }

    /**
     * @return Builder instance to construct a {@link XmlUnmarshallerContext}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for a {@link XmlUnmarshallerContext}.
     */
    public static final class Builder {

        private SdkHttpFullResponse response;
        private UnmarshallerRegistry unmarshallerRegistry;
        private StaxUnmarshallerContext payloadUnmarshallerContext;

        private Builder() {
        }

        public Builder response(SdkHttpFullResponse response) {
            this.response = response;
            return this;
        }

        public Builder unmarshallerRegistry(UnmarshallerRegistry unmarshallerRegistry) {
            this.unmarshallerRegistry = unmarshallerRegistry;
            return this;
        }


        public Builder xmlPayloadUnmarshallerContext(StaxUnmarshallerContext payloadUnmarshallerContext) {
            this.payloadUnmarshallerContext = payloadUnmarshallerContext;
            return this;
        }

        /**
         * @return An immutable {@link XmlUnmarshallerContext} object.
         */
        public XmlUnmarshallerContext build() {
            return new XmlUnmarshallerContext(this);
        }
    }

}
