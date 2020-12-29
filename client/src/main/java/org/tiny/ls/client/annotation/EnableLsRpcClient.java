package org.tiny.ls.client.annotation;

import org.springframework.context.annotation.Import;
import org.tiny.ls.client.configuration.LsRpcBeanConfiguration;
import org.tiny.ls.client.configuration.LsRpcBeanProcessor;

import java.lang.annotation.*;

/**
 * Created By 朱立松 on 2020/12/29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({LsRpcBeanConfiguration.class, LsRpcBeanProcessor.class})
public @interface EnableLsRpcClient {
}
