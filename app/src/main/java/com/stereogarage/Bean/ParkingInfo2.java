package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class ParkingInfo2 implements Serializable{
    public int status;
    public String parking_id;
    public String address;
    public Double price_per_hour;
    public String start_time;
    public String leave_time;
    public String money;
  public ParkingInfo2(){ }
    public ParkingInfo2(int status,String parking_id, String address, Double price_per_hour, String start_time,String leave_time,String money) {

        this.status = status;
        this.parking_id = parking_id;
        this.address = address;
        this.price_per_hour = price_per_hour;
        this.start_time = start_time;
        this.leave_time = leave_time;
        this.money = money;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getParking_id() {
        return parking_id;
    }

    public void setParking_id(String parking_id) {
        this.parking_id = parking_id;
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

    public String getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(String leave_time) {
        this.leave_time = leave_time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
