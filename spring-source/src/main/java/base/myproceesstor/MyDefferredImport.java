/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package base.myproceesstor;

import base.entity.P5;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Pu PanSheng, 2021/12/24
 * @version OPRA v1.0
 */
public class MyDefferredImport implements DeferredImportSelector {



    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[0];
    }
}
