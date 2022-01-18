/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package 泛型测试;

import org.junit.Test;

import java.io.*;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Pu PanSheng, 2022/1/17
 * @version OPRA v1.0
 */
public class FunctionTest {

    @Test
    public void f1(){

        Function<String, Integer> function=s->{return Integer.parseInt(s);};

        Class<? extends Function> aClass1 = function.getClass();


        Class<? extends Function> aClass = aClass1;

         eq(function);



    }
    public void eq(Function<?, ?> lambda) {


        if (!lambda.getClass().isSynthetic()) {
            throw new RuntimeException("该方法仅能传入 lambda 表达式产生的合成类");
        }


        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(lambda);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " +  ex);
        }
        byte[] bytes1 = baos.toByteArray();

        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(bytes1)) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz = super.resolveClass(objectStreamClass);
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        }) {

            int available = objIn.available();
            byte[] bytes=new byte[available];
            objIn.read(bytes);

            SerializedLambda v=(SerializedLambda)objIn.readObject();

            System.out.println(v);
            System.out.println(v.getImplMethodName());

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException("This is impossible to happen", e);
        }


    }
    public static Class getSuperClassGenericType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();

        for(Class superClass = clazz; !(genType instanceof ParameterizedType); genType = superClass.getGenericSuperclass()) {

            if (superClass == Object.class) {
                return Object.class;
            }

            superClass = superClass.getSuperclass();
        }

        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        if (index < params.length && index >= 0) {
            if (!(params[index] instanceof Class) && params[index] instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType)params[index];
                return (Class)pType.getRawType();
            } else {
                return (Class)params[index];
            }
        } else {
            return Object.class;
        }
    }


}
