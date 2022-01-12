package base;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * @discription;
 * @time 2020/9/22 16:14
 */
@Configuration
public class BeanFactoryMy implements BeanFactoryPostProcessor {

    public BeanFactoryMy(){
        System.out.println("2222");
    }


    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {




    }

}
