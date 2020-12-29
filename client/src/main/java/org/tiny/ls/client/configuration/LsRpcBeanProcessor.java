package org.tiny.ls.client.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created By 朱立松 on 2020/12/29
 */
@Slf4j
public class LsRpcBeanProcessor implements BeanPostProcessor, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        // 在所有bean加载之前执行setBeanFactory,用处是在接入服务加载service前，
        // 提前加载SDK中的LsRpcConfiguration，用于client的初始化，以及代理对象的注入，防止接入服务中的接口报空指针错误
        LsRpcConfiguration bean = beanFactory.getBean(LsRpcConfiguration.class);
        if (bean == null) {
            throw new RuntimeException("load LsRpcConfiguration empty");
        }
    }

}
