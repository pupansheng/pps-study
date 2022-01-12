/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package base.customconfig;

import org.springframework.core.env.EnumerablePropertySource;

import java.util.Map;

/**
 * @author Pu PanSheng, 2021/8/10
 * @version OPRA v1.0
 */
public class MyPropertySource extends EnumerablePropertySource<Map<String,String>> {

    public MyPropertySource(String name, Map source) {
        super(name, source);
    }

    //获取所有的配置名字
    @Override
    public String[] getPropertyNames() {
        return source.keySet().toArray(new String[source.size()]);
    }

    //根据配置返回对应的属性
    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }
}
