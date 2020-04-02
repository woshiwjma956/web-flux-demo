package com.ycorn.rector.demo;

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 14:49
 * @Function:
 * @Version 1.0
 */
public class FlowTest {

    public static void main(String[] args) throws InterruptedException {
        // Processor 中间操作
        ProcessorTest processorTest = new ProcessorTest();

        // Publisher 发布者
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        // 发布者绑定 中间操作者 Processor
        publisher.subscribe(processorTest);

        // 创建一个订阅者 Subscriber
        SubscriberTest subscriberTest = new SubscriberTest();

        // 中间操作者Processor 绑定下游 Subscriber
        processorTest.subscribe(subscriberTest);
        System.out.println(publisher.getSubscribers());
        System.out.println(publisher.getNumberOfSubscribers());
        // Publisher 发送数据
        publisher.submit(12);

        publisher.submit(11);
        TimeUnit.SECONDS.sleep(3);
        publisher.close();

        SubmissionPublisher<String> strPublisher = new SubmissionPublisher<>();
        strPublisher.subscribe(subscriberTest);
        strPublisher.submit("test");

        // 主线程延迟停止, 否则数据没有消费就退出
        Thread.currentThread().join(10000);
    }
}
