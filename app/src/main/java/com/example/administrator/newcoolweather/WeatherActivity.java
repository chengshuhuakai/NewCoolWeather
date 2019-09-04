package com.example.administrator.newcoolweather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.newcoolweather.gson.Forecast;
import com.example.administrator.newcoolweather.gson.Weather;
import com.example.administrator.newcoolweather.util.HttpUtil;
import com.example.administrator.newcoolweather.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity" ;
    public DrawerLayout drawerLayout;

    private Button navButton;

    public SwipeRefreshLayout swipeRefresh;

    private String mWeatherId;

    private TextView aqiText;//空气质量--空气质量指数
    private TextView pm25Text;//空气质量--PM2.5指数
    private TextView qltyText;//空气质量--空气质量水平

    private TextView titleCity;//基本信息--城市名
    private TextView weatherInfoText;//实时天气信息--天气信息
    private TextView titleLat;//基本信息--经度
    private TextView titleLon;//基本信息--纬度
    private TextView titleUpdateTime;//基本信息--更新时间

    private ScrollView weatherLayout;//滚动视图对象

    private TextView flText;//实时天气信息--体感温度

    private TextView humText;//实时天气信息--相对湿度

    private TextView pcpnText;//实时天气信息--降水量

    private TextView presText;//实时天气信息--大气压强

    private TextView degreeText;//实时天气信息--温度

    private TextView visText;//实时天气信息--能见度

    private TextView dirText;//实时天气信息--风向

    private TextView scText;//实时天气信息--风力

    private TextView spdText;//实时天气信息--风速

    private LinearLayout forecastLayout;//线性布局对象--预报天气



/*    private TextView coText;//空气质量--一氧化碳指数

    private TextView no2Text;//空气质量--二氧化氮指数

    private TextView o3Text;//空气质量--臭氧指数

    private TextView pm10Text;//空气质量--PM10指数*/



/*    private TextView so2Text;//空气质量--二氧化硫指数

    private TextView airText;//生活建议--空气质量指数*/

    private TextView comfortText;//生活建议--舒适度指数

    private TextView carWashText;//生活建议--洗车指数

    private TextView sportText;//生活建议--运动指数

   /* private TextView drsgText;//生活建议--穿衣指数

    private TextView fluText;//生活建议--感冒指数

    private TextView travText;//生活建议--旅游指数

    private TextView uvText;//生活建议--紫外线指数*/

    private ImageView bingPicImg;//背景图片

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //版本控制(当系统版本大于等于21，也就是5.0以上系统时才会执行后面的代码)
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_weather);

        //初始化各控件
        bingPicImg = (ImageView)findViewById(R.id.bing_pic_img);//背景图片
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);//滚动视图对象

        titleCity = (TextView) findViewById(R.id.title_city);//基本信息--城市
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);//基本信息--更新时间
        titleLat = (TextView) findViewById(R.id.lat_text);//基本信息--经度
        titleLon = (TextView) findViewById(R.id.lon_text);//基本信息--纬度

        aqiText = (TextView) findViewById(R.id.aqi_text);//空气质量--空气质量指数
        pm25Text = (TextView) findViewById(R.id.pm25_text);//空气质量--PM2.5指数
       /* qltyText = (TextView) findViewById(R.id.qlty_text);//空气质量--空气质量水平*/

        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);//实时天气信息--天气信息
        flText = (TextView) findViewById(R.id.fl_text) ;//实时天气信息--体感温度
        humText = (TextView) findViewById(R.id.hum_text);//实时天气信息--相对湿度
        pcpnText = (TextView) findViewById(R.id.pcpn_text);//实时天气信息--降水量
        presText = (TextView) findViewById(R.id.pres_text); //实时天气信息--大气压强
        degreeText = (TextView) findViewById(R.id.degree_text);//实时天气信息--温度
        visText = (TextView) findViewById(R.id.vis_text);//实时天气信息--能见度
        dirText = (TextView) findViewById(R.id.dir_text);//实时天气信息--风向
        scText = (TextView) findViewById(R.id.sc_text);//实时天气信息--风力
        spdText = (TextView) findViewById(R.id.spd_text); //实时天气信息--风速

        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);//线性布局对象--预报天气


      /*  coText = (TextView) findViewById(R.id.co_text);//空气质量--一氧化碳指数
        no2Text = (TextView) findViewById(R.id.no2_text);//空气质量--二氧化氮指数
        o3Text = (TextView) findViewById(R.id.o3_text);//空气质量--臭氧指数
        pm10Text = (TextView) findViewById(R.id.pm10_text); //空气质量--PM10指数*/


        comfortText = (TextView) findViewById(R.id.comfort_text);//生活建议--舒适度指数
        carWashText = (TextView) findViewById(R.id.car_wash_text);//生活建议--洗车指数
        sportText = (TextView) findViewById(R.id.sport_text);//生活建议--运动指数

 /*       airText = (TextView) findViewById(R.id.air_text); //生活建议--空气质量指数
        drsgText = (TextView) findViewById(R.id.drsg_text);//生活建议--穿衣指数
        fluText = (TextView) findViewById(R.id.flu_text); //生活建议--感冒指数
        so2Text = (TextView) findViewById(R.id.so2_text); //空气质量--二氧化硫指数
        travText = (TextView) findViewById(R.id.trav_text);//生活建议--旅游指数
        uvText = (TextView) findViewById(R.id.uv_text);//生活建议--紫外线指数*/
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);//DrawerLayout实例
        navButton = (Button) findViewById(R.id.nav_button);//Button实例

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);//获取SwipeRefreshLayout的实例
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);//调用setColorSchemeResources()方法来设置下拉刷新进度条的颜色
       
        // 使用SharePreference  缓存数据 
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if(weatherString != null){
            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        }else{
            //无缓存时去服务器查询天气 ，通过Inent 获取需要的信息
            mWeatherId = getIntent().getStringExtra("weather_id");
            //在请求数据的时候将ScrollView()隐藏
            weatherLayout.setVisibility(View.INVISIBLE);
            //从服务器请求天气数据
            requestWeather(mWeatherId);
        }

        //设置下拉刷新监听器
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);//请求天气信息
            }
        });
        String bingPic = prefs.getString("bing_pic",null);
        if(bingPic != null){
            //如果有缓存数据就直接使用Glide来加载这张图片
            Glide.with(this).load(bingPic).into(bingPicImg);
        }else{
            //如果没有缓存数据就调用loadBingPic()方法去请求今日的必应背景图
            loadBingPic();//加载每日一图
        }
        //请求新选择城市的天气信息
        navButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        //调用HttpUtil.sendOkHttpRequest()方法获取必应背景图的链接
        HttpUtil.sendHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                //将链接缓存到SharedPreferences当中
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                //将线程切换到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //使用Glide来加载图片
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }

    private void requestWeather(String weatherId) {
        //组装接口地址 ,这里key 还没修改，要改成自己的。
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=50495675180144f098496983601b4f80";
        Log.d(TAG, "---------requestWeather开始请求天气信息 ----------"+"weatherUrl ="+weatherUrl);
        HttpUtil.sendHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);//刷新事件结束，将进度条隐藏起来
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseText = response.body().string();
                Log.d(TAG, "---------------responseText---------------- :"+ responseText);
                //将返回的JSON数据转换成Weather对象
                final Weather weather = Utility.handleWeatherResponse(responseText);
                //将当前线程切换到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather != null && "ok".equals(weather.status)){//请求天气成功
                            //将返回的数据缓存到SharedPreferences当中
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            //调用showWeatherInfo()方法进行内容显示
                            showWeatherInfo(weather);
                        }else{
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);//刷新事件结束，将进度条隐藏起来
                    }
                });
                //每次请求天气信息的时候也会刷新背景图片
                loadBingPic();
            }
        });
    }

    /**
     * 显示Weather 信息。
     */

    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = "更新时间:"+weather.basic.update.updateTime.split(" ")[1];
        String lat = "经度:"+weather.basic.cityLat;
        String lon = "纬度:"+weather.basic.cityLon;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        titleLat.setText(lat);
        titleLon.setText(lon);
        //天气信息
        String weatherInfo = weather.now.more.info;
        String flInfo = weather.now.fl + "℃";
        String humInfo = weather.now.hum;
        String pcpnInfo = weather.now.pcpn + "mm";
        String presInfo = weather.now.pres + "Pa";
        String degree = weather.now.temperature + "℃";
        String visInfo = weather.now.vis;
        String dirInfo = weather.now.dir;
        String scInfo = weather.now.sc;
        String spdInfo = weather.now.spd + "m/s";
        weatherInfoText.setText(weatherInfo);
        flText.setText(flInfo);
        humText.setText(humInfo);
        pcpnText.setText(pcpnInfo);
        presText.setText(presInfo);
        degreeText.setText(degree);
        visText.setText(visInfo);
        dirText.setText(dirInfo);
        scText.setText(scInfo);
        spdText.setText(spdInfo);

        forecastLayout.removeAllViews();
        for(Forecast forecast : weather.forecastList){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dateText = (TextView)view.findViewById(R.id.date_text);
            TextView infoText = (TextView)view.findViewById(R.id.info_text);
            TextView maxText = (TextView)view.findViewById(R.id.max_text);
            TextView minText = (TextView)view.findViewById(R.id.min_text);
      /*      TextView dirText = (TextView)view.findViewById(R.id.dir_text);
            TextView scText = (TextView)view.findViewById(R.id.sc_text);*/
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }

        if(weather.aqi != null){
            aqiText.setText(weather.aqi.city.aqi);
        /*    coText.setText(weather.aqi.city.co);
            no2Text.setText(weather.aqi.city.no2);
            o3Text.setText(weather.aqi.city.o3);
            pm10Text.setText(weather.aqi.city.pm10);*/
            pm25Text.setText(weather.aqi.city.pm25);
            /*qltyText.setText(weather.aqi.city.qlty);*/
         /*   so2Text.setText(weather.aqi.city.so2);*/
        }

      /*  String air = "空气质量:"+weather.suggestion.air.info;*/
        String comfort = "舒适度:"+weather.suggestion.comfort.info;
        String carWash = "洗车指数:"+weather.suggestion.carWash.info;
/*        String drsg = "穿衣指数:"+weather.suggestion.drsg.info;
        String flu = "感冒指数:"+weather.suggestion.flu.info;*/
        String sport = "运动建议:"+weather.suggestion.sport.info;
 /*       String trav = "旅游指数:"+weather.suggestion.trav.info;
        String uv = "紫外线指数:"+weather.suggestion.uv.info;*/
       /* airText.setText(air);*/
        comfortText.setText(comfort);
        carWashText.setText(carWash);
    /*    drsgText.setText(drsg);
        fluText.setText(flu);
        sportText.setText(sport);
        travText.setText(trav);
        uvText.setText(uv);*/
        //在设置完所有数据后，再将ScrollView设为可见
        weatherLayout.setVisibility(View.VISIBLE);
        //激活AutoUpdateService这个服务，只要选中了某个城市并成功更新天气之后，
        // AutoUpdateService就会一直在后台运行，并保证每8个小时更新一次天气
       // Intent intent = new Intent(this, AutoUpdateService.class);
       // startService(intent);
      }

  }



