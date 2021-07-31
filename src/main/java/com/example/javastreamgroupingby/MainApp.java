package com.example.javastreamgroupingby;

import static java.util.stream.Collectors.*;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MainApp {

    public static void main(String[] args) {
        List<Order> orders = dummy();

        // 단일 컬럼
        Map<OrderType, List<Order>> collect = orders.stream().collect(groupingBy((Order::getOrderType)));
        // 복합 키 컬럼
        Map<OrderTuple, List<Order>> collect1 = orders.stream().collect(groupingBy(order -> new OrderTuple(order.getItemName(), order.getOrderType())));

        // 집계 타입 변경
        Map<OrderType, Set<Order>> collect2 = orders.stream().collect(groupingBy(Order::getOrderType, toSet()));
        // 중첩 집계 (multiple feilds)
        Map<String, Map<OrderType, List<Order>>> collect3 = orders.stream().collect(groupingBy(Order::getOrderBy, groupingBy(Order::getOrderType)));

        // 통계 데이터
        Map<OrderType, Integer> collect4 = orders.stream().collect(groupingBy(Order::getOrderType, summingInt(Order::getAmount)));
        Map<OrderType, Double> collect5 = orders.stream().collect(groupingBy(Order::getOrderType, averagingDouble(Order::getAmount)));
        Map<OrderType, Optional<Order>> collect6 = orders.stream().collect(groupingBy(Order::getOrderType, maxBy(Comparator.comparingInt(Order::getAmount))));
        Map<OrderType, IntSummaryStatistics> collect7 = orders.stream().collect(groupingBy(Order::getOrderType, summarizingInt(Order::getAmount)));
        Map<OrderType, String> collect8 = orders.stream().collect(groupingBy(Order::getOrderType, mapping(Order::getItemName, joining(",", "[", "]"))));
        System.out.println("collect8 = " + collect8);

        EnumMap<OrderType, List<Order>> collect9 = orders.stream().collect(groupingBy(Order::getOrderType, () -> new EnumMap<>(OrderType.class), toList()));


    }

    private static List<Order> dummy() {
        return List.of(
                new Order("후라이드 치킨", 17_000, OrderType.DELIVERY, "Andrew"),
                new Order("양념 치킨2", 18_000, OrderType.DELIVERY, "Andrew"),
                new Order("양념 치킨1", 18_000, OrderType.DELIVERY, "Andrew"),
                new Order("피자", 18_000, OrderType.PICKUP, "Andrew"),
                new Order("돈가스", 10_000, OrderType.PICKUP, "Andrew"),
                new Order("모둠초밥", 13_000, OrderType.PRESENT, "Andrew")
        );
    }
}
