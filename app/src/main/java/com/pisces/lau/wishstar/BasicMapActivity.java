package com.pisces.lau.wishstar;

import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.pisces.lau.wishstar.util.BaseActivity;


/**
 * Created by Liu Wenyue on 2015/7/11.
 * 新入快捷键:Ctrl+Alt+O优化导入的包
 */
public class BasicMapActivity extends BaseActivity {
    public MapView mMapView = null;
    public BaiduMap mBaiduMap = null;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    public MyLocationData locData;
    public BDLocation location;
    /*public Toolbar toolbar;*/
    public BitmapDescriptor mCurrentMarker;
    public MyLocationConfiguration.LocationMode mode;


    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* toolbar = (Toolbar)findViewById(R.id.tool_bar_BasicMapActivity);*/
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //定义Maker坐标点
        //LatLng point=new LatLng()
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);

        //注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option);
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //构造定位数据
        locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
// 设置定位数据
        mBaiduMap.setMyLocationData(locData);
        //设置定位图层的配置(定位模式,是否允许方向信息,用户自定义定位图标)
        //mCurrentMarker= BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        mode = MyLocationConfiguration.LocationMode.NORMAL;
        MyLocationConfiguration config;
        config = new MyLocationConfiguration(mode, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);
        //当不需要定位图层时候关闭图层
        mBaiduMap.setMyLocationEnabled(false);


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                return;
            }
            //StringBuffer是同步的,StringBuilder是异步的
            //StringBuffer sb = new StringBuffer(256);
            StringBuilder sb=new StringBuilder(256);
            sb.append("time:");
            sb.append(bdLocation.getTime());
            sb.append("\nerror code:");
            sb.append(bdLocation.getLocType());
            sb.append("\nlatitude : ");
            sb.append(bdLocation.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(bdLocation.getLongitude());
            sb.append("\nradius : ");
            sb.append(bdLocation.getRadius());
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(bdLocation.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(bdLocation.getSatelliteNumber());

            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr:");
                sb.append(bdLocation.getAddrStr());
            }
            Log.v(sb.toString(), "定位信息");


        }
    }

}
