    package com.example.administrator.newcoolweather;


    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.Bundle;
    import android.support.v4.app.Fragment;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.ListView;
    import android.widget.TextView;

    import com.example.administrator.newcoolweather.db.City;
    import com.example.administrator.newcoolweather.db.County;
    import com.example.administrator.newcoolweather.db.Province;
    import com.example.administrator.newcoolweather.util.HttpUtil;
    import com.example.administrator.newcoolweather.util.Utility;

    import org.jetbrains.annotations.NotNull;
    import org.litepal.LitePal;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;

    import okhttp3.Call;
    import okhttp3.Callback;
    import okhttp3.Response;

    import static android.content.ContentValues.TAG;


    /**
 * A simple {@link Fragment} subclass.
 */
public class ChooseAreaFragment extends Fragment {
    /**
     * 设置三个静态常量
     * */
    public static  final int LEVEL_PROVINCE = 0;
    public static  final int LEVEL_CITY = 1;
    public static  final int LEVEL_COUNTRY = 2;


    private TextView mTitleText;
    private Button mBackButton;
    private ListView mListView;

    private ProgressDialog progressDialog;

    private List<String>  dataList  = new ArrayList<>();
    /**
     *省列表
     */
    private List<Province>  provinceList;

    /**
     *市列表
     */
    private List<City> cityList;

    /**
     * 县列表
     */
    private  List<County> countyList;

    /**
     *当前选中的省份
     */
    private Province seletedProvince;

    /**
     *当前选中的市
     */
    private City seletedCity;

    /**
     *  当前选中的级别
     */
    private  int currentLevel;
    private ArrayAdapter<String> mAdapter;



    /**
     * 在onCreatView()获取到一些控件的实例，然后初始化了ArrayAdapter ，并且将它设置成为ListView的
     * 适配器
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.choose_area, container, false);
        initView(view);
        mTitleText = (TextView) view.findViewById(R.id.title_text);
        mBackButton = (Button) view.findViewById(R.id.back_button);
        mListView = (ListView) view.findViewById(R.id.list_view);

        //listView 初始化Adapter，并且设置。
        mAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        mListView.setAdapter(mAdapter);

        return  view ;
    }

    private void initView(View view) {


    }

    /**
     * 在onActivityCreated()方法中给ListView 和Button设置了点击事件。
     * 在onActivityCreated()方法最后，调用queryProvinces()方法，也就是从这里开始加载省级数据。
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel == LEVEL_PROVINCE){
                    //获取选取的省份
                    seletedProvince = provinceList.get(position);
                    Log.d(TAG, "onItemClick " + seletedProvince.getProvinceName() );
                    queryCities();
                }else if(currentLevel == LEVEL_CITY){
                    seletedCity = cityList.get(position);
                    queryCountries();
                }else if(currentLevel == LEVEL_COUNTRY){
                    //如果当前是 LEVEL_COUNTY ,则启动weatherActivity，并且把weatherId传过去
                    String weatherId  = countyList.get(position).getWeatherId();
                    Intent intent = new Intent (getActivity(),WeatherActivity.class);
                    intent.putExtra("weather_id",weatherId);
                    startActivity(intent);
                    //停止当前MainActivity
                    getActivity().finish();
                }
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel == LEVEL_COUNTRY){
                    queryCities();
                }else if(currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();

    }

    /**
     * 查询全国所有的县城，优先从数据库开始查询，没有从服务器查询
     */
    private void queryCountries() {
        //UI处理
        mTitleText.setText(seletedCity.getCityName());
        Log.d(TAG, "queryCity "+ seletedCity.getCityName());
        mBackButton.setVisibility(View.VISIBLE);
        //SQL 获取数据
        //cityList = LitePal.findAll(City.class);
        //  int provinceId = seletedProvince.getProvinceId();
        countyList =  LitePal.where("cityid = ?" ,String.valueOf(seletedCity.getId())).find(County.class);
        Log.d(TAG, "queryCountries -countyList" +countyList);
        if(countyList.size()>0){
            dataList.clear();
            for (County county  :countyList ) {
                dataList.add(county.getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            //?
            currentLevel = LEVEL_COUNTRY ;
        }else{
          int provinceCode= seletedProvince.getProvinceCode();
            int cityCode =seletedCity.getCityCode();
            String address = "http://guolin.tech/api/china/"+ provinceCode + "/" + cityCode;
            queryFromServer(address,"county");
        }

    }

    private void queryCities() {
        //UI处理
        mTitleText.setText(seletedProvince.getProvinceName());
        mBackButton.setVisibility(View.VISIBLE);
        //SQL 获取数据  order按什么顺序。
        cityList = LitePal.where("provinceid = ?", String.valueOf(seletedProvince.getId())).find(City.class);

        Log.d(TAG, "cityList=" + cityList);
        if(cityList.size()>0){
            dataList.clear();
            for (City city:cityList) {
                dataList.add(city.getCityName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            //?
            currentLevel = LEVEL_CITY;
        }else {
            //先走这里
            int provinceCode = seletedProvince.getProvinceCode();
            Log.d(TAG, "queryCities .getProvinceCode ="+ provinceCode);
            //int cityId =seletedCity.getCityId();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address,"city");
        }
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询再到服务器查询
     */
    private  void queryProvinces(){
        Log.d(TAG, "onActivityCreated "+  "------------------------queryProvinces()方法执行了------------------");
        mTitleText.setText("中国");
        mBackButton.setVisibility(View.GONE);

        provinceList = LitePal.findAll(Province.class);

        Log.d(TAG, "queryProvinces :"+ provinceList +"provinceList.size() =" + provinceList.size());

        if(provinceList.size()>0 ){
            dataList.clear();
            for (Province province:provinceList) {
                // dataList 将省级数据进行添加
                dataList.add(province.getProvinceName());
            }
            Log.d(TAG, "datalist :"+ dataList +"dataList.size() =" + dataList.size());

            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        }else{
            String address ="http://guolin.tech/api/china";
            //这里可以用到多态
            queryFromServer(address ,"province");
        }
    }

    /**
     *  根据传入的地址和类型从服务器上查询省市县数据
     * @param address
     * @param type
     */
    private void queryFromServer(String address, final String type) {
        //UI处理
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                // 方法
                String responseText = response.body().string();
               // Log.d(TAG, "onResponse " +responseText);
                boolean result = false;

                // 判断申请的数据
                if("province".equals(type)){
                    result = Utility.handleProvinceResponse(responseText);
                }else if("city".equals(type)){

                    result = Utility.handlerCityResponse(responseText,seletedProvince.getProvinceCode());
                    Log.d(TAG, "选择了城市:" +address.toString() + "result ="+ result);
                }else if("county".equals(type)){
                    result = Utility.handlerCountyResponse(responseText,seletedCity.getId());
                    Log.d(TAG, "选择了县市:" +address.toString() + "result ="+ result);
                }
                // 如果返回数据，存储到数据库。
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type)){
                                Log.d(TAG, "province.equals(type) :" + "province".equals(type) );
                                queryProvinces();
                            }else if("city".equals(type)){
                                queryCities();
                            }else if("county".equals(type)){
                                Log.d(TAG, "county.equals(type) :" + "county".equals(type) );
                                queryCountries();
                            }
                        }
                    });

                }
            }
        });
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    /**
     *  显示进度对话框
     */
    private void showProgressDialog() {
        if(progressDialog == null){
            //?
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.dismiss();
    }
}
