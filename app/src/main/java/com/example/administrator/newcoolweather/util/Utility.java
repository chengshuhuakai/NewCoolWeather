package com.example.administrator.newcoolweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.newcoolweather.db.City;
import com.example.administrator.newcoolweather.db.County;
import com.example.administrator.newcoolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2019/8/30 0030.
 */

public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     * */
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)) {
            Log.d(TAG, "TextUtils.isEmpty(response): "+ TextUtils.isEmpty(response));
            try {
                //
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                   // Log.d(TAG, "allProvinces.length()" + allProvinces.length());
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));

                    province.setProvinceCode(provinceObject.getInt("id"));



                    //调用save()方法将数据存储到数据库中
                    province.assignBaseObjId(0);
                    province.save();
                    if(! province.save()){
                        Log.d(TAG, "handleProvinceResponse -----------------------------" + "province数据并没有进行 save()");
                    }
                   // Log.d(TAG, "provinceId "+ province.getProvinceId());
                    // Log.d(TAG, "provinceName "+province.getProvinceName());
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  false;
    }
    /**
     * 解析和处理服务器返回的市级数据
     * */
    public static boolean handlerCityResponse(String response, int provinceId){
        try {
            JSONArray allCitys = new JSONArray(response);
            for (int i = 0; i <allCitys.length() ; i++) {
                JSONObject cityObject =  allCitys.getJSONObject(i);
                City city = new City();
                //
                city.setCityName(cityObject.getString("name"));
                city.setCityCode(cityObject.getInt("id"));
                city.setProvinceId(provinceId);
                //调用save()方法将数据存储到数据库中
                city.save();
              //  Log.d(TAG, "city.getCityName :---------------" + city.getCityName());
              //  Log.d(TAG, "city.getCityCode :---------------" + city.getCityCode());
               // Log.d(TAG, "city.getProvinceId :-------------- " + city.getProvinceId());
            }
            return  true ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  false;
    }

    public static  boolean handlerCountyResponse(String response, int cityId){
        try {
            JSONArray allCountys = new JSONArray(response);
            for (int i = 0; i <allCountys.length() ; i++) {
                JSONObject countyObject = allCountys.getJSONObject(i);
                County county = new County();
                county.setCountyName(countyObject.getString("name"));
                county.setWeatherId(countyObject.getString("weather_id"));
                county.setCityId(cityId);
                county.save();
            }
            return  true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  false;
    }
}
