package org.ls.rpc.server.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * Created By 朱立松 on 2020/12/10
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Service
public @interface RpcProvider {
    String name();
}
