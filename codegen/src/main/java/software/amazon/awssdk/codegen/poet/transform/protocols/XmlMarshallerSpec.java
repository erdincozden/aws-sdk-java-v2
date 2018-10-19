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

package software.amazon.awssdk.codegen.poet.transform.protocols;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;
import software.amazon.awssdk.awscore.protocol.xml.AwsXmlProtocolFactory;
import software.amazon.awssdk.codegen.model.intermediate.IntermediateModel;
import software.amazon.awssdk.codegen.model.intermediate.Metadata;
import software.amazon.awssdk.codegen.model.intermediate.ShapeModel;
import software.amazon.awssdk.core.Request;
import software.amazon.awssdk.core.http.HttpMethodName;
import software.amazon.awssdk.core.protocol.OperationInfo;
import software.amazon.awssdk.core.protocol.ProtocolMarshaller;
import software.amazon.awssdk.utils.StringUtils;

/**
 * MarshallerSpec for Xml protocol
 */
public class XmlMarshallerSpec implements MarshallerProtocolSpec {
    private final Metadata metadata;
    private final ShapeModel shapeModel;

    public XmlMarshallerSpec(IntermediateModel model, ShapeModel shapeModel) {
        this.metadata = model.getMetadata();
        this.shapeModel = shapeModel;
    }

    @Override
    public ParameterSpec protocolFactoryParameter() {
        return ParameterSpec.builder(AwsXmlProtocolFactory.class, "protocolFactory").build();
    }

    @Override
    public Optional<MethodSpec> constructor() {
        return Optional.of(MethodSpec.constructorBuilder()
                                     .addModifiers(Modifier.PUBLIC)
                                     .addParameter(protocolFactoryParameter())
                                     .addStatement("this.protocolFactory = protocolFactory")
                                     .build());
    }

    @Override
    public CodeBlock marshalCodeBlock(ClassName requestClassName) {
        String variableName = shapeModel.getVariable().getVariableName();
        return CodeBlock.builder()
                        .addStatement("$T<$T<$T>> protocolMarshaller = protocolFactory.createProtocolMarshaller"
                                      + "(SDK_OPERATION_BINDING, $L, xmlNameSpace)",
                                      ProtocolMarshaller.class, Request.class,
                                      requestClassName, variableName)
                        .addStatement("return protocolMarshaller.marshall($L)", variableName)
                        .build();
    }

    @Override
    public FieldSpec protocolFactory() {
        return FieldSpec.builder(AwsXmlProtocolFactory.class, "protocolFactory")
                        .addModifiers(Modifier.PRIVATE, Modifier.FINAL).build();
    }

    @Override
    public List<FieldSpec> memberVariables() {
        List<FieldSpec> fields = new ArrayList<>();

        CodeBlock.Builder initializationCodeBlockBuilder = CodeBlock.builder()
                                                                    .add("$T.builder()", OperationInfo.class);
        initializationCodeBlockBuilder.add(".requestUri($S)", shapeModel.getMarshaller().getRequestUri())
                                      .add(".httpMethodName($T.$L)", HttpMethodName.class, shapeModel.getMarshaller().getVerb())
                                      .add(".hasExplicitPayloadMember($L)", shapeModel.isHasPayloadMember() ||
                                                                            shapeModel.getExplicitEventPayloadMember() != null)
                                      .add(".hasPayloadMembers($L)", shapeModel.hasPayloadMembers())
                                      .add(".serviceName($S)", metadata.getServiceName());

        if (StringUtils.isNotBlank(shapeModel.getMarshaller().getTarget())) {
            initializationCodeBlockBuilder.add(".operationIdentifier($S)", shapeModel.getMarshaller().getTarget());
        }

        if (shapeModel.isHasStreamingMember()) {
            initializationCodeBlockBuilder.add(".hasStreamingInput(true)");
        }

        CodeBlock codeBlock = initializationCodeBlockBuilder.add(".build()").build();

        FieldSpec.Builder instance = FieldSpec.builder(ClassName.get(OperationInfo.class), "SDK_OPERATION_BINDING")
                                              .addModifiers(Modifier.PRIVATE, Modifier.FINAL, Modifier.STATIC)
                                              .initializer(codeBlock);

        FieldSpec xmlNameSpaceUri = FieldSpec.builder(ClassName.get(String.class), "xmlNameSpace")
                                                  .addModifiers(Modifier.PRIVATE, Modifier.FINAL, Modifier.STATIC)
                                                  .initializer("$S", xmlNameSpaceUri())
                                                  .build();

        fields.add(xmlNameSpaceUri);
        fields.add(instance.build());
        fields.add(protocolFactory());
        return fields;
    }

    // TODO Not generating xmlnamespace for RestXmlTypes shape
    private String xmlNameSpaceUri() {
        Set<String> xmlUris = shapeModel.getMembers().stream()
                                        .filter(m -> m.getXmlNameSpaceUri() != null)
                                        .map(m -> m.getXmlNameSpaceUri())
                                        .collect(Collectors.toSet());

        if (xmlUris.isEmpty()) {
            return null;
        } else if (xmlUris.size() == 1) {
            return xmlUris.iterator().next();
        } else {
            throw new RuntimeException("Request has more than 1 xmlNameSpace uri.");
        }
    }
}
