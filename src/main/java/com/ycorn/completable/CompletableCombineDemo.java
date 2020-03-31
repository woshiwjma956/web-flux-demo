package com.ycorn.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: wujianmin
 * @Date: 2020/3/31 16:28
 * @Function:
 * @Version 1.0
 */
public class CompletableCombineDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("======================= testThenRun =======================");
        testThenRun();
        System.out.println("======================= testAccept =======================");
        testAccept();
        System.out.println("======================= testCombine =======================");
        testCombine();
        System.out.println("======================= testCompose =======================");
        testCompose();
        Thread.currentThread().join();
    }

    /**
     * thenRun 一般用于在处理任务之后的收尾工作
     */
    public static void testThenRun() {
        CompletableFuture.supplyAsync(() -> ThreadLocalRandom.current().nextInt())
                .thenApply(i -> i * 10)
                .whenComplete((r, e) -> System.out.println(r+"before task finished"))
                .thenRun(() -> {
                    System.out.println("all task is finished");
                });
    }

    /**
     * 接受上流参数执行comsumer,没有返回值
     * 注意在accpet 之后已经被消费了.. 下游不会再有数据
     */
    public static void testAccept() {
        CompletableFuture.supplyAsync(() -> ThreadLocalRandom.current().nextInt())
                .thenAccept(i -> System.out.println(i * 10+"testAccept"))
                .whenComplete((r, e) -> System.out.println(r+"testAccept"));
    }

    /**
     * testCombine
     * Combine 合并2个CompletableFuture的执行结果
     * combine 编排 合并2个线程,Combine传入bifunction 参数1 线程1返回值,参数2 线程2的返回值
     */

    public static void testCombine() {
        CompletableFuture.supplyAsync(() -> ThreadLocalRandom.current().nextInt())
                .thenCombine(CompletableFuture.supplyAsync(() -> ThreadLocalRandom.current().nextInt()), (t1, t2) -> t1 + t2).thenAccept(r-> System.out.println(r+"testCombine"));
    }

    /**
     * compose 编排 拿到上一个future结果 传入一个function<上一个返回结果,处理之后返回的结果>
     */
    public static void testCompose() {
        CompletableFuture.supplyAsync(() -> ThreadLocalRandom.current().nextInt())
                .thenCompose(preThreadResult -> CompletableFuture.supplyAsync(() -> preThreadResult + "ComPose之后的结果")
                ).thenAccept(System.out::println);
    }
}
