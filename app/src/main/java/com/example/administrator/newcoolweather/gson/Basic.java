package com.example.administrator.newcoolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2019/9/3 0003.
 */

public class Basic {

    @SerializedName("city")
    public String cityName;//城市名

    @SerializedName("id")
    public String weatherId;//城市对应的天气的id

    @SerializedName("lat")
    public String cityLat;//城市的经度

    @SerializedName("lon")
    public String cityLon;//城市的纬度

    public Update update;

    public class Update{

        @SerializedName("loc")
        public String updateTime;//接口更新时间

    }

    /*    //"city"与cityName建立映射关系
    @SerializedName("city")
    public String cityName;


    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{

        @SerializedName("loc")
        public String updateTime;
    }*/

}
