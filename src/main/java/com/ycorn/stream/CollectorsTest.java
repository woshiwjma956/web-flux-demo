package com.ycorn.stream;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * @Author: wujianmin
 * @Date: 2019/8/27 10:30
 * @Function:
 * @Version 1.0
 */
public class CollectorsTest {
    private static List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
//        testAverage();
//        testCollectingAndThen();
//        testCounting();
//        testGroupBy();
//        testGroupByWithDownstream();
//        testJoin();
//        testJoinWithSpliter();
//        testJoinWithDelimitAndPrefixAndSuffix();
//        testMapping();
//        testMapping2();
        testMaxByAndMinBy();
//        testPartitioningBy();
//        testPartitioningByWithDownstream();
//        testReduce();
//        testReduceWithIndentifyAndBinaryOperator();
//        testSummarizing();
//        testSumming();
//        testToMap();
        testToConcurrentMap();
    }

    /**
     * 平均数
     */
    public static void testAverage() {
        Optional.ofNullable(menu.stream().collect(Collectors.averagingInt(Dish::getCalories))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.averagingLong(Dish::getCalories))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.averagingDouble(Dish::getCalories))).ifPresent(System.out::println);
    }


    /**
     * foreach
     *
     * @param dishs
     * @return
     */
    public static int printDish(List<Dish> dishs) {
        dishs.stream().forEach(System.out::println);
        return dishs.size();
    }

    /**
     * 收集之后再提供一个Supplier消费 Supplier是一个消费收集到所有数据集合之后的Supplier
     */
    public static void testCollectingAndThen() {
        Integer ret = menu.stream().collect(Collectors.collectingAndThen(Collectors.toList(), CollectorsTest::printDish));
        System.out.println(ret);
    }

    public static void testCounting() {
        Optional.ofNullable(menu.stream().filter(t -> t.getCalories() > 500).collect(Collectors.counting()));
    }

    /**
     * 分组
     */
    public static void testGroupBy() {
        Optional.of(menu.stream().collect(Collectors.groupingBy(Dish::getType))).ifPresent(System.out::println);
    }

    /**
     * 分组并提供下游操作 分完组之后,拿到分组之后的结果进行操作 wordCount
     */
    public static void testGroupByWithDownstream() {
        Map<Dish.Type, Long> result1 = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));

        Map<Dish.Type, Integer> result = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    /**
     * 分组 之后 可以最生成的Map类型提供一个supplier指定 最后对分组结构进行操作
     * supplier 参数 参考JDK注释 a function which, when called, produces a new empty{@code Map} of the desired type
     */
    public static void testGroupByWithDownstreamAndSupplier() {
        TreeMap<Dish.Type, Integer> treeMap = menu.stream().collect(Collectors.groupingBy(Dish::getType, TreeMap::new, Collectors.summingInt(Dish::getCalories)));
        Optional.ofNullable(treeMap).ifPresent(System.out::println);
    }

    /**
     * 字符串拼接
     */
    public static void testJoin() {
        String result = menu.stream().map(Dish::getName).collect(Collectors.joining());
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    /**
     * 带分隔符的字符串拼接
     */
    public static void testJoinWithSpliter() {
        Optional.ofNullable(menu.stream().map(Dish::getName).collect(Collectors.joining(","))).ifPresent(System.out::println);
    }

    /**
     * 带收尾标志符合分隔符的字符串拼接
     */
    public static void testJoinWithDelimitAndPrefixAndSuffix() {
        Optional.ofNullable(menu.stream().map(Dish::getName).collect(Collectors.joining(",", "[", "]"))).ifPresent(System.out::println);
    }

    /**
     * 分组
     */
    public static void testMapping() {
        Map<Object, Long> result = menu.stream().map(t -> {
            HashMap map = new HashMap();
            map.put(t.getType(), 1);
            return map;
        }).collect(Collectors.groupingBy(t -> t.keySet().toArray()[0], Collectors.counting()));
//        Integer result = menu.stream().collect(Collectors.mapping((t) -> t, Collectors.summingInt(Dish::getCalories)));
//        menu.stream().collect(Collectors.mapping(Dish::getName, Collectors.joining(",")));
//        Integer result = menu.stream().collect(Collectors.mapping(Dish::getCalories, Collectors.summingInt(Integer::valueOf)));
        Optional.ofNullable(result).ifPresent(System.out::println);
        Map<Object, Long> method2 = menu.stream().map(t -> new Object[]{t.getType(), 1}).collect(Collectors.groupingBy(c -> c[0], Collectors.counting()));
        System.out.println(method2);
    }

    /**
     * mapping 根据第一个参数进行收集 相当于stream中的map 像T -> map成 R 第二个参数是对之前收集的数据的下游操作
     */
    public static void testMapping2() {
        Integer result = menu.stream().collect(Collectors.mapping(Dish::getCalories, Collectors.summingInt(Integer::valueOf)));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    /**
     * 收集最大.最小
     */
    public static void testMaxByAndMinBy() {
        menu.stream().collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories))).ifPresent(System.out::println);
        menu.stream().collect(Collectors.minBy(Comparator.comparing(Dish::getCalories))).ifPresent(System.out::println);
    }

    /**
     * 2分 parttiion 返回一个Map<Boolean,List<T>
     */
    public static void testPartitioningBy() {
        Map<Boolean, List<Dish>> result = menu.stream().collect(Collectors.partitioningBy(t -> t.getCalories() > 300));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    /**
     * 带下游操作的parttion
     */
    public static void testPartitioningByWithDownstream() {
        Map<Boolean, Integer> result = menu.stream().collect(Collectors.partitioningBy(t -> t.getCalories() > 300, Collectors.summingInt(Dish::getCalories)));
        Optional.ofNullable(result).ifPresent(System.out::println);
        Map<Boolean, Long> result2 = menu.stream().collect(Collectors.partitioningBy(t -> t.getCalories() > 300, Collectors.counting()));
        Optional.ofNullable(result2).ifPresent(System.out::println);
    }

    /**
     * 汇总操作 传入一个 BinaryOperator (preTotal,nextEle) 之前数据汇总和下一个元素
     */
    public static void testReduce() {
        Optional<Dish> max = menu.stream().collect(Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Dish::getCalories))));
        max.ifPresent(System.out::println);
        menu.stream().collect(Collectors.reducing(BinaryOperator.minBy(Comparator.comparing(Dish::getCalories)))).ifPresent(System.out::println);
        menu.stream().map(Dish::getCalories).collect(Collectors.reducing(Integer::sum)).ifPresent(System.out::println);
    }

    public static void testReduceWithIdentify() {
        // identify 类似于一个init值
        menu.stream().map(Dish::getCalories).collect(Collectors.reducing(0, Integer::sum));
    }

    public static void testReduceWithIndentifyAndBinaryOperator() {
        Integer result = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    /**
     * 汇总数据收集
     */
    public static void testSummarizing() {
        Optional.ofNullable(menu.stream().collect(Collectors.summarizingInt(t -> Integer.valueOf(t.getCalories())))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.summarizingDouble(t -> Double.valueOf(t.getCalories())))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.summarizingLong(t -> Long.valueOf(t.getCalories())))).ifPresent(System.out::println);
        LongSummaryStatistics result = menu.stream().collect(Collectors.summarizingLong(t -> Long.valueOf(t.getCalories())));
        result.getAverage();
    }

    public static void testSumming() {
        Optional.ofNullable(menu.stream().collect(Collectors.summingInt(Dish::getCalories))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.summingDouble(t -> Double.valueOf(t.getCalories())))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.summingLong(t -> Long.valueOf(t.getCalories())))).ifPresent(System.out::println);
    }

    /**
     * 转map
     * toMap(Function, Function)
     * toMap(Function, Function, BinaryOperator, Supplier)
     * toConcurrentMap(Function, Function, BinaryOperator)
     * keyMapper a mapping function to produce keys  如何得到Map的K
     * valueMapper a mapping function to produce values 如何得到Map的Value
     * mergeFunction a merge function, used to resolve collisions between
     *               values associated with the same key 如何聚合相同Key的Value 的BinaryOperator
     */
    public static void testToMap() {
//        Optional.ofNullable(menu.stream().collect(Collectors.toMap(Dish::getName, Function.identity()))).ifPresent(System.out::println);
        Map<Dish.Type, Integer> result = menu.stream().collect(Collectors.toMap(Dish::getType, Dish::getCalories, (pre, next) -> pre + next));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    public static void testToConcurrentMap() {
        ConcurrentMap<Dish.Type, Integer> result = menu.stream().collect(Collectors.toConcurrentMap(Dish::getType, Dish::getCalories, Integer::sum));
        Optional.ofNullable(result).ifPresent(System.out::println);
        Optional.ofNullable(result.getClass()).ifPresent(System.out::println);
    }

}
