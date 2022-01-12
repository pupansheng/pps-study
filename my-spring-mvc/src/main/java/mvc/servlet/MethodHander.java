/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package mvc.servlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
public class MethodHander {

    private Object t;
    private Method method;

    public Object getT() {
        return t;
    }

    public void setT(Object t) {
        this.t = t;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

  public Object invoke(Object[] args){

      try {
          return method.invoke(t, args);
      } catch (IllegalAccessException e) {
          e.printStackTrace();
      } catch (InvocationTargetException e) {
          e.printStackTrace();
      }

      throw new RuntimeException("执行错误！");
  }
}
