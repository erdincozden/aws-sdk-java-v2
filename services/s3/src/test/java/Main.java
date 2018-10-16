import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import software.amazon.awssdk.core.Request;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.utils.IoUtils;

public class Main {

    private static final S3Client s3 = S3Client.builder().region(Region.US_WEST_2).build();
    private static final String BUCKET = "apigateway-sdk-artifact-uploader-test-1518135565343";
    private static final String KEY = "foo";

    @BeforeClass
    public static void setup() {
       // BasicConfigurator.configure();
    }

    @Test
    public void headObject() {
        HeadObjectResponse response =
        s3.headObject(HeadObjectRequest.builder().bucket(BUCKET).key(KEY).build());

        System.out.println(response.metadata());
        System.out.println(response.toString());
    }

    @Test
    public void listObjects() {
        ListObjectsV2Response response =
            s3.listObjectsV2(ListObjectsV2Request.builder().bucket(BUCKET).build());

        System.out.println(response.toString());

        response.contents().stream().map(o -> o.toString()).forEach(System.out::println);

        response.commonPrefixes().stream().map(c -> c.prefix()).forEach(System.out::println);
    }

    //
    //    private static void abortMultipartUploadRequestOldStyle(AbortMultipartUploadRequest request) throws IOException {
    //        AbortMultipartUploadRequestMarshaller marshaller = new AbortMultipartUploadRequestMarshaller();
    //        Request<AbortMultipartUploadRequest> marshalled = marshaller.marshall(request);
    //        print(marshalled);
    //    }
    //
    //    private static void abortMultipartUploadRequestNewStyle(AbortMultipartUploadRequest request) throws IOException {
    //        AwsXmlProtocolFactory protocolFactory = AwsXmlProtocolFactory.builder().build();
    //        DummyAbortMultipartUploadRequestMarshaller marshaller = new DummyAbortMultipartUploadRequestMarshaller(protocolFactory);
    //        Request<AbortMultipartUploadRequest> marshalled = marshaller.marshall(request);
    //
    //        print(marshalled);
    //    }
    //
    //    private static AbortMultipartUploadRequest createReq() {
    //        return AbortMultipartUploadRequest.builder()
    //                                          .bucket("myawesome-bucket")
    //                                          .key("foo/foo")
    //                                          .uploadId("183j8jfjefj8e")
    //                                          .requestPayer(RequestPayer.REQUESTER)
    //                                          .overrideConfiguration(AwsRequestOverrideConfiguration.builder()
    //                                                                                                .addApiName(ApiName.builder()
    //                                                                                                                   .name("foo")
    //                                                                                                                   .version("1.2")
    //                                                                                                                   .build())
    //                                                                                                .build())
    //                                          .build();
    //
    //    }

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
}
