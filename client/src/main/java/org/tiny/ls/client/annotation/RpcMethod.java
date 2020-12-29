package org.tiny.ls.client.annotation;

import org.tiny.ls.client.loadBalance.LoadBalanceStrategy;
import org.tiny.ls.client.retry.RetryStrategy;

import java.lang.annotation.*;

/**
 * Created By 朱立松 on 2020/12/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RpcMethod {

    String name();

    int timeout() default 1000;

    // 负载均衡策略
    LoadBalanceStrategy loadBalance() default LoadBalanceStrategy.RANDOM;

    // 失败重试机制
    RetryStrategy retryStrategy() default RetryStrategy.FAIL_FAST;

    int retry() default 3;

}
