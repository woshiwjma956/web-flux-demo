package com.ycorn.completable;

import com.ycorn.stream.Dish;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @Author: wujianmin
 * @Date: 2019/8/30 16:53
 * @Function:
 * @Version 1.0
 */
public class CompletableRunAfterDemo {

    public static void main(String[] args) throws InterruptedException {
//        testRunAfterBoth();
//        testAcceptEither();
//        testApplyToEither();
//        testRunAfterEither();
//        testOfAll();
        testOfAny();
        Thread.currentThread().join();

    }

    /**
     * testRunAfterBoth 在执行2个任务之后 最后接受工作,参数1 新的一个线程任务 参数2 Runnable接口
     */
    public static void testRunAfterBoth() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("task 1");
            return 1;
        })
                .thenApplyAsync(i -> i * 10)
                .runAfterBoth(
                        CompletableFuture.supplyAsync(() -> {
                            System.out.println("task 2 ");
                            return 2.0d;
                        }), () -> System.out.println("done"));
    }

    /**
     * 2个future 任意执行完一个之后进入,第二个consumer逻辑 acceptEither(另一个future,任意执行完以后之后接下来的逻辑)
     */
    public static void testAcceptEither() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task 1");
            return 1;
        })
                .acceptEither(
                        CompletableFuture.supplyAsync(() -> {
                            System.out.println("task 2");
                            return 2;
                        }),
                        System.out::println
                );
    }

    /**
     * ApplyToEither 与 AcceptEither 类似,只是第一个参数传入的function不是comsumer 有返回值.可以继续thenAccept
     */
    public static void testApplyToEither() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task 1");
            return 1;
        })
                .applyToEither(
                        CompletableFuture.supplyAsync(() -> {
                            System.out.println("task 2");
                            return 2;
                        })
                        , (r) -> {
                            System.out.println(r);
                            return 0;
                        })
                .thenAccept(System.out::println);
    }

    /**
     * runAfterEither 不关心返回值,只要有一个feature 返回就执行
     */
    public static void testRunAfterEither(){
        CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task 1");
            return 1;
        }).runAfterEither(CompletableFuture.supplyAsync(()->{
            System.out.println("task 2");
            return 2;
        }),()->System.out.println("either or the tasks is Done"));
    }

    /**
     * future Array 列表全部执行完毕之后继续调用
     */
    public static void testOfAll(){
        List<Dish> list = Dish.menu;
        List<CompletableFuture<Integer>> result = list.stream().map(i -> CompletableFuture.supplyAsync(() -> {

            int dishCalories2 = getDishCalories2(i);
            System.out.println(dishCalories2);
            return dishCalories2;
        }))
                .collect(Collectors.toList());
        CompletableFuture[] futures = result.toArray(new CompletableFuture[result.size()]);
        CompletableFuture.allOf(futures).thenRun(()->System.out.println("evething is done"));
    }
    /**
     * future Array 列表任意一个future执行完毕之后继续调用
     */
    public static void testOfAny(){
        List<Dish> list = Dish.menu;
        List<CompletableFuture<Integer>> result = list.stream().map(i -> CompletableFuture.supplyAsync(() -> {

            int dishCalories2 = getDishCalories2(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(dishCalories2);
            return dishCalories2;
        }))
                .collect(Collectors.toList());
        CompletableFuture[] futures = result.toArray(new CompletableFuture[result.size()]);
        CompletableFuture.anyOf(futures).thenRun(()->System.out.println("someOne is done"));
    }

    public static int getDishCalories2(Dish dish){
        return dish.getCalories();
    }
}
