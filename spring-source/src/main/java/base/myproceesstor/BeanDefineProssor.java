package base.myproceesstor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.expression.spel.ast.BeanReference;
import org.springframework.stereotype.Component;

/**
 * 时机：
 *
 */
@Component
public class BeanDefineProssor implements BeanDefinitionRegistryPostProcessor {


    public BeanDefineProssor(){

    }



    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {


        System.out.println(beanDefinitionRegistry);

    }


    /**
     *
     * @param configurableListableBeanFactory
     * @throws BeansException
     */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        System.out.println(configurableListableBeanFactory);

    }
}
