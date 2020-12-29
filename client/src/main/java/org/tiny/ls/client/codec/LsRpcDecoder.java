package org.tiny.ls.client.codec;

import com.google.protobuf.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.tiny.ls.client.model.RpcRsp;

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

        final int uuidPos = byteBuf.readInt();
        String uuid = (String) byteBuf.readCharSequence(uuidPos, defaultCharset);


        final int dataPos = byteBuf.readInt();
        final String pbData = ((String) byteBuf.readCharSequence(dataPos, defaultCharset));


        RpcRsp rpcRsp = new RpcRsp();
        rpcRsp.setRequestName(requestName);
        rpcRsp.setUuid(uuid);
        rpcRsp.setData(pbData.getBytes());
        list.add(rpcRsp);
    }


}
