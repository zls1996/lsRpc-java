package org.tiny.ls.client.annotation;


import java.lang.annotation.*;

/**
 * Created By 朱立松 on 2020/12/10
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RpcConsumer {
    String name();
}
