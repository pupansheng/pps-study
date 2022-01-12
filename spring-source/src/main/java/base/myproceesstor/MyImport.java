/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package base.myproceesstor;

import base.entity.P5;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Pu PanSheng, 2021/12/24
 * @version OPRA v1.0
 */
public class MyImport implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(P5.class);
        registry.registerBeanDefinition("pps",rootBeanDefinition);

    }
}
