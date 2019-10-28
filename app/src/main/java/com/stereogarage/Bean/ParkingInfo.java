package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class ParkingInfo implements Serializable{
    public int status;
    public String parking_id;
    public String address;
    public Double price_per_hour;
    public int finish_parking;
    public String start_time;
    public String leave_time;
    public String money;
    public int pay_status;
    public int confirm_out;
  public ParkingInfo(){ }
    public ParkingInfo(int status,String parking_id, String address, Double price_per_hour,int finish_parking, String start_time,String leave_time,String money,int pay_status,int confirm_out) {

        this.status = status;
        this.parking_id = parking_id;
        this.address = address;
        this.price_per_hour = price_per_hour;
        this.finish_parking = finish_parking;
        this.start_time = start_time;
        this.leave_time = leave_time;
        this.money = money;
        this.pay_status = pay_status;
        this.confirm_out = confirm_out;
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

    public int getFinish_parking() {
        return finish_parking;
    }

    public void setFinish_parking(int finish_parking) {
        this.finish_parking = finish_parking;
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

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public int getConfirm_out() {
        return confirm_out;
    }

    public void setConfirm_out(int confirm_out) {
        this.confirm_out = confirm_out;
    }
}
