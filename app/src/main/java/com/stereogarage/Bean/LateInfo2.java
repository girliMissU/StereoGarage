package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class LateInfo2 implements Serializable{
    public String id;
    public String car_num;
    public String address;
    public String action_time;
    public String cancel_time;
    public String order_time;
    public String start_time;
    public String money;

  public LateInfo2(){ }
    public LateInfo2(String id, String car_num, String address, String action_time,String cancel_time,String order_time,String start_time,String money) {

        this.id = id;
        this.car_num = car_num;
        this.address = address;
        this.action_time = action_time;
        this.cancel_time = cancel_time;
        this.order_time = order_time;
        this.start_time = start_time;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCar_num() {
        return car_num;
    }

    public void setCar_num(String car_num) {
        this.car_num = car_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAction_time() {
        return action_time;
    }

    public void setAction_time(String action_time) {
        this.action_time = action_time;
    }

    public String getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(String cancel_time) {
        this.cancel_time = cancel_time;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }


    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

}
