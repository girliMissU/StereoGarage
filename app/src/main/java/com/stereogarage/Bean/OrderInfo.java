package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class OrderInfo implements Serializable{
    public String order_id;
    public String address;
    public Double price_per_hour;
    public String start_time;
  public OrderInfo(){ }
    public OrderInfo(String order_id,String address,Double price_per_hour,String start_time) {

        this.order_id = order_id;
        this.address = address;
        this.price_per_hour = price_per_hour;
        this.start_time = start_time;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice_per_hour() {
        return price_per_hour;
    }

    public void setPrice_per_hour(Double price_per_hour) {
        this.price_per_hour = price_per_hour;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
