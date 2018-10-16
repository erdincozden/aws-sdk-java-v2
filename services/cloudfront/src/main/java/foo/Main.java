// CHECKSTYLE:OFF
package foo;

import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.ListDistributionsRequest;
import software.amazon.awssdk.services.cloudfront.model.ListDistributionsResponse;

public class Main {

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        //createDistro();
        listDistros();
        //
        //        createDistributionRequestOldStyle(createReq());
        //
        //        System.out.println("---------");
        //        System.out.println("---------");
        //
        //        createDistributionRequestNewStyle(createReq());


        //        DeleteStreamingDistributionRequestOldStyle(createDelReq());
        //
        //        System.out.println("---------");
        //        System.out.println("---------");
        //
        //        DeleteStreamingDistributionRequestNewStyle(createDelReq());
    }

    //    private static void createDistro() {
    //        CloudFrontClient client = CloudFrontClient.builder().region(Region.AWS_GLOBAL).build();
    //
    //        CreateDistributionRequest request = CreateDistributionRequest.builder()
    //                                                                     .distributionConfig(DistributionConfig.builder()
    //                                                                                                           .aliases(Aliases.builder()
    //                                                                                                                           .quantity(1234)
    //                                                                                                                           .items("foo", "bar")
    //                                                                                                                           .build())
    //                                                                                                           .build())
    //                                                                     .build();
    //
    //        CreateDistributionResponse response = client.createDistribution(request);
    //    }

        private static void listDistros() {
            CloudFrontClient client = CloudFrontClient.builder().region(Region.AWS_GLOBAL).build();

            ListDistributionsResponse response =
                client.listDistributions(ListDistributionsRequest.builder()
                                                                 .build());

            System.out.println("---------");
            System.out.println(response.distributionList().items());
        }
    //
    //    private static void createDistributionRequestOldStyle(CreateDistributionRequest request) throws IOException {
    //        CreateDistributionRequestMarshaller marshaller = new CreateDistributionRequestMarshaller();
    //        Request<CreateDistributionRequest> marshalled = marshaller.marshall(request);
    //        print(marshalled);
    //    }
    //
    //    private static void createDistributionRequestNewStyle(CreateDistributionRequest request) throws IOException {
    //        AwsXmlProtocolFactory protocolFactory = AwsXmlProtocolFactory.builder().build();
    //        DummyCreateDistributionRequestMarshaller marshaller = new DummyCreateDistributionRequestMarshaller(protocolFactory);
    //        Request<CreateDistributionRequest> marshalled = marshaller.marshall(request);
    //
    //        print(marshalled);
    //    }
    //
    //    private static void DeleteStreamingDistributionRequestOldStyle(DeleteStreamingDistributionRequest request) throws IOException {
    //        DeleteStreamingDistributionRequestMarshaller marshaller = new DeleteStreamingDistributionRequestMarshaller();
    //        Request<DeleteStreamingDistributionRequest> marshalled = marshaller.marshall(request);
    //        print(marshalled);
    //    }
    //
    //    private static void DeleteStreamingDistributionRequestNewStyle(DeleteStreamingDistributionRequest request) throws IOException {
    //        AwsXmlProtocolFactory protocolFactory = AwsXmlProtocolFactory.builder().build();
    //        DummyDeleteStreamingDistributionRequestMarshaller marshaller = new DummyDeleteStreamingDistributionRequestMarshaller(protocolFactory);
    //        Request<DeleteStreamingDistributionRequest> marshalled = marshaller.marshall(request);
    //
    //        print(marshalled);
    //    }
    //
    //    private static void print(Request<?> marshalled) throws IOException {
    //        System.out.println(marshalled.toString());
    //
    //        System.out.println(marshalled.getResourcePath());
    //
    //        System.out.println(marshalled.getHeaders());
    //
    //        System.out.println(marshalled.getParameters());
    //
    //        System.out.println(marshalled.getEndpoint());
    //
    //        System.out.println(marshalled.getServiceName());
    //
    //        System.out.println(marshalled.getHttpMethod());
    //
    //        if (marshalled.getContent() != null) {
    //            System.out.println(IoUtils.toUtf8String(marshalled.getContent()));
    //        }
    //    }
    //
    //    private static DeleteStreamingDistributionRequest createDelReq() {
    //        return DeleteStreamingDistributionRequest
    //            .builder()
    //            .id("foo")
    //            .ifMatch("Bar-boo")
    //            .build();
    //    }
    //
    //    private static CreateDistributionRequest createReq() {
    //        return CreateDistributionRequest
    //            .builder()
    //            .distributionConfig(DistributionConfig.builder()
    //                                                  .callerReference("fooCaller")
    //                                                  .aliases(Aliases.builder()
    //                                                                  .items("foo", "bar")
    //                                                                  .quantity(123)
    //                                                                  .build())
    //                                                  .origins(Origins.builder()
    //                                                                  .quantity(344)
    //                                                                  .items(Origin.builder()
    //                                                                               .id("id1")
    //                                                                               .originPath("mypath")
    //                                                                               .build(),
    //                                                                         Origin.builder()
    //                                                                               .id("id2")
    //                                                                               .originPath("rajiniPath")
    //                                                                               .domainName("domainawersom")
    //                                                                               .customHeaders(CustomHeaders.builder()
    //                                                                                                           .quantity(5)
    //                                                                                                           .items(OriginCustomHeader.builder()
    //                                                                                                                                    .headerName("foo")
    //                                                                                                                                    .headerValue("bar")
    //                                                                                                                                    .build())
    //                                                                                                           .build())
    //                                                                               .customOriginConfig(CustomOriginConfig.builder()
    //                                                                                                                     .originSslProtocols(OriginSslProtocols.builder()
    //                                                                                                                                                           .items(SslProtocol.SSL_V3,
    //                                                                                                                                                                  SslProtocol.TLS_V1)
    //                                                                                                                                                           .itemsWithStrings("TLSv1.2")
    //                                                                                                                                                           .build())
    //
    //                                                                                                                     .build())
    //                                                                               .build())
    //                                                                  .build())
    //                                                  .defaultRootObject("MyRootObj")
    //                                                  .isIPV6Enabled(true)
    //                                                  .enabled(false)
    //                                                  .httpVersion("1.2")
    //                                                  .logging(LoggingConfig.builder()
    //                                                                        .enabled(null)
    //                                                                        .bucket("/")
    //                                                                        .build())
    //                                                  .build())
    //            .build();
    //    }
}