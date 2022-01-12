/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Pu PanSheng, 2022/1/10
 * @version OPRA v1.0
 */
public class RxTest {


    public void asynnc(){

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        })
             /*   .observeOn(AndroidSchedulers.mainThread())//回调在主线程*/
                .subscribeOn(Schedulers.io())//执行在io线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        System.out.println("onNext:"+value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError="+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete()");
                    }
                });
    }

    public static void main(String args[]){



        //被观察者
        Observable novel=Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        });





       //观察者
        Observer<String> reader=new Observer<String>() {

            Disposable  mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable=d;
                System.out.println("订阅："+d);
            }

            @Override
            public void onNext(String value) {
                if ("2".equals(value)){
                    mDisposable.dispose();
                    return;
                }
                System.out.println("onNext:"+value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError="+e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete()");
            }
        };



        novel.subscribe(reader);//一行代码搞定





    }
}
