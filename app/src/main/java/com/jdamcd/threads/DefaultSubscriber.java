package com.jdamcd.threads;

import rx.Subscriber;

public class DefaultSubscriber<T> extends Subscriber<T> {

    @Override
    public void onNext(T args) {}

    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e) {
        // Do global handling for uncaught exceptions
    }

}