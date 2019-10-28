package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class MapData implements Serializable{
    public int id;
    public Double latitude;
    public Double longtitude;
    public String address;
    public int totalnum;
    public int freenum;
    public Double price_per_hour;
  public MapData(){ }
    public MapData( int id, Double latitude, Double longtitude,String address, int totalnum,int freenum,Double price_per_hour) {
        this.id = id;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.address = address;
        this.freenum = freenum;
        this.price_per_hour = price_per_hour;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalnum() { return totalnum; }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

    public int getFreenum() { return freenum; }

    public void setFreenum(int freenum) {
        this.freenum = freenum;
    }

    public Double getPrice_per_hour() {
        return price_per_hour;
    }

    public void setPrice_per_hour(Double price_per_hour) {
        this.price_per_hour = price_per_hour;
    }
}
