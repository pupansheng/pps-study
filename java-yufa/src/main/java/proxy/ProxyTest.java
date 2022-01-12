package proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Pu PanSheng, 2022/1/12
 * @version v1.0
 */
public class ProxyTest {



    @Test
    public void proxyTest(){


        InvocationHandler handler=new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return "参数"+args;
            }
        };


       Mapper mapper= (Mapper)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Mapper.class}, /*(p,m,args)->{


            return "参数"+args;

        }*/handler);

        System.out.println(mapper.print("123"));

    }
}
