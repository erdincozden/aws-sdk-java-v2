import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.xml.stream.XMLEventReader;
import org.junit.Test;
import software.amazon.awssdk.awscore.protocol.xml.AwsXmlProtocolFactory;
import software.amazon.awssdk.core.Request;
import software.amazon.awssdk.core.internal.protocol.restxml.unmarshall.StaxUnmarshallerContext;
import software.amazon.awssdk.core.internal.protocol.restxml.unmarshall.XmlProtocolUnmarshaller;
import software.amazon.awssdk.core.protocol.SdkPojo;
import software.amazon.awssdk.http.AbortableInputStream;
import software.amazon.awssdk.http.SdkHttpFullResponse;
import software.amazon.awssdk.services.protocolrestxml.ProtocolRestXmlClient;
import software.amazon.awssdk.services.protocolrestxml.model.RestXmlTypesRequest;
import software.amazon.awssdk.services.protocolrestxml.model.RestXmlTypesResponse;
import software.amazon.awssdk.services.protocolrestxml.model.SimpleStruct;
import software.amazon.awssdk.services.protocolrestxml.transform.RestXmlTypesRequestMarshaller;
import software.amazon.awssdk.utils.ImmutableMap;
import software.amazon.awssdk.utils.IoUtils;
import software.amazon.awssdk.utils.XmlUtils;

public class Main {

    @Test
    public void marshallRestXmlTypes() throws IOException {
        RestXmlTypesRequest request = RestXmlTypesRequest
            .builder()
            .flattenedListOfStrings("s1", "s2")
            .nonFlattenedListWithLocation("f1", "f2")
            .flattenedListOfStructs(SimpleStruct.builder().stringMember("foo").build(),
                                    SimpleStruct.builder().stringMember("bar").build())
            .flattenedListWithLocation("f3", "f4")
            .flattenedMap(ImmutableMap.<String, String>builder().put("a1", "b1")
                                                                .put("a2", "b2")
                                                                .build())
            .flattenedMapWithLocation(ImmutableMap.<String, String>builder().put("a3", "b3")
                                                                            .put("a4", "b4")
                                                                            .build())
            .nonFlattenedMapWithLocation(ImmutableMap.<String, String>builder().put("a5", "b5")
                                                                            .put("a6", "b6")
                                                                            .build())
            .mapOfStringToStringInQuery(ImmutableMap.<String, String>builder().put("a7", "b7")
                                                                              .put("a8", "b8")
                                                                              .build())

            .build();


        RestXmlTypesRequestMarshaller marshaller = new RestXmlTypesRequestMarshaller(AwsXmlProtocolFactory.builder()
                                                                                                          .build());
        Request<RestXmlTypesRequest> marshalled = marshaller.marshall(request);
        print(marshalled);
    }

    @Test
    public void unmarshallRestXmlTypes() throws Exception {
        XmlProtocolUnmarshaller unmarshaller = new XmlProtocolUnmarshaller();
        Supplier<SdkPojo> pojoSupplier = RestXmlTypesResponse::builder;
        Function<SdkHttpFullResponse, SdkPojo> pojoFunction = r -> pojoSupplier.get();

        SdkHttpFullResponse httpResponse = new FullResponse();

        RestXmlTypesResponse response = (RestXmlTypesResponse) unmarshaller.unmarshall(pojoFunction.apply(httpResponse),
                                                                                       httpResponse,
                                                                                       context());


        response.flattenedListOfStrings().stream().forEach(System.out::println);
        response.nonFlattenedListWithLocation().stream().forEach(System.out::println);
        System.out.println("---------");

        response.flattenedListOfStructs().stream().forEach(System.out::println);
        response.flattenedListWithLocation().stream().forEach(System.out::println);

        System.out.println("---------");

        response.flattenedMap().forEach((k, v) -> System.out.println(k + "  " + v));
        response.flattenedMapWithLocation().forEach((k, v) -> System.out.println(k + "  " + v));

        System.out.println("---------");
        response.nonFlattenedMapWithLocation().forEach((k, v) -> System.out.println(k + "  " + v));
        response.mapOfStringToStringInQuery().forEach((k, v) -> System.out.println(k + "  " + v));
    }


    private StaxUnmarshallerContext context() throws Exception {
        XMLEventReader eventReader = null;
        try {
            String string = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RestXmlTypesRequest xmlns=\"https://restxml/\"><FlattenedListOfStrings>s1</FlattenedListOfStrings><FlattenedListOfStrings>s2</FlattenedListOfStrings><NonFlattenedListWithLocation><item>f1</item><item>f2</item></NonFlattenedListWithLocation><FlattenedListOfStructs><StringMember>foo</StringMember></FlattenedListOfStructs><FlattenedListOfStructs><StringMember>bar</StringMember></FlattenedListOfStructs><item>f3</item><item>f4</item><flatmap><entry><thekey>a3</thekey><thevalue>b3</thevalue></entry><entry><thekey>a4</thekey><thevalue>b4</thevalue></entry></flatmap><themap><entry><thekey>a5</thekey><thevalue>b5</thevalue></entry><entry><thekey>a6</thekey><thevalue>b6</thevalue></entry></themap></RestXmlTypesRequest>";

            InputStream content = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));

            eventReader = XmlUtils.xmlInputFactory().createXMLEventReader(content);

            return new StaxUnmarshallerContext(eventReader);
            //            unmarshallerContext.registerMetadataExpression("ResponseMetadata/RequestId", 2, AWS_REQUEST_ID);
            //            unmarshallerContext.registerMetadataExpression("requestId", 2, AWS_REQUEST_ID);

        } finally {
            if (eventReader != null) {
                eventReader.close();
            }
        }
    }

    private static void print(Request<?> marshalled) throws IOException {
        System.out.println(marshalled.toString());

        System.out.println(marshalled.getResourcePath());

        System.out.println(marshalled.getHeaders());

        System.out.println(marshalled.getParameters());

        System.out.println(marshalled.getEndpoint());

        System.out.println(marshalled.getServiceName());

        System.out.println(marshalled.getHttpMethod());

        if (marshalled.getContent() != null) {
            System.out.println(IoUtils.toUtf8String(marshalled.getContent()));
        }
    }

    private class FullResponse implements SdkHttpFullResponse {

        @Override
        public Optional<AbortableInputStream> content() {
            return Optional.empty();
        }

        @Override
        public Optional<String> statusText() {
            return Optional.of("foo");
        }

        @Override
        public int statusCode() {
            return 0;
        }

        @Override
        public Map<String, List<String>> headers() {
            return new HashMap<>();
        }

        @Override
        public Builder toBuilder() {
            return null;
        }
    }
}
