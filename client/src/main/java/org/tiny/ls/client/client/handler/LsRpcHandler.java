package org.tiny.ls.client.client.handler;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tiny.ls.client.loadBalance.LoadBalanceStrategy;
import org.tiny.ls.client.loadBalance.LsRpcLoadBalance;
import org.tiny.ls.client.loadBalance.LsRpcLoadBalanceManager;
import org.tiny.ls.client.manager.LsRpcServerManager;
import org.tiny.ls.client.model.RpcReq;
import org.tiny.ls.client.model.RpcRsp;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

/**
 * Created By 朱立松 on 2020/12/13
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class LsRpcHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private LsRpcServerManager rpcServerManager;

    private Map<String, SynchronousQueue<RpcRsp>> rspMap = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("connect to rpc server : {}", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info("disconnect from remote server : {}", address);
        closeChannel(ctx);
        // 与该server断开连接
        rpcServerManager.removeServer(address);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRsp rpcRsp = (RpcRsp) msg;
        SynchronousQueue<RpcRsp> queue = rspMap.get(rpcRsp.getUuid());
        if (queue == null) {
            log.error("find rsp empty for uuid : {}", rpcRsp.getUuid());
            return;
        }
        queue.put(rpcRsp);
        rspMap.remove(rpcRsp.getUuid());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // TODO
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("rpc communication error : {}", cause.toString());
    }

    private void closeChannel(ChannelHandlerContext ctx) {
        ctx.channel().close();
    }

    public SynchronousQueue<RpcRsp> sendReq(RpcReq rpcReq, LoadBalanceStrategy loadBalanceStrategy) {
        String serviceName = rpcReq.getRequestName().substring(0, rpcReq.getRequestName().lastIndexOf("."));
        LsRpcLoadBalance loadBalance = LsRpcLoadBalanceManager.getLoadBalance(loadBalanceStrategy);

        if (null == loadBalance) {
            log.error("unknown lsRpc service loadBalance strategy :{}, method:{}", loadBalanceStrategy.getType(), serviceName);
            return null;
        }

        Channel channel = loadBalance.select(serviceName,rpcServerManager.getServiceChannels(serviceName));
        if (channel == null) {
            log.error("no suitable server channel to connect for service : {}", serviceName);
            return null;
        }

        log.info("current service method loadBalance to connect server : {}, method:{}",channel.remoteAddress(), serviceName);
        String uuid = rpcReq.getUuid();
        ChannelFuture channelFuture = channel.writeAndFlush(rpcReq);
        while (!channelFuture.isDone()) {
            try {
                channelFuture.await();
            } catch (InterruptedException e) {
                log.error("wait channelFuture error : {}", e);
                return null;
            }
        }
        if (!channelFuture.isSuccess()) {
            log.error("send request error : {}", channelFuture.cause().toString());
            return null;
        }
        SynchronousQueue<RpcRsp> queue = new SynchronousQueue<>();
        rspMap.put(uuid, queue);
        return queue;
    }
}
