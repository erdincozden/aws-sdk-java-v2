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
import software.amazon.awssdk.regions.regionmetadata.ApNortheast1;
import software.amazon.awssdk.regions.regionmetadata.ApNortheast2;
import software.amazon.awssdk.regions.regionmetadata.ApSouth1;
import software.amazon.awssdk.regions.regionmetadata.ApSoutheast1;
import software.amazon.awssdk.regions.regionmetadata.ApSoutheast2;
import software.amazon.awssdk.regions.regionmetadata.CaCentral1;
import software.amazon.awssdk.regions.regionmetadata.CnNorth1;
import software.amazon.awssdk.regions.regionmetadata.CnNorthwest1;
import software.amazon.awssdk.regions.regionmetadata.EuCentral1;
import software.amazon.awssdk.regions.regionmetadata.EuWest1;
import software.amazon.awssdk.regions.regionmetadata.EuWest2;
import software.amazon.awssdk.regions.regionmetadata.EuWest3;
import software.amazon.awssdk.regions.regionmetadata.SaEast1;
import software.amazon.awssdk.regions.regionmetadata.UsEast1;
import software.amazon.awssdk.regions.regionmetadata.UsEast2;
import software.amazon.awssdk.regions.regionmetadata.UsGovWest1;
import software.amazon.awssdk.regions.regionmetadata.UsWest1;
import software.amazon.awssdk.regions.regionmetadata.UsWest2;

@Generated("software.amazon.awssdk:codegen")
@SdkPublicApi
public final class RegionMetadataProvider {
    private static final Map<Region, RegionMetadata> REGION_METADATA = Collections.unmodifiableMap(Stream.of(
            new AbstractMap.SimpleEntry<>(Region.AP_NORTHEAST_1, new ApNortheast1()),
            new AbstractMap.SimpleEntry<>(Region.AP_NORTHEAST_2, new ApNortheast2()),
            new AbstractMap.SimpleEntry<>(Region.AP_SOUTH_1, new ApSouth1()),
            new AbstractMap.SimpleEntry<>(Region.AP_SOUTHEAST_1, new ApSoutheast1()),
            new AbstractMap.SimpleEntry<>(Region.AP_SOUTHEAST_2, new ApSoutheast2()),
            new AbstractMap.SimpleEntry<>(Region.CA_CENTRAL_1, new CaCentral1()),
            new AbstractMap.SimpleEntry<>(Region.EU_CENTRAL_1, new EuCentral1()),
            new AbstractMap.SimpleEntry<>(Region.EU_WEST_1, new EuWest1()),
            new AbstractMap.SimpleEntry<>(Region.EU_WEST_2, new EuWest2()),
            new AbstractMap.SimpleEntry<>(Region.EU_WEST_3, new EuWest3()),
            new AbstractMap.SimpleEntry<>(Region.SA_EAST_1, new SaEast1()),
            new AbstractMap.SimpleEntry<>(Region.US_EAST_1, new UsEast1()),
            new AbstractMap.SimpleEntry<>(Region.US_EAST_2, new UsEast2()),
            new AbstractMap.SimpleEntry<>(Region.US_WEST_1, new UsWest1()),
            new AbstractMap.SimpleEntry<>(Region.US_WEST_2, new UsWest2()),
            new AbstractMap.SimpleEntry<>(Region.CN_NORTH_1, new CnNorth1()),
            new AbstractMap.SimpleEntry<>(Region.CN_NORTHWEST_1, new CnNorthwest1()),
            new AbstractMap.SimpleEntry<>(Region.US_GOV_WEST_1, new UsGovWest1())).collect(
            Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));

    public static RegionMetadata regionMetadata(Region region) {
        return REGION_METADATA.get(region);
    }
}
