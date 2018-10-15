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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import software.amazon.awssdk.annotations.Generated;
import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.utils.Validate;

@SdkPublicApi
@Generated("software.amazon.awssdk:codegen")
public final class Region {
    public static final Region AP_SOUTH_1 = Region.of("ap-south-1");

    public static final Region EU_WEST_3 = Region.of("eu-west-3");

    public static final Region EU_WEST_2 = Region.of("eu-west-2");

    public static final Region EU_WEST_1 = Region.of("eu-west-1");

    public static final Region AP_NORTHEAST_2 = Region.of("ap-northeast-2");

    public static final Region AP_NORTHEAST_1 = Region.of("ap-northeast-1");

    public static final Region CA_CENTRAL_1 = Region.of("ca-central-1");

    public static final Region SA_EAST_1 = Region.of("sa-east-1");

    public static final Region CN_NORTH_1 = Region.of("cn-north-1");

    public static final Region US_GOV_WEST_1 = Region.of("us-gov-west-1");

    public static final Region AP_SOUTHEAST_1 = Region.of("ap-southeast-1");

    public static final Region AP_SOUTHEAST_2 = Region.of("ap-southeast-2");

    public static final Region EU_CENTRAL_1 = Region.of("eu-central-1");

    public static final Region US_EAST_1 = Region.of("us-east-1");

    public static final Region US_EAST_2 = Region.of("us-east-2");

    public static final Region US_WEST_1 = Region.of("us-west-1");

    public static final Region CN_NORTHWEST_1 = Region.of("cn-northwest-1");

    public static final Region US_WEST_2 = Region.of("us-west-2");

    public static final Region AWS_GLOBAL = Region.of("aws-global", true);

    public static final Region AWS_CN_GLOBAL = Region.of("aws-cn-global", true);

    public static final Region AWS_US_GOV_GLOBAL = Region.of("aws-us-gov-global", true);

    private static final List<Region> REGIONS = Collections.unmodifiableList(Arrays.asList(AP_SOUTH_1, EU_WEST_3, EU_WEST_2,
            EU_WEST_1, AP_NORTHEAST_2, AP_NORTHEAST_1, CA_CENTRAL_1, SA_EAST_1, CN_NORTH_1, US_GOV_WEST_1, AP_SOUTHEAST_1,
            AP_SOUTHEAST_2, EU_CENTRAL_1, US_EAST_1, US_EAST_2, US_WEST_1, CN_NORTHWEST_1, US_WEST_2, AWS_GLOBAL, AWS_CN_GLOBAL,
            AWS_US_GOV_GLOBAL));

    private final boolean isGlobalRegion;

    private final String id;

    private Region(String id, boolean isGlobalRegion) {
        this.id = id;
        this.isGlobalRegion = isGlobalRegion;
    }

    public static Region of(String value) {
        return of(value, false);
    }

    private static Region of(String value, boolean isGlobalRegion) {
        Validate.paramNotBlank(value, "region");
        return RegionCache.put(value, isGlobalRegion);
    }

    public String id() {
        return this.id;
    }

    public static List<Region> regions() {
        return REGIONS;
    }

    public boolean isGlobalRegion() {
        return isGlobalRegion;
    }

    @Override
    public String toString() {
        return id;
    }

    private static class RegionCache {
        private static final ConcurrentHashMap<String, Region> VALUES = new ConcurrentHashMap<>();

        private RegionCache() {
        }

        private static Region put(String value, boolean isGlobalRegion) {
            return VALUES.computeIfAbsent(value, v -> new Region(value, isGlobalRegion));
        }
    }
}
