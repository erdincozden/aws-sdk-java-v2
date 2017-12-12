/*
 * Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

package software.amazon.awssdk.core.util;

import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Stack;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.utils.Base64Utils;
import software.amazon.awssdk.utils.BinaryUtils;

/**
 * Utility for creating easily creating XML documents, one element at a time.
 */
public class XmlWriter {

    /** Standard XML prolog to add to the beginning of each XML document. */
    private static final String PROLOG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    /** The writer to which the XML document created by this writer will be written. */
    private final Writer writer;

    /** Optional XML namespace attribute value to include in the root element. */
    private final String xmlns;

    private Stack<String> elementStack = new Stack<String>();
    private boolean rootElement = true;


    /**
     * Creates a new XMLWriter, ready to write an XML document to the specified
     * writer.  The XML document will not specify an xmlns attribute.
     *
     * @param w
     *            The writer this XMLWriter will write to.
     */
    public XmlWriter(Writer w) {
        this(w, null);
    }

    /**
     * Creates a new XMLWriter, ready to write an XML document to the specified
     * writer. The root element in the XML document will specify an xmlns
     * attribute with the specified namespace parameter.
     *
     * @param w
     *            The writer this XMLWriter will write to.
     * @param xmlns
     *            The XML namespace to include in the xmlns attribute of the
     *            root element.
     */
    public XmlWriter(Writer w, String xmlns) {
        this.writer = w;
        this.xmlns = xmlns;
        append(PROLOG);
    }

    /**
     * Starts a new element with the specified name at the current position in
     * the in-progress XML document.
     *
     * @param element
     *            The name of the new element.
     *
     * @return This XMLWriter so that additional method calls can be chained
     *         together.
     */
    public XmlWriter startElement(String element) {
        append("<" + element);
        if (rootElement && xmlns != null) {
            append(" xmlns=\"" + xmlns + "\"");
            rootElement = false;
        }
        append(">");
        elementStack.push(element);
        return this;
    }

    /**
     * Closes the last opened element at the current position in the in-progress
     * XML document.
     *
     * @return This XMLWriter so that additional method calls can be chained
     *         together.
     */
    public XmlWriter endElement() {
        String lastElement = elementStack.pop();
        append("</" + lastElement + ">");
        return this;
    }

    /**
     * Adds the specified value as text to the current position of the in
     * progress XML document.
     *
     * @param s
     *            The text to add to the XML document.
     *
     * @return This XMLWriter so that additional method calls can be chained
     *         together.
     */
    public XmlWriter value(String s) {
        append(escapeXmlEntities(s));
        return this;
    }

    /**
     * Adds the specified value as Base64 encoded text to the current position of the in
     * progress XML document.
     *
     * @param b
     *            The binary data to add to the XML document.
     *
     * @return This XMLWriter so that additional method calls can be chained
     *         together.
     */
    public XmlWriter value(ByteBuffer b) {
        append(escapeXmlEntities(Base64Utils.encodeAsString(BinaryUtils.copyBytesFrom(b))));
        return this;
    }

    /**
     * Adds the specified date as text to the current position of the
     * in-progress XML document.
     *
     * @param date
     *            The date to add to the XML document.
     *
     * @return This XMLWriter so that additional method calls can be chained
     *         together.
     */
    public XmlWriter value(Date date) {
        append(escapeXmlEntities(DateUtils.formatIso8601Date(date.toInstant())));
        return this;
    }

    /**
     * Adds the string representation of the specified object to the current
     * position of the in progress XML document.
     *
     * @param obj
     *            The object to translate to a string and add to the XML
     *            document.
     *
     * @return This XMLWriter so that additional method calls can be chained
     *         together.
     */
    public XmlWriter value(Object obj) {
        append(escapeXmlEntities(obj.toString()));
        return this;
    }

    private void append(String s) {
        try {
            writer.append(s);
        } catch (IOException e) {
            throw new SdkClientException("Unable to write XML document", e);
        }
    }

    private String escapeXmlEntities(String s) {
        /**
         * Unescape any escaped characters.
         */
        if (s.contains("&")) {
            s = s.replace("&quot;", "\"");
            s = s.replace("&apos;", "'");
            s = s.replace("&lt;", "<");
            s = s.replace("&gt;", ">");
            // Ampersands should always be the last to unescape
            s = s.replace("&amp;", "&");
        }
        // Ampersands should always be the first to escape
        s = s.replace("&", "&amp;");
        s = s.replace("\"", "&quot;");
        s = s.replace("'", "&apos;");
        s = s.replace("<", "&lt;");
        s = s.replace(">", "&gt;");
        return s;
    }

}
