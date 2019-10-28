package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class UserInfo implements Serializable{
    public int id;
    public String userid;
    public String passwd;
    public String telephone;
  public UserInfo(){ }
    public UserInfo(int id,String userid,String passwd,String telephone ) {
        this.id = id;
        this.userid = userid;
        this.passwd = passwd;
        this.telephone = telephone;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getTelephone() { return telephone; }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
