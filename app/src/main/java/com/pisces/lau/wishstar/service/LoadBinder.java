package com.pisces.lau.wishstar.service;

import android.content.Context;
import android.os.Binder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;

/**
 * Created by E440 on 2016/5/15.
 * Binder 执行具体的下载任务
 */
public class LoadBinder extends Binder {
    private static final String TAG = "LoadBinder";

    public void startDownload(Context context, String url) {
        //小而多的图片下载使用Volley下载

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        });
        requestQueue.add(request);

    }

    //查询服务端的信息
    public void queryData(final Context context){
        BmobQuery query = new BmobQuery("T_a_b");
        query.findObjects(context, new FindCallback() {

            @Override
            public void onSuccess(JSONArray arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                showToast("发布成功！", context);
                //解析此JSON数据
                Log.v(TAG, arg0.toString());

            }

            @Override
            public void onFailure(int arg0, String arg1) {
                showToast("发布失败！", context);
            }
        });
    }

    private void showToast(String msg,Context context){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
