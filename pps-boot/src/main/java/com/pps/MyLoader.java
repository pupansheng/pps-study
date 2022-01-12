/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps;

import sun.misc.PerfCounter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pu PanSheng, 2021/12/28
 * @version OPRA v1.0
 */
public class MyLoader extends URLClassLoader {

    //c://lib
    private String root;

    public void setRoot(String root) {
        this.root = root;
    }

    public MyLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static MyLoader getLoader(String root){

        File file=new File(root);
        List<URL> urls=new ArrayList<>();
        for (File listFile : file.listFiles()) {

            if(listFile.isFile()) {

                String name = listFile.getName();
                if(name.endsWith(".jar")){
                    try {
                        URL url = listFile.toURI().toURL();
                        urls.add(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }else if(name.endsWith(".class")){

                    try {
                        URL url = listFile.toURI().toURL();
                        urls.add(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                }


            }

        }

        URL[] urls1 = urls.toArray(new URL[0]);

        MyLoader myLoader=new MyLoader(urls1,ClassLoader.getSystemClassLoader());
        myLoader.setRoot(root);

        return myLoader;

    }

    /**
     * 改变双亲委托模型  先加载自己的 如果自己加载不到 再父亲加载
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        System.out.println(this+" 加载class: "+name);
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();

               /*
                原本的

               try {
                    if (parent != null) {
                        c = parent.loadClass(name, false);
                    } else {
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }*/





                    // If still not found, then invoke findClass in order


                    // to find the class.

                 //先自己加
                {
                    try {
                        long t1 = System.nanoTime();
                        c = findClass(name);
                        // this is the defining class loader; record the stats
                        PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                        PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                        PerfCounter.getFindClasses().increment();
                    } catch (ClassNotFoundException e) {

                    }
                }


                if(c==null) {
                    //如果还是加载不到 那么父类加载
                    try {
                        ClassLoader parent = getParent();
                        if (parent != null) {
                            c = parent.loadClass(name);
                        }else {
                        } 
                    } catch (ClassNotFoundException e) {

                    }
                }


            }
            if(c==null){
                
                throw new ClassNotFoundException(name);
            }

            return c;
        }
    }
}
