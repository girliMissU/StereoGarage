package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class LoginInfo implements Serializable{
    public int status;
  public LoginInfo(){ }
    public LoginInfo(int status) {
        this.status = status;
    }
    public int getstatus() {
        return status;
    }

    public void setstatus(int status) {
        this.status = status;
    }
}
