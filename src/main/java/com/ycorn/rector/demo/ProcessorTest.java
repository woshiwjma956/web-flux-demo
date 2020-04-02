package com.ycorn.rector.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 14:43
 * @Function:
 * @Version 1.0
 */
@Slf4j
public class ProcessorTest extends SubmissionPublisher<String> implements Flow.Processor<Integer, String> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        log.info("Processor on subscribe..");
        // ask for data
        this.subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        log.info("Processor on Next " + item);
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        if (item % 2 == 0) {
            // filter data
            this.submit("Process Info " + item);
        }
        // process done ask for new request
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        log.info("Processor Error...");
        // cancel
        this.subscription.cancel();
    }

    @Override
    public void onComplete() {
        log.info(" Process has done ");
        this.close();
    }
}
