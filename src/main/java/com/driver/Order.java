package com.driver;

import lombok.ToString;

@ToString
public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM

        this.id = id;
        // 12:34
        // 1 12 : 13 - 24
        int i = 0;
        while( i < deliveryTime.length()){
            if(deliveryTime.charAt(i) == ':') break;
            i++;
        }

        int hour = Integer.parseInt(deliveryTime.substring(0,i));
        int min = Integer.parseInt(deliveryTime.substring(i + 1));

        this.deliveryTime = hour * 60 + min;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
