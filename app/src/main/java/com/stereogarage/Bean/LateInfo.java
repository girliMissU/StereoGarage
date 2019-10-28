package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class LateInfo implements Serializable{
    public int status;
    public String money;
  public LateInfo(){ }
    public LateInfo(int status,String money) {
        this.status = status;
        this.money = money;
    }
    public int getstatus() {
        return status;
    }

    public void setstatus(int status) {
        this.status = status;
    }

    public String getmoney() {
        return money;
    }

    public void setmoney(String money) {
        this.money = money;
    }
}
