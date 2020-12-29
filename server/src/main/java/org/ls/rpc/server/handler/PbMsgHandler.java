package org.ls.rpc.server.handler;

import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.ls.rpc.server.annotation.RpcMethod;
import org.ls.rpc.server.model.RpcReq;
import org.ls.rpc.server.model.RpcRsp;
import org.springframework.stereotype.Component;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By 朱立松 on 2020/12/8
 */
@ChannelHandler.Sharable
@Slf4j
@Component
public class PbMsgHandler extends ChannelInboundHandlerAdapter {


    private Map<String, Object> rpcServiceMap = new ConcurrentHashMap<>(16);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("start to read client request");
        doExecute(ctx, msg);
    }

    private void doExecute(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcReq rpcReq = (RpcReq) msg;

        RpcRsp rpcRsp = new RpcRsp();
        String requestName = rpcReq.getRequestName();
        String clazzName = getClazzName(requestName);
        String methodName = getMethodName(requestName);

        if (!rpcServiceMap.containsKey(clazzName)) {
            log.info("service name : {} not found", clazzName);
            ctx.writeAndFlush(new Object());
            return;
        }

        Object targetBean = rpcServiceMap.get(clazzName);

        Class<?> clazz = targetBean.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            RpcMethod rpcMethod = method.getAnnotation(RpcMethod.class);
            if (!rpcMethod.name().equals(methodName)) {
                continue;
            }
            method.setAccessible(true);
            Class<Message> reqType = (Class<Message>) method.getParameterTypes()[0];
            Constructor<Message> constructor = reqType.getDeclaredConstructor();
            constructor.setAccessible(true);
            Parser<? extends Message> parser = constructor.newInstance().getParserForType();
            Message message = parser.parseFrom(rpcReq.getData());

            log.info("decode message type is : {}", message.getClass().toString());
            try {
                Object rsp = method.invoke(targetBean, message);
                rpcRsp.setData((Message) rsp);
            } catch (IllegalAccessException |
                    InvocationTargetException e) {
                log.error("rpc method invoke error : {}", e);
                ctx.writeAndFlush(new Object());
            }
            break;
        }


        if (rpcRsp.getData() == null) {
            log.error("unable to locate rpc method : " + rpcReq.getRequestName());
            ctx.writeAndFlush(new Object());
            return;
        }


        rpcRsp.setRequestName(rpcReq.getRequestName());
        rpcRsp.setUuid(rpcReq.getUuid());
        ctx.writeAndFlush(rpcRsp);

    }



    private String getClazzName(String requestName) {
        return requestName.substring(0, requestName.lastIndexOf("."));
    }

    private String getMethodName(String requestName) {
        return requestName.substring(requestName.lastIndexOf(".")+1);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        log.info("receive client connection request : {}", socketAddress.toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        log.info("close client connection channel : {}", socketAddress.toString());
        ctx.channel().close();
    }

    public void setServiceMap(Map<String, Object> rpcServiceMap) {
        this.rpcServiceMap = rpcServiceMap;
    }
}
