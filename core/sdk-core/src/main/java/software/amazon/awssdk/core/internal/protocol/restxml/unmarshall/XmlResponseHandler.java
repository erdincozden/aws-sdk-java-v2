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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.SdkStandardLogger;
import software.amazon.awssdk.core.http.HttpResponseHandler;
import software.amazon.awssdk.core.interceptor.ExecutionAttributes;
import software.amazon.awssdk.core.protocol.SdkPojo;
import software.amazon.awssdk.http.AbortableInputStream;
import software.amazon.awssdk.http.SdkHttpFullResponse;
import software.amazon.awssdk.utils.Logger;
import software.amazon.awssdk.utils.XmlUtils;

// CHECKSTYLE:OFF
@SdkInternalApi
//TODO WIP
public class XmlResponseHandler<T extends SdkPojo> implements HttpResponseHandler<T> {
    private static final Logger log = Logger.loggerFor(XmlResponseHandler.class);

    private XmlProtocolUnmarshaller<T> unmarshaller;
    private final Function<SdkHttpFullResponse, SdkPojo> pojoSupplier;
    private final StaxOperationMetadata operationMetadata;

    public XmlResponseHandler(XmlProtocolUnmarshaller<T> unmarshaller,
                              Function<SdkHttpFullResponse, SdkPojo> pojoSupplier,
                              StaxOperationMetadata operationMetadata) {
        this.unmarshaller = unmarshaller;
        this.pojoSupplier = pojoSupplier;
        this.operationMetadata = operationMetadata;
    }

    @Override
    public T handle(SdkHttpFullResponse response, ExecutionAttributes executionAttributes) throws Exception {
        SdkStandardLogger.REQUEST_LOGGER.trace(() -> "Parsing service response XML.");

        SdkStandardLogger.REQUEST_ID_LOGGER.debug(() -> X_AMZN_REQUEST_ID_HEADER + " : " +
                                                        response.firstMatchingHeader(X_AMZN_REQUEST_ID_HEADER)
                                                                .orElse("not available"));


        T result = unmarshalResponse(response);
        SdkStandardLogger.REQUEST_LOGGER.trace(() -> "Done parsing service response.");
        return result;
    }

    private T unmarshalResponse(SdkHttpFullResponse response) throws Exception {
        XMLEventReader eventReader = null;
        try {
            InputStream content;

            if (!response.content().isPresent() || operationMetadata.isHasStreamingSuccessResponse()) {
                content = AbortableInputStream.create(new ByteArrayInputStream("<eof/>".getBytes(StandardCharsets.UTF_8)));
            } else {
                content = response.content().get();
            }

            eventReader = XmlUtils.xmlInputFactory().createXMLEventReader(content);

            StaxUnmarshallerContext unmarshallerContext = new StaxUnmarshallerContext(eventReader, response.headers());
            // TODO Move to AwsXmlResponseHandler
//            unmarshallerContext.registerMetadataExpression("ResponseMetadata/RequestId", 2, AWS_REQUEST_ID);
//            unmarshallerContext.registerMetadataExpression("requestId", 2, AWS_REQUEST_ID);

            return unmarshaller.unmarshall(pojoSupplier.apply(response), response, unmarshallerContext);

        } finally {
            try {
                if (eventReader != null) {
                    eventReader.close();
                }
            } catch (XMLStreamException e) {
                log.warn(() -> "Error closing XML parser.", e);
            }
        }
    }
}
