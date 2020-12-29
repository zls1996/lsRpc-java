package org.tiny.ls.client.proxy;

import com.google.protobuf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tiny.ls.client.annotation.RpcMethod;
import org.tiny.ls.client.annotation.RpcConsumer;
import org.tiny.ls.client.client.LsRpcClient;
import org.tiny.ls.client.exception.LsRpcConsumerException;
import org.tiny.ls.client.exception.LsRpcMethodException;
import org.tiny.ls.client.exception.LsRpcRequestException;
import org.tiny.ls.client.model.RpcReq;
import org.tiny.ls.client.model.RpcRsp;
import org.tiny.ls.client.retry.LsRpcRetry;
import org.tiny.ls.client.retry.LsRpcRetryStrategyManager;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created By 朱立松 on 2020/12/13
 */

@Component
@Slf4j
public class RpcServiceProxy<T> implements InvocationHandler {


    @Resource
    private LsRpcClient client;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return proxy == args[0];
            }
            if ("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            }

            if ("toString".equals(name)) {
                return proxy.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(proxy))
                        + ", with InvocationHandler " + this;
            }

            throw new IllegalStateException(String.valueOf(method));
        }
        String className = method.getDeclaringClass().getName();
        RpcConsumer rpcConsumer = method.getDeclaringClass().getAnnotation(RpcConsumer.class);

        if (rpcConsumer == null) {
            log.warn("current proxy Service object is not RpcConsumer service");
            throw new LsRpcConsumerException("current Service object is not RpcConsumer service, use annotation @RpcConsumer to decorate class :" + className);
        }
        RpcMethod rpcMethod = method.getAnnotation(RpcMethod.class);
        if (rpcMethod == null) {
            throw new LsRpcMethodException("current request method is not RpcMethod, use annotation @RpcMethod to decorate method : "+ className + "."+ method.getName());
        }
        String requestName = buildMethodName(rpcConsumer.name(), rpcMethod.name());
        Class<Message> returnType = (Class<Message>) method.getReturnType();

        RpcReq rpcReq = new RpcReq();
        Message pbData = (Message) args[0];
        rpcReq.setData(pbData);
        rpcReq.setRequestName(requestName);
        String uuid = UUID.randomUUID().toString();
        rpcReq.setUuid(uuid);

        LsRpcRetry retryPolicy = LsRpcRetryStrategyManager.getRetryPolicy(rpcMethod.retryStrategy());

        int retryTimes = rpcMethod.retry();
        int count = 0;
        // 未达到结束失败重试退出条件，就继续请求
        while(! retryPolicy.reachedLastRetry(uuid, retryTimes)) {
            if (count != 0) {
                log.info("retry method with time : {}, method:{}", count, requestName);
            }
            SynchronousQueue<RpcRsp> queue = client.send(rpcReq, rpcMethod.loadBalance());

            // 发送失败
            if (queue == null) {
                log.error("send rpc request failed, no rpc server available to connect");
                // 失败重试
                retryPolicy.multiFailCount(uuid);
                count++;
                continue;
            }

            // block
            final RpcRsp rsp = queue.poll(rpcMethod.timeout(), TimeUnit.MILLISECONDS);

            // 请求超时
            if (rsp == null) {
                log.error("get rpc rsp timeout, method: {} timeout config is : {} ms ",requestName, rpcMethod.timeout());
                // 失败重试
                retryPolicy.multiFailCount(uuid);
                count++;
                continue;
            }

            Constructor<Message> constructor = returnType.getDeclaredConstructor();
            constructor.setAccessible(true);
            Parser<? extends Message> parser = constructor.newInstance().getParserForType();
            return parser.parseFrom(rsp.getData()).toBuilder().build();
        }
        throw new LsRpcRequestException("send lsRpc request failed. Maybe no available server to connect or request timeout");
    }

    private String buildMethodName(String serviceName, String methodName) {

        return serviceName + "." + methodName;
    }
}
