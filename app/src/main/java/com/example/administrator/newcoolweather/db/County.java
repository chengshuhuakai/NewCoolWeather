package com.example.administrator.newcoolweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Administrator on 2019/8/30 0030.
 */

public class County extends LitePalSupport {


    int id;
    String CountyName;
    private  String weatherId ;
    private  int cityId ;
    public String getCountyName() {
        return CountyName;
    }

    public void setCountyName(String countyName) {
        CountyName = countyName;
    }


    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }








    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }






}
