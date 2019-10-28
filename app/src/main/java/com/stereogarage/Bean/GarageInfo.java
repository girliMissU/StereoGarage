package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class GarageInfo implements Serializable{
    public String id;
    public String total_number;
    public String rest_number;
  public GarageInfo(){ }
    public GarageInfo(String id, String total_number, String rest_number) {

        this.id = id;
        this.total_number = total_number;
        this.rest_number = rest_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal_number() {
        return total_number;
    }

    public void setTotal_number(String total_number) {
        this.total_number = total_number;
    }

    public String getRest_number() {
        return rest_number;
    }

    public void setRest_number(String rest_number) {
        this.rest_number = rest_number;
    }
}
