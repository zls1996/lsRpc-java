// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hello.proto

package com.example.demo.service.pb;

public final class Hello {
  private Hello() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MultiRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:protobuf.MultiRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 args1 = 1;</code>
     */
    long getArgs1();

    /**
     * <code>int64 args2 = 2;</code>
     */
    long getArgs2();
  }
  /**
   * Protobuf type {@code protobuf.MultiRequest}
   */
  public  static final class MultiRequest extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:protobuf.MultiRequest)
      MultiRequestOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use MultiRequest.newBuilder() to construct.
    private MultiRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MultiRequest() {
      args1_ = 0L;
      args2_ = 0L;
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private MultiRequest(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownFieldProto3(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {

              args1_ = input.readInt64();
              break;
            }
            case 16: {

              args2_ = input.readInt64();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Hello.internal_static_protobuf_MultiRequest_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Hello.internal_static_protobuf_MultiRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Hello.MultiRequest.class, Hello.MultiRequest.Builder.class);
    }

    public static final int ARGS1_FIELD_NUMBER = 1;
    private long args1_;
    /**
     * <code>int64 args1 = 1;</code>
     */
    public long getArgs1() {
      return args1_;
    }

    public static final int ARGS2_FIELD_NUMBER = 2;
    private long args2_;
    /**
     * <code>int64 args2 = 2;</code>
     */
    public long getArgs2() {
      return args2_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (args1_ != 0L) {
        output.writeInt64(1, args1_);
      }
      if (args2_ != 0L) {
        output.writeInt64(2, args2_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (args1_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, args1_);
      }
      if (args2_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(2, args2_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof Hello.MultiRequest)) {
        return super.equals(obj);
      }
      Hello.MultiRequest other = (Hello.MultiRequest) obj;

      boolean result = true;
      result = result && (getArgs1()
          == other.getArgs1());
      result = result && (getArgs2()
          == other.getArgs2());
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + ARGS1_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getArgs1());
      hash = (37 * hash) + ARGS2_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getArgs2());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Hello.MultiRequest parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Hello.MultiRequest parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Hello.MultiRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Hello.MultiRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Hello.MultiRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Hello.MultiRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Hello.MultiRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Hello.MultiRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Hello.MultiRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static Hello.MultiRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Hello.MultiRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Hello.MultiRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Hello.MultiRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code protobuf.MultiRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:protobuf.MultiRequest)
        Hello.MultiRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return Hello.internal_static_protobuf_MultiRequest_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return Hello.internal_static_protobuf_MultiRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Hello.MultiRequest.class, Hello.MultiRequest.Builder.class);
      }

      // Construct using org.tiny.ls.tinyRpc.server.demo.pb.Hello.MultiRequest.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        args1_ = 0L;

        args2_ = 0L;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return Hello.internal_static_protobuf_MultiRequest_descriptor;
      }

      public Hello.MultiRequest getDefaultInstanceForType() {
        return Hello.MultiRequest.getDefaultInstance();
      }

      public Hello.MultiRequest build() {
        Hello.MultiRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public Hello.MultiRequest buildPartial() {
        Hello.MultiRequest result = new Hello.MultiRequest(this);
        result.args1_ = args1_;
        result.args2_ = args2_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Hello.MultiRequest) {
          return mergeFrom((Hello.MultiRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Hello.MultiRequest other) {
        if (other == Hello.MultiRequest.getDefaultInstance()) return this;
        if (other.getArgs1() != 0L) {
          setArgs1(other.getArgs1());
        }
        if (other.getArgs2() != 0L) {
          setArgs2(other.getArgs2());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Hello.MultiRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Hello.MultiRequest) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long args1_ ;
      /**
       * <code>int64 args1 = 1;</code>
       */
      public long getArgs1() {
        return args1_;
      }
      /**
       * <code>int64 args1 = 1;</code>
       */
      public Builder setArgs1(long value) {

        args1_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 args1 = 1;</code>
       */
      public Builder clearArgs1() {

        args1_ = 0L;
        onChanged();
        return this;
      }

      private long args2_ ;
      /**
       * <code>int64 args2 = 2;</code>
       */
      public long getArgs2() {
        return args2_;
      }
      /**
       * <code>int64 args2 = 2;</code>
       */
      public Builder setArgs2(long value) {

        args2_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 args2 = 2;</code>
       */
      public Builder clearArgs2() {

        args2_ = 0L;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFieldsProto3(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:protobuf.MultiRequest)
    }

    // @@protoc_insertion_point(class_scope:protobuf.MultiRequest)
    private static final Hello.MultiRequest DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Hello.MultiRequest();
    }

    public static Hello.MultiRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<MultiRequest>
        PARSER = new com.google.protobuf.AbstractParser<MultiRequest>() {
      public MultiRequest parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new MultiRequest(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MultiRequest> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<MultiRequest> getParserForType() {
      return PARSER;
    }

    public Hello.MultiRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public interface MultiReplyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:protobuf.MultiReply)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 result = 1;</code>
     */
    long getResult();

    /**
     * <code>string address = 2;</code>
     */
    String getAddress();
    /**
     * <code>string address = 2;</code>
     */
    com.google.protobuf.ByteString
        getAddressBytes();
  }
  /**
   * Protobuf type {@code protobuf.MultiReply}
   */
  public  static final class MultiReply extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:protobuf.MultiReply)
      MultiReplyOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use MultiReply.newBuilder() to construct.
    private MultiReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MultiReply() {
      result_ = 0L;
      address_ = "";
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private MultiReply(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownFieldProto3(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {

              result_ = input.readInt64();
              break;
            }
            case 18: {
              String s = input.readStringRequireUtf8();

              address_ = s;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Hello.internal_static_protobuf_MultiReply_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Hello.internal_static_protobuf_MultiReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Hello.MultiReply.class, Hello.MultiReply.Builder.class);
    }

    public static final int RESULT_FIELD_NUMBER = 1;
    private long result_;
    /**
     * <code>int64 result = 1;</code>
     */
    public long getResult() {
      return result_;
    }

    public static final int ADDRESS_FIELD_NUMBER = 2;
    private volatile Object address_;
    /**
     * <code>string address = 2;</code>
     */
    public String getAddress() {
      Object ref = address_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        address_ = s;
        return s;
      }
    }
    /**
     * <code>string address = 2;</code>
     */
    public com.google.protobuf.ByteString
        getAddressBytes() {
      Object ref = address_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        address_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (result_ != 0L) {
        output.writeInt64(1, result_);
      }
      if (!getAddressBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, address_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (result_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, result_);
      }
      if (!getAddressBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, address_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof Hello.MultiReply)) {
        return super.equals(obj);
      }
      Hello.MultiReply other = (Hello.MultiReply) obj;

      boolean result = true;
      result = result && (getResult()
          == other.getResult());
      result = result && getAddress()
          .equals(other.getAddress());
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + RESULT_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getResult());
      hash = (37 * hash) + ADDRESS_FIELD_NUMBER;
      hash = (53 * hash) + getAddress().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Hello.MultiReply parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Hello.MultiReply parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Hello.MultiReply parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Hello.MultiReply parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Hello.MultiReply parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Hello.MultiReply parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Hello.MultiReply parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Hello.MultiReply parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Hello.MultiReply parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static Hello.MultiReply parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Hello.MultiReply parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Hello.MultiReply parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Hello.MultiReply prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code protobuf.MultiReply}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:protobuf.MultiReply)
        Hello.MultiReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return Hello.internal_static_protobuf_MultiReply_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return Hello.internal_static_protobuf_MultiReply_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Hello.MultiReply.class, Hello.MultiReply.Builder.class);
      }

      // Construct using org.tiny.ls.tinyRpc.server.demo.pb.Hello.MultiReply.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        result_ = 0L;

        address_ = "";

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return Hello.internal_static_protobuf_MultiReply_descriptor;
      }

      public Hello.MultiReply getDefaultInstanceForType() {
        return Hello.MultiReply.getDefaultInstance();
      }

      public Hello.MultiReply build() {
        Hello.MultiReply result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public Hello.MultiReply buildPartial() {
        Hello.MultiReply result = new Hello.MultiReply(this);
        result.result_ = result_;
        result.address_ = address_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Hello.MultiReply) {
          return mergeFrom((Hello.MultiReply)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Hello.MultiReply other) {
        if (other == Hello.MultiReply.getDefaultInstance()) return this;
        if (other.getResult() != 0L) {
          setResult(other.getResult());
        }
        if (!other.getAddress().isEmpty()) {
          address_ = other.address_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Hello.MultiReply parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Hello.MultiReply) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long result_ ;
      /**
       * <code>int64 result = 1;</code>
       */
      public long getResult() {
        return result_;
      }
      /**
       * <code>int64 result = 1;</code>
       */
      public Builder setResult(long value) {

        result_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 result = 1;</code>
       */
      public Builder clearResult() {

        result_ = 0L;
        onChanged();
        return this;
      }

      private Object address_ = "";
      /**
       * <code>string address = 2;</code>
       */
      public String getAddress() {
        Object ref = address_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          address_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string address = 2;</code>
       */
      public com.google.protobuf.ByteString
          getAddressBytes() {
        Object ref = address_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          address_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string address = 2;</code>
       */
      public Builder setAddress(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }

        address_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string address = 2;</code>
       */
      public Builder clearAddress() {

        address_ = getDefaultInstance().getAddress();
        onChanged();
        return this;
      }
      /**
       * <code>string address = 2;</code>
       */
      public Builder setAddressBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

        address_ = value;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFieldsProto3(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:protobuf.MultiReply)
    }

    // @@protoc_insertion_point(class_scope:protobuf.MultiReply)
    private static final Hello.MultiReply DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Hello.MultiReply();
    }

    public static Hello.MultiReply getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<MultiReply>
        PARSER = new com.google.protobuf.AbstractParser<MultiReply>() {
      public MultiReply parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new MultiReply(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MultiReply> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<MultiReply> getParserForType() {
      return PARSER;
    }

    public Hello.MultiReply getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protobuf_MultiRequest_descriptor;
  private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_protobuf_MultiRequest_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protobuf_MultiReply_descriptor;
  private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_protobuf_MultiReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\013hello.proto\022\010protobuf\",\n\014MultiRequest\022" +
      "\r\n\005args1\030\001 \001(\003\022\r\n\005args2\030\002 \001(\003\"-\n\nMultiRe" +
      "ply\022\016\n\006result\030\001 \001(\003\022\017\n\007address\030\002 \001(\t2?\n\004" +
      "Demo\0227\n\005Multi\022\026.protobuf.MultiRequest\032\024." +
      "protobuf.MultiReply\"\000b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_protobuf_MultiRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_protobuf_MultiRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_protobuf_MultiRequest_descriptor,
        new String[] { "Args1", "Args2", });
    internal_static_protobuf_MultiReply_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_protobuf_MultiReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_protobuf_MultiReply_descriptor,
        new String[] { "Result", "Address", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}