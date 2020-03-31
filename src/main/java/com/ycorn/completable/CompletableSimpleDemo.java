package com.ycorn.completable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: wujianmin
 * @Date: 2020/3/31 16:10
 * @Function:
 * @Version 1.0
 */
public class CompletableSimpleDemo {


    /**
     * 使用CompletableFuture实现并行化操作
     * 单一的CompletableFuture
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        CompletableFuture.supplyAsync(CompletableSimpleDemo::getaDouble, executorService)
                .thenApply(i -> i * 10d)
                .whenComplete((t,e)->{
                    Optional.of(t).ifPresent(System.out::println);
                    Optional.of(e).ifPresent(Throwable::printStackTrace);
        });
    }

    private static Double getaDouble() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble();
    }
}
