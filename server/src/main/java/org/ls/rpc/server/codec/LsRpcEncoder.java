package org.ls.rpc.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.ls.rpc.server.model.RpcRsp;

/**
 * Created By 朱立松 on 2020/12/14
 */
public class LsRpcEncoder extends MessageToByteEncoder<RpcRsp> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcRsp rsp, ByteBuf byteBuf) throws Exception {
        // 先写请求方法
        byteBuf.writeInt(rsp.getRequestName().getBytes().length);
        byteBuf.writeBytes(rsp.getRequestName().getBytes());
        // 然后写uuid
        byteBuf.writeInt(rsp.getUuid().getBytes().length);
        byteBuf.writeBytes(rsp.getUuid().getBytes());
        // 传输pb数据段
        byteBuf.writeInt(rsp.getData().toByteArray().length);
        byteBuf.writeBytes(rsp.getData().toByteArray());
    }

}
