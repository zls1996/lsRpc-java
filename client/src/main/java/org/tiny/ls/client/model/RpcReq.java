package org.tiny.ls.client.model;

import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import lombok.Data;

import java.io.Serializable;

/**
 * Created By 朱立松 on 2020/12/10
 */
@Data
public class RpcReq implements Serializable {

    private String requestName;

    private Message data;

    private String uuid;
}
