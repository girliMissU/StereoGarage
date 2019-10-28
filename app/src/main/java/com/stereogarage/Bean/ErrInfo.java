package com.stereogarage.Bean;

import java.io.Serializable;

/**
 * Created by Administor on 2017/11/3.
 */

public class ErrInfo implements Serializable{
    public String id;
    public String ji_ting;
    public String guang_dian;
    public String re_ji_guo_zai;
    public String duan_dian;
    public String fang_song_lian;
    public String ji_xian;
    public String gua_gou;
    public String xiang_xu;
  public ErrInfo(){ }
    public ErrInfo(String id, String ji_ting, String guang_dian, String re_ji_guo_zai, String duan_dian, String fang_song_lian, String ji_xian, String gua_gou, String xiang_xu) {
        this.id=id;
        this.ji_ting=ji_ting;
        this.guang_dian=guang_dian;
        this.re_ji_guo_zai=re_ji_guo_zai;
        this.duan_dian=duan_dian;
        this.fang_song_lian=fang_song_lian;
        this.ji_xian=ji_xian;
        this.gua_gou=gua_gou;
        this.xiang_xu=xiang_xu;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJi_ting() {
        return ji_ting;
    }

    public void setJi_ting(String ji_ting) { this.ji_ting = ji_ting;}

    public String getGuang_dian() {
        return guang_dian;
    }

    public void setGuang_dian(String guang_dian) {
        this.guang_dian = guang_dian;
    }

    public String getRe_ji_guo_zai() {
        return re_ji_guo_zai;
    }

    public void setRe_ji_guo_zai(String re_ji_guo_zai) {
        this.re_ji_guo_zai = re_ji_guo_zai;
    }

    public String getDuan_dian() {
        return duan_dian;
    }

    public void setDuan_dian(String duan_dian) {
        this.duan_dian = duan_dian;
    }

    public String getFang_song_lian() {
        return fang_song_lian;
    }

    public void setFang_song_lian(String fang_song_lian) {
        this.fang_song_lian = fang_song_lian;
    }

    public String getJi_xian() {
        return ji_xian;
    }

    public void setJi_xian(String ji_xian) { this.ji_xian = ji_xian;}

    public String getGua_gou() {
        return gua_gou;
    }

    public void setGua_gou(String gua_gou) {
        this.gua_gou = gua_gou;
    }

    public String getXiang_xu() {
        return xiang_xu;
    }

    public void setXiang_xu(String xiang_xu) {
        this.xiang_xu = xiang_xu;
    }


}
