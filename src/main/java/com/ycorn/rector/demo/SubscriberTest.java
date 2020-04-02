package com.ycorn.rector.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 14:50
 * @Function:
 * @Version 1.0
 */
@Slf4j
public class SubscriberTest implements Flow.Subscriber<String> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        log.info("Subscriber receive data from process " + item);
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        log.info("Subscriber process error");

    }

    @Override
    public void onComplete() {
        log.info("Subscriber has done");
    }
}
