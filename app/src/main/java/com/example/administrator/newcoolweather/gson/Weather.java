package com.example.administrator.newcoolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2019/9/3 0003.
 */

public class Weather {

    /**
     * Weather类作为总的实例类来引用以上各个实体类
     */

    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;
    //由于daily_forecase中包含的是一个数组，
    //这里使用List集合来引用Forecast类
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
