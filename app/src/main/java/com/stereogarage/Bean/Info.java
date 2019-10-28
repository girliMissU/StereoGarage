package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class Info implements Serializable{
    public String car_type;
    public String car_num;
  public Info(){ }
    public Info(String type, String number) {
        this.car_type = car_type;
        this.car_num = car_num;
    }

    public String getType() {
        return car_type;
    }

    public void setType(String car_type) {
        this.car_type = car_type;
    }

    public String getNumber() { return car_num; }

    public void setNumber(String car_num) {
        this.car_num = car_num;
    }

}
