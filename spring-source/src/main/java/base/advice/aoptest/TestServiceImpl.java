package base.advice.aoptest;

import org.springframework.stereotype.Component;

/**
 * @author
 * @discription;
 * @time 2021/2/21 13:40
 */
//@Component
public class TestServiceImpl implements TestService {

    @Override
    public void fun() {
        System.out.println("业务操作哦----------");
    }
}
