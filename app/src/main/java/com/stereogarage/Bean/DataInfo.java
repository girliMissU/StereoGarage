package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class DataInfo implements Serializable{
    public String parking_id;
    public String username;
    public String car_num;
    public String garage_num;
    public String cp_num;
    public String finish_parking;
    public String confirm_parking;
    public String action_time;
    public String order_time;
    public String start_time;
    public String leave_time;
    public String money;
    public String pay_status;
    public String confirm_out;
  public DataInfo(){ }
    public DataInfo(String parking_id,String username,String car_num,String garage_num,String cp_num,String finish_parking,String confirm_parking,String action_time,String order_time,String start_time,String leave_time,String money,String pay_status,String confirm_out) {
        this.parking_id=parking_id;
        this.username=username;
        this.car_num=car_num;
        this.garage_num=garage_num;
        this.cp_num=cp_num;
        this.finish_parking=finish_parking;
        this.confirm_parking=confirm_parking;
        this.action_time=action_time;
        this.order_time=order_time;
        this.start_time=start_time;
        this.leave_time=leave_time;
        this.money=money;
        this.pay_status=pay_status;
        this.confirm_out=confirm_out;

    }

    public String getParking_id() {
        return parking_id;
    }

    public void setParking_id(String parking_id) {
        this.parking_id = parking_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCar_num() {
        return car_num;
    }

    public void setCar_num(String car_num) {
        this.car_num = car_num;
    }

    public String getGarage_num() {
        return garage_num;
    }

    public void setGarage_num(String garage_num) {
        this.garage_num = garage_num;
    }

    public String getCp_num() {
        return cp_num;
    }

    public void setCp_num(String cp_num) {
        this.cp_num = cp_num;
    }

    public String getFinish_parking() {
        return finish_parking;
    }

    public void setFinish_parking(String finish_parking) {
        this.finish_parking = finish_parking;
    }

    public String getConfirm_parking() {
        return confirm_parking;
    }

    public void setConfirm_parking(String confirm_parking) {
        this.confirm_parking = confirm_parking;
    }

    public String getAction_time() {
        return action_time;
    }

    public void setAction_time(String action_time) {
        this.action_time = action_time;
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

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getConfirm_out() {
        return confirm_out;
    }

    public void setConfirm_out(String confirm_out) {
        this.confirm_out = confirm_out;
    }

}
