package com.jdamcd.threads;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

public final class Example {

    public static String blocking() {
        String threadInfo = Util.getThreadInfo();
        try {
            Thread.sleep(8 * 1000);
        } catch (InterruptedException e) {
            // Nothing
        }
        return threadInfo;
    }

    public static Observable<String> observable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(blocking());
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> altObservable() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just(blocking());
            }
        });
    }

}
