package com.ycorn.stream;


import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author: wujianmin
 * @Date: 2020/3/31 10:33
 * @Function:
 * @Version 1.0
 */
public class StreamTest {

    public static void main(String[] args) {
//        lazyLoad();
        testCollectors();
    }

    public static void lazyLoad() {
        //惰性求值 如果不执行终止操作 不会打印
        Stream.generate(() -> ThreadLocalRandom.current().nextInt()).limit(100).map(i -> {
            System.out.println(i);
            return "没有终止操作的add " + i;
        });
        Stream.iterate("init value", r -> r + 1).limit(5).forEach(System.out::println);
    }

    public static void testCollectors() {
        // partition 2分
        Map<Boolean, List<Integer>> maps = IntStream.generate(() -> ThreadLocalRandom.current().nextInt()).limit(20).boxed().collect(Collectors.partitioningBy(t -> t % 2 == 0));
        System.out.println(maps);
        // word count
        String str = "my name is haha , I like haha , I lie code";
        System.out.println(Arrays.stream(str.split(" ")).map(s -> new Object[]{s, 1}).collect(Collectors.groupingBy(t -> t[0], Collectors.counting())));
    }
}
