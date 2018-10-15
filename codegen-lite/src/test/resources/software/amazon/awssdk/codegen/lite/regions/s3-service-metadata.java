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

package software.amazon.awssdk.regions.servicemetadata;

import java.net.URI;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import software.amazon.awssdk.annotations.Generated;
import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.ServiceMetadata;

@Generated("software.amazon.awssdk:codegen")
@SdkPublicApi
public final class S3ServiceMetadata implements ServiceMetadata {
    private static final String ENDPOINT_PREFIX = "s3";

    private static final Map<String, String> REGION_OVERRIDDEN_ENDPOINTS = Collections.unmodifiableMap(Stream.of(
            new AbstractMap.SimpleEntry<>("ap-northeast-1", "s3.ap-northeast-1.amazonaws.com"),
            new AbstractMap.SimpleEntry<>("ap-southeast-1", "s3.ap-southeast-1.amazonaws.com"),
            new AbstractMap.SimpleEntry<>("ap-southeast-2", "s3.ap-southeast-2.amazonaws.com"),
            new AbstractMap.SimpleEntry<>("eu-west-1", "s3.eu-west-1.amazonaws.com"),
            new AbstractMap.SimpleEntry<>("s3-external-1", "s3-external-1.amazonaws.com"),
            new AbstractMap.SimpleEntry<>("sa-east-1", "s3.sa-east-1.amazonaws.com"),
            new AbstractMap.SimpleEntry<>("us-east-1", "s3.amazonaws.com"),
            new AbstractMap.SimpleEntry<>("us-west-1", "s3.us-west-1.amazonaws.com"),
            new AbstractMap.SimpleEntry<>("us-west-2", "s3.us-west-2.amazonaws.com"),
            new AbstractMap.SimpleEntry<>("fips-us-gov-west-1", "s3-fips-us-gov-west-1.amazonaws.com"),
            new AbstractMap.SimpleEntry<>("us-gov-west-1", "s3.us-gov-west-1.amazonaws.com")).collect(
            Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));

    private static final List<Region> REGIONS = Collections
            .unmodifiableList(Arrays.asList(Region.AP_NORTHEAST_1, Region.AP_NORTHEAST_2, Region.AP_SOUTH_1,
                    Region.AP_SOUTHEAST_1, Region.AP_SOUTHEAST_2, Region.CA_CENTRAL_1, Region.EU_CENTRAL_1, Region.EU_WEST_1,
                    Region.EU_WEST_2, Region.EU_WEST_3, Region.SA_EAST_1, Region.US_EAST_1, Region.US_EAST_2, Region.US_WEST_1,
                    Region.US_WEST_2, Region.CN_NORTH_1, Region.CN_NORTHWEST_1, Region.US_GOV_WEST_1));

    private static final Map<String, String> SIGNING_REGION_OVERRIDES = Collections.unmodifiableMap(Stream.of(
            new AbstractMap.SimpleEntry<>("s3-external-1", "us-east-1"),
            new AbstractMap.SimpleEntry<>("fips-us-gov-west-1", "us-gov-west-1")).collect(
            Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));

    @Override
    public List<Region> regions() {
        return REGIONS;
    }

    @Override
    public URI endpointFor(Region region) {
        return URI.create(REGION_OVERRIDDEN_ENDPOINTS.getOrDefault(region.id(), computeEndpoint(ENDPOINT_PREFIX, region)));
    }

    @Override
    public Region signingRegion(Region region) {
        return Region.of(SIGNING_REGION_OVERRIDES.getOrDefault(region.id(), region.id()));
    }
}
