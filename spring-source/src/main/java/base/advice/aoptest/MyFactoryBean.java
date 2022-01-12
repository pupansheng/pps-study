/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package base.advice.aoptest;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author Pu PanSheng, 2021/12/24
 * @version OPRA v1.0
 */

public class MyFactoryBean implements FactoryBean{


    @Override
    public Object getObject() throws Exception {

        return new TestServiceImpl();
    }

    @Override
    public Class<?> getObjectType() {
        return TestService.class;
    }

}
