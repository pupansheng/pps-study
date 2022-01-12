package base.myproceesstor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author
 * @discription;
 * @time 2020/9/22 15:32
 */
//@Component
public class BeanPostProcessorMy implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }


    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {




        return bean;
    }
}
