package software.amazon.awssdk.services.jsonprotocoltests.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import software.amazon.awssdk.annotations.Generated;
import software.amazon.awssdk.core.protocol.MarshallLocation;
import software.amazon.awssdk.core.protocol.MarshallingType;
import software.amazon.awssdk.core.protocol.SdkField;
import software.amazon.awssdk.core.protocol.SdkPojo;
import software.amazon.awssdk.core.protocol.traits.LocationTrait;
import software.amazon.awssdk.utils.ToString;
import software.amazon.awssdk.utils.builder.CopyableBuilder;
import software.amazon.awssdk.utils.builder.ToCopyableBuilder;

/**
 */
@Generated("software.amazon.awssdk:codegen")
public final class SubTypeOne implements SdkPojo, ToCopyableBuilder<SubTypeOne.Builder, SubTypeOne> {
    private static final SdkField<String> SUB_TYPE_ONE_MEMBER_FIELD = SdkField.<String> builder(MarshallingType.STRING)
        .getter(getter(SubTypeOne::subTypeOneMember)).setter(setter(Builder::subTypeOneMember))
        .traits(LocationTrait.builder().location(MarshallLocation.PAYLOAD).locationName("SubTypeOneMember").build()).build();

    private static final List<SdkField<?>> SDK_FIELDS = Collections.unmodifiableList(Arrays.asList(SUB_TYPE_ONE_MEMBER_FIELD));

    private final String subTypeOneMember;

    private SubTypeOne(BuilderImpl builder) {
        this.subTypeOneMember = builder.subTypeOneMember;
    }

    /**
     * Returns the value of the SubTypeOneMember property for this object.
     *
     * @return The value of the SubTypeOneMember property for this object.
     */
    public String subTypeOneMember() {
        return subTypeOneMember;
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    public static Class<? extends Builder> serializableBuilderClass() {
        return BuilderImpl.class;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + Objects.hashCode(subTypeOneMember());
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SubTypeOne)) {
            return false;
        }
        SubTypeOne other = (SubTypeOne) obj;
        return Objects.equals(subTypeOneMember(), other.subTypeOneMember());
    }

    @Override
    public String toString() {
        return ToString.builder("SubTypeOne").add("SubTypeOneMember", subTypeOneMember()).build();
    }

    public <T> Optional<T> getValueForField(String fieldName, Class<T> clazz) {
        switch (fieldName) {
            case "SubTypeOneMember":
                return Optional.ofNullable(clazz.cast(subTypeOneMember()));
            default:
                return Optional.empty();
        }
    }

    @Override
    public List<SdkField<?>> sdkFields() {
        return SDK_FIELDS;
    }

    private static <T> Function<Object, T> getter(Function<SubTypeOne, T> g) {
        return obj -> g.apply((SubTypeOne) obj);
    }

    private static <T> BiConsumer<Object, T> setter(BiConsumer<Builder, T> s) {
        return (obj, val) -> s.accept((Builder) obj, val);
    }

    public interface Builder extends SdkPojo, CopyableBuilder<Builder, SubTypeOne> {
        /**
         * Sets the value of the SubTypeOneMember property for this object.
         *
         * @param subTypeOneMember
         *        The new value for the SubTypeOneMember property for this object.
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        Builder subTypeOneMember(String subTypeOneMember);
    }

    static final class BuilderImpl implements Builder {
        private String subTypeOneMember;

        private BuilderImpl() {
        }

        private BuilderImpl(SubTypeOne model) {
            subTypeOneMember(model.subTypeOneMember);
        }

        public final String getSubTypeOneMember() {
            return subTypeOneMember;
        }

        @Override
        public final Builder subTypeOneMember(String subTypeOneMember) {
            this.subTypeOneMember = subTypeOneMember;
            return this;
        }

        public final void setSubTypeOneMember(String subTypeOneMember) {
            this.subTypeOneMember = subTypeOneMember;
        }

        @Override
        public SubTypeOne build() {
            return new SubTypeOne(this);
        }

        @Override
        public List<SdkField<?>> sdkFields() {
            return SDK_FIELDS;
        }
    }
}

