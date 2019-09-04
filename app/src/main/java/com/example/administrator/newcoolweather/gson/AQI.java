package com.example.administrator.newcoolweather.gson;

/**
 * Created by Administrator on 2019/9/3 0003.
 */

public class AQI {

    public AQICITY city;

    public class AQICITY{

        public String aqi;//空气质量指数


        public String pm25;//PM2.5指数

        public String qlty;//空气质量（优/良/轻度污染/中度污染/重度污染/严重污染）


    }

 /*   public class AQICity{
        @SerializedName("aqi")
        String aqi;

        @SerializedName("pm25")
        String pm25;
    }*/
}
