package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class CancelInfo implements Serializable{
    public int status;
    public String username;
    public String money;
  public CancelInfo(){ }
    public CancelInfo(int status,String username, String money) {
        this.status = status;
        this.username = username;
        this.money = money;
    }
    public int getstatus() {
        return status;
    }

    public void setstatus(int status) {
        this.status = status;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getmoney() {
        return money;
    }

    public void setmoney(String money) {
        this.money = money;
    }
}
