package org.ls.rpc.server.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created By 朱立松 on 2020/12/10
 */
@Data
public class RpcReq implements Serializable {

    private String requestName;

    private byte[] data;

    private String uuid;
}
