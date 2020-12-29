package org.tiny.ls.client.proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created By 朱立松 on 2020/12/21
 */
public class RpcServiceFactory<T> implements FactoryBean<T> {

    private Class<T> interfaceClazz;

    private InvocationHandler handler;

    public RpcServiceFactory(Class<T> interfaceClazz, InvocationHandler handler) {
        this.interfaceClazz = interfaceClazz;
        this.handler = handler;
    }

    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(interfaceClazz.getClassLoader(),
                new Class<?>[]{interfaceClazz}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
