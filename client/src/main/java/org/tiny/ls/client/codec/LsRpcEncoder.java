package org.tiny.ls.client.codec;

import com.google.protobuf.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.tiny.ls.client.model.RpcReq;

import javax.xml.soap.MessageFactory;

/**
 * Created By 朱立松 on 2020/12/14
 */
@Slf4j
public class LsRpcEncoder extends MessageToByteEncoder<RpcReq> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcReq req, ByteBuf byteBuf) throws Exception {
        // 先写请求方法
        byte[] nameBytes = req.getRequestName().getBytes(CharsetUtil.UTF_8);
        byteBuf.writeInt(nameBytes.length);
        byteBuf.writeBytes(nameBytes);
        log.debug("name encode len is : {}", nameBytes.length);
        // 然后写uuid
        byte[] uuidBytes = req.getUuid().getBytes(CharsetUtil.UTF_8);
        byteBuf.writeInt(uuidBytes.length);
        byteBuf.writeBytes(uuidBytes);
        log.debug("uuid encode len is : {}", uuidBytes.length);

        // 传输pb数据段
        byte[] dataBytes = req.getData().toByteArray();
        log.debug("marshal pb data is : {}", new String(dataBytes, CharsetUtil.UTF_8));
        byteBuf.writeInt(dataBytes.length);
        byteBuf.writeBytes(dataBytes);
        log.debug("pb data encode len is : {}", dataBytes.length);

    }


}
