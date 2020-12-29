package org.tiny.ls.client.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created By 朱立松 on 2020/12/21
 */
@Component
public class SpringApplicationUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public ApplicationContext getApplication() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
