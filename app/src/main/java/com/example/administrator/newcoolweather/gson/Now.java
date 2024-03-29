package com.example.administrator.newcoolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2019/9/3 0003.
 */

public class Now {

    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt")
        public String info;//天气信息
    }

    public String fl;//体感温度

    public String hum;//相对湿度

    public String pcpn;//降水量

    public String pres;//大气压强

    @SerializedName("tmp")
    public String temperature;//温度

    public String vis;//能见度

  /*  public WIND wind;

    public class WIND{
    }*/
    @SerializedName("wind_dir")
     public String dir;//风向
    @SerializedName("wind_sc")
     public String sc;//风力
    @SerializedName("wind_spd")
     public String spd  ;//风速



/*    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;
    public class More{
        @SerializedName("txt")
        public String info;
    }*/
}
