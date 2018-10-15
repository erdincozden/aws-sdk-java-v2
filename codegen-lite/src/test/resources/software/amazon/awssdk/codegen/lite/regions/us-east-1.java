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

package software.amazon.awssdk.regions.regionmetadata;

import software.amazon.awssdk.annotations.Generated;
import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.regions.RegionMetadata;

@Generated("software.amazon.awssdk:codegen")
@SdkPublicApi
public final class UsEast1 implements RegionMetadata {
    private static final String ID = "us-east-1";

    private static final String DOMAIN = "amazonaws.com";

    private static final String NAME = "US East (N. Virginia)";

    private static final String PARTITION = "aws";

    public String name() {
        return NAME;
    }

    public String domain() {
        return DOMAIN;
    }

    public String id() {
        return ID;
    }

    public String partition() {
        return PARTITION;
    }
}
