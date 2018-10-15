/*
 * Copyright 2013-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with
 * the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package software.amazon.awssdk.regions;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import software.amazon.awssdk.annotations.Generated;
import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.regions.servicemetadata.A4bServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.AcmPcaServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.AcmServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ApiMediatailorServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ApiPricingServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ApigatewayServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ApplicationAutoscalingServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.Appstream2ServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.AthenaServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.AutoscalingPlansServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.AutoscalingServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.BatchServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.BudgetsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CeServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.Cloud9ServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ClouddirectoryServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CloudformationServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CloudfrontServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CloudhsmServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.Cloudhsmv2ServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CloudsearchServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CloudtrailServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CodebuildServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CodecommitServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CodedeployServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CodepipelineServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CodestarServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CognitoIdentityServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CognitoIdpServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CognitoSyncServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ComprehendServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ConfigServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.CurServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.DataIotServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.DatapipelineServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.DaxServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.DevicefarmServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.DirectconnectServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.DiscoveryServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.DmsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.DsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.DynamodbServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.Ec2ServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.EcrServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.EcsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ElasticacheServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ElasticbeanstalkServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ElasticfilesystemServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ElasticloadbalancingServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ElasticmapreduceServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ElastictranscoderServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.EmailServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.EntitlementMarketplaceServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.EsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.EventsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.FirehoseServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.FmsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.GameliftServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.GlacierServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.GlueServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.GreengrassServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.GuarddutyServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.HealthServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.IamServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ImportexportServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.InspectorServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.IotServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.KinesisServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.KinesisanalyticsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.KinesisvideoServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.KmsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.LambdaServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.LightsailServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.LogsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.MachinelearningServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.MarketplacecommerceanalyticsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.MediaconvertServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.MedialiveServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.MediapackageServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.MediastoreServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.MeteringMarketplaceServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.MghServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.MobileanalyticsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ModelsLexServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.MonitoringServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.MturkRequesterServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.NeptuneServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.OpsworksCmServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.OpsworksServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.OrganizationsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.PinpointServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.PollyServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.RdsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.RedshiftServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.RekognitionServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ResourceGroupsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.Route53ServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.Route53domainsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.RuntimeLexServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.RuntimeSagemakerServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.S3ServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.SagemakerServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.SdbServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.SecretsmanagerServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ServerlessrepoServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ServicecatalogServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ServicediscoveryServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.ShieldServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.SmsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.SnowballServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.SnsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.SqsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.SsmServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.StatesServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.StoragegatewayServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.StreamsDynamodbServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.StsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.SupportServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.SwfServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.TaggingServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.TranslateServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.WafRegionalServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.WafServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.WorkdocsServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.WorkmailServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.WorkspacesServiceMetadata;
import software.amazon.awssdk.regions.servicemetadata.XrayServiceMetadata;

@Generated("software.amazon.awssdk:codegen")
@SdkPublicApi
public final class ServiceMetadataProvider {
    private static final Map<String, ServiceMetadata> SERVICE_METADATA = Collections.unmodifiableMap(Stream.of(
            new AbstractMap.SimpleEntry<>("a4b", new A4bServiceMetadata()),
            new AbstractMap.SimpleEntry<>("acm", new AcmServiceMetadata()),
            new AbstractMap.SimpleEntry<>("acm-pca", new AcmPcaServiceMetadata()),
            new AbstractMap.SimpleEntry<>("api.mediatailor", new ApiMediatailorServiceMetadata()),
            new AbstractMap.SimpleEntry<>("api.pricing", new ApiPricingServiceMetadata()),
            new AbstractMap.SimpleEntry<>("apigateway", new ApigatewayServiceMetadata()),
            new AbstractMap.SimpleEntry<>("application-autoscaling", new ApplicationAutoscalingServiceMetadata()),
            new AbstractMap.SimpleEntry<>("appstream2", new Appstream2ServiceMetadata()),
            new AbstractMap.SimpleEntry<>("athena", new AthenaServiceMetadata()),
            new AbstractMap.SimpleEntry<>("autoscaling", new AutoscalingServiceMetadata()),
            new AbstractMap.SimpleEntry<>("autoscaling-plans", new AutoscalingPlansServiceMetadata()),
            new AbstractMap.SimpleEntry<>("batch", new BatchServiceMetadata()),
            new AbstractMap.SimpleEntry<>("budgets", new BudgetsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ce", new CeServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloud9", new Cloud9ServiceMetadata()),
            new AbstractMap.SimpleEntry<>("clouddirectory", new ClouddirectoryServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloudformation", new CloudformationServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloudfront", new CloudfrontServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloudhsm", new CloudhsmServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloudhsmv2", new Cloudhsmv2ServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloudsearch", new CloudsearchServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloudtrail", new CloudtrailServiceMetadata()),
            new AbstractMap.SimpleEntry<>("codebuild", new CodebuildServiceMetadata()),
            new AbstractMap.SimpleEntry<>("codecommit", new CodecommitServiceMetadata()),
            new AbstractMap.SimpleEntry<>("codedeploy", new CodedeployServiceMetadata()),
            new AbstractMap.SimpleEntry<>("codepipeline", new CodepipelineServiceMetadata()),
            new AbstractMap.SimpleEntry<>("codestar", new CodestarServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cognito-identity", new CognitoIdentityServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cognito-idp", new CognitoIdpServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cognito-sync", new CognitoSyncServiceMetadata()),
            new AbstractMap.SimpleEntry<>("comprehend", new ComprehendServiceMetadata()),
            new AbstractMap.SimpleEntry<>("config", new ConfigServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cur", new CurServiceMetadata()),
            new AbstractMap.SimpleEntry<>("data.iot", new DataIotServiceMetadata()),
            new AbstractMap.SimpleEntry<>("datapipeline", new DatapipelineServiceMetadata()),
            new AbstractMap.SimpleEntry<>("dax", new DaxServiceMetadata()),
            new AbstractMap.SimpleEntry<>("devicefarm", new DevicefarmServiceMetadata()),
            new AbstractMap.SimpleEntry<>("directconnect", new DirectconnectServiceMetadata()),
            new AbstractMap.SimpleEntry<>("discovery", new DiscoveryServiceMetadata()),
            new AbstractMap.SimpleEntry<>("dms", new DmsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ds", new DsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("dynamodb", new DynamodbServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ec2", new Ec2ServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ecr", new EcrServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ecs", new EcsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticache", new ElasticacheServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticbeanstalk", new ElasticbeanstalkServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticfilesystem", new ElasticfilesystemServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticloadbalancing", new ElasticloadbalancingServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticmapreduce", new ElasticmapreduceServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elastictranscoder", new ElastictranscoderServiceMetadata()),
            new AbstractMap.SimpleEntry<>("email", new EmailServiceMetadata()),
            new AbstractMap.SimpleEntry<>("entitlement.marketplace", new EntitlementMarketplaceServiceMetadata()),
            new AbstractMap.SimpleEntry<>("es", new EsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("events", new EventsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("firehose", new FirehoseServiceMetadata()),
            new AbstractMap.SimpleEntry<>("fms", new FmsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("gamelift", new GameliftServiceMetadata()),
            new AbstractMap.SimpleEntry<>("glacier", new GlacierServiceMetadata()),
            new AbstractMap.SimpleEntry<>("glue", new GlueServiceMetadata()),
            new AbstractMap.SimpleEntry<>("greengrass", new GreengrassServiceMetadata()),
            new AbstractMap.SimpleEntry<>("guardduty", new GuarddutyServiceMetadata()),
            new AbstractMap.SimpleEntry<>("health", new HealthServiceMetadata()),
            new AbstractMap.SimpleEntry<>("iam", new IamServiceMetadata()),
            new AbstractMap.SimpleEntry<>("importexport", new ImportexportServiceMetadata()),
            new AbstractMap.SimpleEntry<>("inspector", new InspectorServiceMetadata()),
            new AbstractMap.SimpleEntry<>("iot", new IotServiceMetadata()),
            new AbstractMap.SimpleEntry<>("kinesis", new KinesisServiceMetadata()),
            new AbstractMap.SimpleEntry<>("kinesisanalytics", new KinesisanalyticsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("kinesisvideo", new KinesisvideoServiceMetadata()),
            new AbstractMap.SimpleEntry<>("kms", new KmsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("lambda", new LambdaServiceMetadata()),
            new AbstractMap.SimpleEntry<>("lightsail", new LightsailServiceMetadata()),
            new AbstractMap.SimpleEntry<>("logs", new LogsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("machinelearning", new MachinelearningServiceMetadata()),
            new AbstractMap.SimpleEntry<>("marketplacecommerceanalytics", new MarketplacecommerceanalyticsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("mediaconvert", new MediaconvertServiceMetadata()),
            new AbstractMap.SimpleEntry<>("medialive", new MedialiveServiceMetadata()),
            new AbstractMap.SimpleEntry<>("mediapackage", new MediapackageServiceMetadata()),
            new AbstractMap.SimpleEntry<>("mediastore", new MediastoreServiceMetadata()),
            new AbstractMap.SimpleEntry<>("metering.marketplace", new MeteringMarketplaceServiceMetadata()),
            new AbstractMap.SimpleEntry<>("mgh", new MghServiceMetadata()),
            new AbstractMap.SimpleEntry<>("mobileanalytics", new MobileanalyticsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("models.lex", new ModelsLexServiceMetadata()),
            new AbstractMap.SimpleEntry<>("monitoring", new MonitoringServiceMetadata()),
            new AbstractMap.SimpleEntry<>("mturk-requester", new MturkRequesterServiceMetadata()),
            new AbstractMap.SimpleEntry<>("neptune", new NeptuneServiceMetadata()),
            new AbstractMap.SimpleEntry<>("opsworks", new OpsworksServiceMetadata()),
            new AbstractMap.SimpleEntry<>("opsworks-cm", new OpsworksCmServiceMetadata()),
            new AbstractMap.SimpleEntry<>("organizations", new OrganizationsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("pinpoint", new PinpointServiceMetadata()),
            new AbstractMap.SimpleEntry<>("polly", new PollyServiceMetadata()),
            new AbstractMap.SimpleEntry<>("rds", new RdsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("redshift", new RedshiftServiceMetadata()),
            new AbstractMap.SimpleEntry<>("rekognition", new RekognitionServiceMetadata()),
            new AbstractMap.SimpleEntry<>("resource-groups", new ResourceGroupsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("route53", new Route53ServiceMetadata()),
            new AbstractMap.SimpleEntry<>("route53domains", new Route53domainsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("runtime.lex", new RuntimeLexServiceMetadata()),
            new AbstractMap.SimpleEntry<>("runtime.sagemaker", new RuntimeSagemakerServiceMetadata()),
            new AbstractMap.SimpleEntry<>("s3", new S3ServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sagemaker", new SagemakerServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sdb", new SdbServiceMetadata()),
            new AbstractMap.SimpleEntry<>("secretsmanager", new SecretsmanagerServiceMetadata()),
            new AbstractMap.SimpleEntry<>("serverlessrepo", new ServerlessrepoServiceMetadata()),
            new AbstractMap.SimpleEntry<>("servicecatalog", new ServicecatalogServiceMetadata()),
            new AbstractMap.SimpleEntry<>("servicediscovery", new ServicediscoveryServiceMetadata()),
            new AbstractMap.SimpleEntry<>("shield", new ShieldServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sms", new SmsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("snowball", new SnowballServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sns", new SnsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sqs", new SqsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ssm", new SsmServiceMetadata()),
            new AbstractMap.SimpleEntry<>("states", new StatesServiceMetadata()),
            new AbstractMap.SimpleEntry<>("storagegateway", new StoragegatewayServiceMetadata()),
            new AbstractMap.SimpleEntry<>("streams.dynamodb", new StreamsDynamodbServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sts", new StsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("support", new SupportServiceMetadata()),
            new AbstractMap.SimpleEntry<>("swf", new SwfServiceMetadata()),
            new AbstractMap.SimpleEntry<>("tagging", new TaggingServiceMetadata()),
            new AbstractMap.SimpleEntry<>("translate", new TranslateServiceMetadata()),
            new AbstractMap.SimpleEntry<>("waf", new WafServiceMetadata()),
            new AbstractMap.SimpleEntry<>("waf-regional", new WafRegionalServiceMetadata()),
            new AbstractMap.SimpleEntry<>("workdocs", new WorkdocsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("workmail", new WorkmailServiceMetadata()),
            new AbstractMap.SimpleEntry<>("workspaces", new WorkspacesServiceMetadata()),
            new AbstractMap.SimpleEntry<>("xray", new XrayServiceMetadata()),
            new AbstractMap.SimpleEntry<>("apigateway", new ApigatewayServiceMetadata()),
            new AbstractMap.SimpleEntry<>("application-autoscaling", new ApplicationAutoscalingServiceMetadata()),
            new AbstractMap.SimpleEntry<>("autoscaling", new AutoscalingServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloudformation", new CloudformationServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloudtrail", new CloudtrailServiceMetadata()),
            new AbstractMap.SimpleEntry<>("codedeploy", new CodedeployServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cognito-identity", new CognitoIdentityServiceMetadata()),
            new AbstractMap.SimpleEntry<>("config", new ConfigServiceMetadata()),
            new AbstractMap.SimpleEntry<>("data.iot", new DataIotServiceMetadata()),
            new AbstractMap.SimpleEntry<>("directconnect", new DirectconnectServiceMetadata()),
            new AbstractMap.SimpleEntry<>("dynamodb", new DynamodbServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ec2", new Ec2ServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ecr", new EcrServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ecs", new EcsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticache", new ElasticacheServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticbeanstalk", new ElasticbeanstalkServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticloadbalancing", new ElasticloadbalancingServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticmapreduce", new ElasticmapreduceServiceMetadata()),
            new AbstractMap.SimpleEntry<>("es", new EsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("events", new EventsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("glacier", new GlacierServiceMetadata()),
            new AbstractMap.SimpleEntry<>("iam", new IamServiceMetadata()),
            new AbstractMap.SimpleEntry<>("iot", new IotServiceMetadata()),
            new AbstractMap.SimpleEntry<>("kinesis", new KinesisServiceMetadata()),
            new AbstractMap.SimpleEntry<>("lambda", new LambdaServiceMetadata()),
            new AbstractMap.SimpleEntry<>("logs", new LogsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("monitoring", new MonitoringServiceMetadata()),
            new AbstractMap.SimpleEntry<>("rds", new RdsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("redshift", new RedshiftServiceMetadata()),
            new AbstractMap.SimpleEntry<>("s3", new S3ServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sms", new SmsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("snowball", new SnowballServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sns", new SnsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sqs", new SqsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ssm", new SsmServiceMetadata()),
            new AbstractMap.SimpleEntry<>("storagegateway", new StoragegatewayServiceMetadata()),
            new AbstractMap.SimpleEntry<>("streams.dynamodb", new StreamsDynamodbServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sts", new StsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("swf", new SwfServiceMetadata()),
            new AbstractMap.SimpleEntry<>("tagging", new TaggingServiceMetadata()),
            new AbstractMap.SimpleEntry<>("acm", new AcmServiceMetadata()),
            new AbstractMap.SimpleEntry<>("apigateway", new ApigatewayServiceMetadata()),
            new AbstractMap.SimpleEntry<>("autoscaling", new AutoscalingServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloudformation", new CloudformationServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloudhsm", new CloudhsmServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloudhsmv2", new Cloudhsmv2ServiceMetadata()),
            new AbstractMap.SimpleEntry<>("cloudtrail", new CloudtrailServiceMetadata()),
            new AbstractMap.SimpleEntry<>("codedeploy", new CodedeployServiceMetadata()),
            new AbstractMap.SimpleEntry<>("config", new ConfigServiceMetadata()),
            new AbstractMap.SimpleEntry<>("directconnect", new DirectconnectServiceMetadata()),
            new AbstractMap.SimpleEntry<>("dms", new DmsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("dynamodb", new DynamodbServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ec2", new Ec2ServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ecr", new EcrServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ecs", new EcsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticache", new ElasticacheServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticbeanstalk", new ElasticbeanstalkServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticloadbalancing", new ElasticloadbalancingServiceMetadata()),
            new AbstractMap.SimpleEntry<>("elasticmapreduce", new ElasticmapreduceServiceMetadata()),
            new AbstractMap.SimpleEntry<>("es", new EsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("events", new EventsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("glacier", new GlacierServiceMetadata()),
            new AbstractMap.SimpleEntry<>("iam", new IamServiceMetadata()),
            new AbstractMap.SimpleEntry<>("inspector", new InspectorServiceMetadata()),
            new AbstractMap.SimpleEntry<>("kinesis", new KinesisServiceMetadata()),
            new AbstractMap.SimpleEntry<>("kms", new KmsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("lambda", new LambdaServiceMetadata()),
            new AbstractMap.SimpleEntry<>("logs", new LogsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("metering.marketplace", new MeteringMarketplaceServiceMetadata()),
            new AbstractMap.SimpleEntry<>("monitoring", new MonitoringServiceMetadata()),
            new AbstractMap.SimpleEntry<>("polly", new PollyServiceMetadata()),
            new AbstractMap.SimpleEntry<>("rds", new RdsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("redshift", new RedshiftServiceMetadata()),
            new AbstractMap.SimpleEntry<>("rekognition", new RekognitionServiceMetadata()),
            new AbstractMap.SimpleEntry<>("s3", new S3ServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sms", new SmsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("snowball", new SnowballServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sns", new SnsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sqs", new SqsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("ssm", new SsmServiceMetadata()),
            new AbstractMap.SimpleEntry<>("storagegateway", new StoragegatewayServiceMetadata()),
            new AbstractMap.SimpleEntry<>("streams.dynamodb", new StreamsDynamodbServiceMetadata()),
            new AbstractMap.SimpleEntry<>("sts", new StsServiceMetadata()),
            new AbstractMap.SimpleEntry<>("swf", new SwfServiceMetadata()),
            new AbstractMap.SimpleEntry<>("tagging", new TaggingServiceMetadata())).collect(
            Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));

    public static ServiceMetadata serviceMetadata(String endpointPrefix) {
        return SERVICE_METADATA.get(endpointPrefix);
    }
}
