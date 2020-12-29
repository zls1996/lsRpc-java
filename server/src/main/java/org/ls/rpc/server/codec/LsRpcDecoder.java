package org.ls.rpc.server.codec;

import com.google.protobuf.Descriptors;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.ls.rpc.server.model.RpcReq;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created By 朱立松 on 2020/12/14
 */
@Slf4j
public class LsRpcDecoder extends ByteToMessageDecoder {

    Charset defaultCharset = CharsetUtil.UTF_8;


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf, List<Object> list) throws Exception {
        final int namePos = byteBuf.readInt();
        String requestName = (String) byteBuf.readCharSequence(namePos, defaultCharset);
        log.debug("namePos is : {}, requestName is : {}", namePos, requestName);

        final int uuidPos = byteBuf.readInt();
        String uuid = (String) byteBuf.readCharSequence(uuidPos, defaultCharset);
        log.debug("uuidPos is : {}, decode uuid is : {}", uuidPos, uuid);


        final int dataPos = byteBuf.readInt();

        final String pbData = ((String) byteBuf.readCharSequence(dataPos, defaultCharset));


        RpcReq rpcReq = new RpcReq();

        rpcReq.setRequestName(requestName);
        rpcReq.setData(pbData.getBytes());
        rpcReq.setUuid(uuid);
        list.add(rpcReq);
    }



}
