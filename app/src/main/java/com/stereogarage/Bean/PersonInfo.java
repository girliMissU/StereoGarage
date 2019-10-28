package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class PersonInfo implements Serializable{
    public String user;
    public String num;
    public String mail;

  public PersonInfo(){ }
    public PersonInfo(String user, String num, String mail) {
        this.user=user;
        this.num=num;
        this.mail=mail;

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }



}
