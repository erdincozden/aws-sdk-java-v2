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

import java.io.StringWriter;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.util.xml.XmlWriter;

/**
 * Wrapper around the {@link XmlWriter} for marshalling requests for XML protocol
 */
@SdkInternalApi
public class SdkXmlGenerator implements XmlGenerator {

    private final StringWriter stringWriter;
    private final XmlWriter xmlWriter;

    private SdkXmlGenerator(StringWriter stringWriter, XmlWriter xmlWriter) {
        this.stringWriter = stringWriter;
        this.xmlWriter = xmlWriter;
    }

    public static SdkXmlGenerator create(String xmlns) {
        final StringWriter stringWriter = new StringWriter();
        return new SdkXmlGenerator(stringWriter, new XmlWriter(stringWriter, xmlns));
    }

    @Override
    public XmlWriter xmlWriter() {
        return xmlWriter;
    }

    @Override
    public StringWriter stringWriter() {
        return stringWriter;
    }

    @Override
    public void startElement(String element) {
        xmlWriter.startElement(element);
    }

    @Override
    public void endElement() {
        xmlWriter.endElement();
    }
}
