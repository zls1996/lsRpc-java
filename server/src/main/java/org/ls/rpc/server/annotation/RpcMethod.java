package org.ls.rpc.server.annotation;

import java.lang.annotation.*;

/**
 * Created By 朱立松 on 2020/12/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RpcMethod {

    String name();
}
