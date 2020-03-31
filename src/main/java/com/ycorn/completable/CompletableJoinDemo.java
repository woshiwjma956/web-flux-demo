package com.ycorn.completable;

import com.ycorn.stream.Dish;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @Author: wujianmin
 * @Date: 2020/3/31 16:59
 * @Function:
 * @Version 1.0
 */
public class CompletableJoinDemo {

    public static void main(String[] args) {
        List<Dish> menu = Dish.menu;
        Integer total = menu.stream().map(dish -> CompletableFuture.supplyAsync(() -> getDishCalories2(dish)))
                .map(future -> future.thenApply(i -> i * 10))
                .map(CompletableFuture::join)
                .collect(Collectors.reducing(0, Integer::sum));
        System.out.println(total);
    }

    public static List<Integer> getDishCalories(List<Dish> list) {
        List<Integer> result = list.stream().map(Dish::getCalories).collect(toList());
        return result;
    }

    public static int getDishCalories2(Dish dish) {
        return dish.getCalories();
    }
}
