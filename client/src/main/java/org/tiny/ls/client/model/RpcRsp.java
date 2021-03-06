package org.tiny.ls.client.model;

import com.google.protobuf.Message;
import lombok.Data;

import java.io.Serializable;

/**
 * Created By 朱立松 on 2020/12/13
 */
@Data
public class RpcRsp implements Serializable {
    private String requestName;

    private byte[] data;

    private String uuid;
}
