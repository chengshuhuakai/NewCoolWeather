package com.example.administrator.newcoolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2019/9/3 0003.
 */

public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;//生活建议--舒适度指数

    @SerializedName("cw")
    public CarWash carWash;//生活建议--洗车指数

    @SerializedName("sport")
    public Sport sport;//生活建议--运动指数

    public class Comfort {
        @SerializedName("txt")
        public String info;
    }

    public class CarWash {

        @SerializedName("txt")
        public String info;
    }

    public class Sport {

        @SerializedName("txt")
        public String info;
    }


}