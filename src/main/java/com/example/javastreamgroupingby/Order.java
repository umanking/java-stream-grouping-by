package com.example.javastreamgroupingby;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Order {

    private String itemName;
    private Integer amount;
    private OrderType orderType;
    private String orderBy;

}
