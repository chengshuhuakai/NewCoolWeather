package com.example.administrator.newcoolweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Administrator on 2019/8/30 0030.
 */

public class Province extends LitePalSupport{

    int id ;
    String provinceName ;
    int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }







    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }



}
