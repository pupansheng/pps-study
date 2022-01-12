/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package base.customconfig;

import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pu PanSheng, 2021/8/10
 * @version OPRA v1.0
 */
@Component
public class MyPropertySourceLocator implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        //简单起见，这里直接创建一个map,你可以在这里写从哪里获取配置信息。
        Map<String,String> properties = new HashMap<>();
        properties.put("myName","lizo");
        MyPropertySource myPropertySource = new MyPropertySource("myPropertySource",properties);
        return myPropertySource;
    }

}
