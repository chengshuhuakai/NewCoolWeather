package com.example.administrator.newcoolweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Administrator on 2019/8/30 0030.
 */

public class City extends LitePalSupport {


    int id;
    int cityCode;
    String cityName;
    int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

}
