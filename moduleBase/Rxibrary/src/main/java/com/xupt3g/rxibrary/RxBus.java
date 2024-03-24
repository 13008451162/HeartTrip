package com.xupt3g.rxibrary;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

/**
 * 项目名: HeartTrip
 * 文件名: RxBus
 *
 * @author: lukecc0
 * @data:2024/3/21 上午11:20
 * @about: TODO RxBus事件总线
 */

public class RxBus {
    private final Subject<Object> subject;

    private static volatile RxBus rxBus;

    private RxBus (){
        subject = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance(){
        if (rxBus == null){
            synchronized (RxBus.class){
                if (rxBus == null){
                    return new RxBus();
                }
            }
        }
        return rxBus;
    }

    public void post(Object object){
        subject.onNext(object);
    }

    public @NonNull <T> Observable<T> toObservable(Class<T> eventType){
        return subject.ofType(eventType);
    }
}
