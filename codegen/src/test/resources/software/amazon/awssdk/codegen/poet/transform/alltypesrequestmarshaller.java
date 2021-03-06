package software.amazon.awssdk.services.jsonprotocoltests.transform;

import software.amazon.awssdk.annotations.Generated;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.awscore.protocol.json.AwsJsonProtocolFactory;
import software.amazon.awssdk.core.Request;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.http.HttpMethodName;
import software.amazon.awssdk.core.protocol.OperationInfo;
import software.amazon.awssdk.core.protocol.ProtocolMarshaller;
import software.amazon.awssdk.core.runtime.transform.Marshaller;
import software.amazon.awssdk.services.jsonprotocoltests.model.AllTypesRequest;
import software.amazon.awssdk.utils.Validate;

/**
 * {@link AllTypesRequest} Marshaller
 */
@Generated("software.amazon.awssdk:codegen")
@SdkInternalApi
public class AllTypesRequestMarshaller implements Marshaller<Request<AllTypesRequest>, AllTypesRequest> {
    private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().requestUri("/")
                                                                            .httpMethodName(HttpMethodName.POST).hasExplicitPayloadMember(false).hasPayloadMembers(true).build();

    private final AwsJsonProtocolFactory protocolFactory;

    public AllTypesRequestMarshaller(AwsJsonProtocolFactory protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    @Override
    public Request<AllTypesRequest> marshall(AllTypesRequest allTypesRequest) {
        Validate.paramNotNull(allTypesRequest, "allTypesRequest");
        try {
            ProtocolMarshaller<Request<AllTypesRequest>> protocolMarshaller = protocolFactory.createProtocolMarshaller(
                SDK_OPERATION_BINDING, allTypesRequest);
            return protocolMarshaller.marshall(allTypesRequest);
        } catch (Exception e) {
            throw SdkClientException.builder().message("Unable to marshall request to JSON: " + e.getMessage()).cause(e).build();
        }
    }
}

