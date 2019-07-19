package com.qm.qmlife.business.weather;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.orhanobut.logger.Logger;
import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.business.adapter.WeatherAdapter;
import com.qm.qmlife.business.model.air.WeatherAir;
import com.qm.qmlife.business.model.forecast.Daily_forecast;
import com.qm.qmlife.business.model.forecast.WeatherForecast;
import com.qm.qmlife.business.model.weather.Weather;
import com.qm.qmlife.util.DateUtil;
import com.qm.qmlife.util.OkhttpUtil;
import com.qm.qmlife.util.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherActivity extends BaseActivity {

    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_tmp) TextView tvTmp;
    @BindView(R.id.tv_cond_txt) TextView tvCondTxt;
    @BindView(R.id.tv_district) TextView tvDistrict;
    @BindView(R.id.tv_max_and_min) TextView tvMaxAndMin;
    @BindView(R.id.tv_update_time) TextView tvUpdateTime;
    @BindView(R.id.tv_qlty) TextView tvQlty;
    @BindView(R.id.rv_weather_forecast)RecyclerView rvWeatherForecast;
    @BindView(R.id.srl_pull_refresh)SwipeRefreshLayout srlPullToRefresh;
    @BindView(R.id.ll_weather_back)LinearLayout llWeatherBack;

    //申明对象
    CityPickerView mPicker=new CityPickerView();
    List<Daily_forecast> daily_forecasts=new ArrayList<>();

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private WeatherAdapter weatherAdapter;

    private String province,city,district;
    private ObjectAnimator animatorStart;
    private ObjectAnimator animatorEnd;

    private static final String NOTIFICATION_CHANNEL_NAME = "BackgroundLocation";
    private NotificationManager notificationManager = null;
    boolean isCreateChannel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        tvTitle.setText("天气");
        initView();
        initAMap();
    }

    private void initView() {
        mPicker.init(this);
        CityConfig cityConfig = new CityConfig.Builder()
                .confirTextColor("#0098cb")//确认按钮文字颜色
                .cancelTextColor("#0098cb")//取消按钮文字颜色
                .setLineColor("0098cb")//中间横线的颜色
                .setLineHeigh(1)//中间横线的高度
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(5)//显示item的数量
                .provinceCyclic(true)//省份滚轮是否可以循环滚动
                .cityCyclic(true)//城市滚轮是否可以循环滚动
                .districtCyclic(true)//区县滚轮是否循环滚动
                .build();
        mPicker.setConfig(cityConfig);
        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                tvDistrict.setText(district.getName());
                getNow(district.getName());
                getForecast(district.getName());
                if ("省直辖县级行政单位".equals(city.getName())){
                    getAir(province.getName());
                }else {
                    getAir(city.getName());
                }

                //省份province
                //城市city
                //地区district
            }

            @Override
            public void onCancel() {
            }
        });




        srlPullToRefresh.setColorSchemeResources(R.color.tab_selected_color);
        srlPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public synchronized void onRefresh() {
                refreshWeatherData();
                //具体操作
            }
        });

        weatherAdapter = new WeatherAdapter(R.layout.weather_recycler_item,daily_forecasts);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        rvWeatherForecast.setLayoutManager(linearLayoutManager);
        rvWeatherForecast.setAdapter(weatherAdapter);
        weatherAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        weatherAdapter.isFirstOnly(false);
    }

    private void refreshWeatherData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //启动定位
                mLocationClient.startLocation();
                srlPullToRefresh.setRefreshing(false);

            }
        }, 1500);
    }

    private void initAMap() {
        //声明定位回调监听器
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。


                        province=aMapLocation.getProvince();
                        city=aMapLocation.getCity();
                        district=aMapLocation.getDistrict();
                        tvDistrict.setText(district);

                        getNow(district);
                        getForecast(district);
                        getAir(city);

                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
//                        Logger.e("AmapError","location Error, ErrCode:"
//                                + aMapLocation.getErrorCode() + ", errInfo:"
//                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocationLatest(true);

        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.enableBackgroundLocation(2001, buildNotification());
        //启动定位
        mLocationClient.startLocation();
    }

    private void getAir(String city) {
        OkhttpUtil.getInstance().doGet(WeatherActivity.this, "https://free-api.heweather.net/s6/air/now?key=e1984444a03d4a52a1f6cc545cce9245&location="
                +city, new OkhttpUtil.OkCallback() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onResponse(String json) {
                try{
                    //转module对象，因为其中有时间所以添加setDateFormat("yyyy-MM-dd HH:mm")方法
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
                    WeatherAir weatherAir = gson.fromJson(json, WeatherAir.class);
                    tvQlty.setText(weatherAir.getHeWeather6().get(0).getAir_now_city().getQlty());
                }catch (Exception e){
                }


            }
        });
    }

    private void getForecast(String district) {
        OkhttpUtil.getInstance().doGet(WeatherActivity.this, "https://free-api.heweather.net/s6/weather/forecast?key=e1984444a03d4a52a1f6cc545cce9245&location="
                +district, new OkhttpUtil.OkCallback() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onResponse(String json) {
                try{

                //转module对象，因为其中有时间所以添加setDateFormat("yyyy-MM-dd HH:mm")方法
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
                WeatherForecast weatherForecast = gson.fromJson(json, WeatherForecast.class);

                tvMaxAndMin.setText(weatherForecast.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_max()+"℃/"+
                        weatherForecast.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_min()+"℃");

                daily_forecasts.clear();
                List<Daily_forecast> dailyForecasts=new ArrayList<>();
                dailyForecasts.addAll(weatherForecast.getHeWeather6().get(0).getDaily_forecast());
                dailyForecasts.remove(0);
                daily_forecasts.addAll(dailyForecasts);
                weatherAdapter.notifyDataSetChanged();
                }catch (Exception e){
                }
            }
        });
    }

    private void getNow(String district) {
        OkhttpUtil.getInstance().doGet(WeatherActivity.this, "https://free-api.heweather.net/s6/weather/now?key=e1984444a03d4a52a1f6cc545cce9245&location="
                +district, new OkhttpUtil.OkCallback() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onResponse(String json) {
                try{

                //转module对象，因为其中有时间所以添加setDateFormat("yyyy-MM-dd HH:mm")方法
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
                Weather weather = gson.fromJson(json, Weather.class);
                tvTmp.setText(weather.getHeWeather6().get(0).getNow().getTmp());
                if ("多云".equals(weather.getHeWeather6().get(0).getNow().getCond_txt())||"阴".equals(weather.getHeWeather6().get(0).getNow().getCond_txt())){
                    playAnimator(R.drawable.cloudy);

                }else if ("晴".equals(weather.getHeWeather6().get(0).getNow().getCond_txt())){
                    playAnimator(R.drawable.fine);

                }else {
                    playAnimator(R.drawable.rainy);
                }
                tvCondTxt.setText(weather.getHeWeather6().get(0).getNow().getCond_txt());
                }catch (Exception e){
                }
                tvUpdateTime.setText("上次更新时间："+String.valueOf(DateUtil.getHour(new Date()))+":"+String.valueOf(DateUtil.getMinute(new Date())));
            }
        });
    }

    private void playAnimator(int fine) {
        //这是动画
        animatorStart = ObjectAnimator.ofFloat( llWeatherBack,"alpha",0);
        //动画时间
        animatorStart.setDuration(0);
        //启动动画
        animatorStart.start();
        llWeatherBack.setBackgroundResource(fine);
        //这是动画
        animatorEnd = ObjectAnimator.ofFloat( llWeatherBack,"alpha",0.5f);
        //动画时间
        animatorEnd.setDuration(2000);
        //启动动画
        animatorEnd.start();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.disableBackgroundLocation(true);
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        animatorStart.cancel();
        animatorEnd.cancel();
        super.onDestroy();
    }

    @OnClick({R.id.tv_district,R.id.iv_district})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_district:
                //显示
                mPicker.showCityPicker();
                break;
            case R.id.iv_district:
                //显示
                mPicker.showCityPicker();
                break;
        }
    }

    @SuppressLint("NewApi")
    private Notification buildNotification() {

        Notification.Builder builder = null;
        Notification notification = null;
        if(android.os.Build.VERSION.SDK_INT >= 26) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            if (null == notificationManager) {
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }
            String channelId = getPackageName();
            if(!isCreateChannel) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId,
                        NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.enableLights(true);//是否在桌面icon右上角展示小圆点
                notificationChannel.setLightColor(Color.BLUE); //小圆点颜色
                notificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                notificationManager.createNotificationChannel(notificationChannel);
                isCreateChannel = true;
            }
            builder = new Notification.Builder(getApplicationContext(), channelId);
        } else {
            builder = new Notification.Builder(getApplicationContext());
        }
        builder.setSmallIcon(R.drawable.qm_life)
                .setContentTitle(getTitle())
                .setContentText("正在后台运行")
                .setWhen(System.currentTimeMillis());

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            return builder.getNotification();
        }
        return notification;
    }
}
