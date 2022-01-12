/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package base.customconfig;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Pu PanSheng, 2021/8/10
 * @version OPRA v1.0
 */
@Component
public class EnvProcessor implements EnvironmentAware {
    @Override
    public void setEnvironment(Environment environment) {

        StandardEnvironment environment1 = (StandardEnvironment) environment;
        MutablePropertySources propertySources = environment1.getPropertySources();
        System.out.println(environment);


    }
}
