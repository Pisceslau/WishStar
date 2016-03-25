package com.pisces.lau.wishstar.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Liu Wenyue on 2015/10/16.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class HttpUtils {

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    private static final int CALLBACK_SUCCESSFUL = 0x01;
    private static final int CALLBACK_FAILED = 0x02;
    // private Handler handler = new UIHandler<>(Callback c=);

    //判断是否有网络连接
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    //判断是否WiFi连接
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return networkInfo.isAvailable();
            }
        }
        return false;

    }

    //判断是否3G连接
    public static boolean is3GConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return networkInfo.isAvailable();

            }
        }
        return false;
    }

    /*
    OkHttp Http GET
    * */
/*    public String getString(String url) throws IOException {

        StringParser parser = new StringParser();
        Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        return call.enqueue(new StringCallback<String>(parser) {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {

            }
        });


    }*/

    //解析的方法;该接口传入okhttp给我们返回的Response,我们将其进行解析，
    public interface Parser<T> {
        T parse(Response response);
    }

    //默认的返回结果解析之字符串形式
    public class StringParser implements Parser<String> {
        @Override
        public String parse(Response response) {
            String result = null;
            try {
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    //默认的返回结果解析之字节数组形式
    public class ByteArray implements Parser<byte[]> {
        @Override
        public byte[] parse(Response response) {
            byte[] result = null;
            try {
                result = response.body().bytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

 /*   public class Callback<T> implements com.squareup.okhttp.Callback {
        private Parser<T> parser;

        ///通过构造函数将我们的Parser传递了进去。
        public Callback(Parser<T> parser) {
            if (parser == null) {
                throw new IllegalArgumentException("Parser can't be null!");

            }
            this.parser = parser;
        }

        @Override
        public void onFailure(Request request, IOException e) {
            //失败发送失败的消息
            Message message = Message.obtain();
            message.what = CALLBACK_FAILED;
            message.obj = e;
            handler.sendMessage(message);

        }

        @Override
        public void onResponse(Response response) throws IOException {
            if (response.isSuccessful()) {
                T parseResult = parser.parse(response);
                Message message = Message.obtain();
                message.what = CALLBACK_SUCCESSFUL;
                message.obj = parseResult;
                handler.sendMessage(message);
            } else {
                Message message = Message.obtain();
                message.what = CALLBACK_FAILED;
                handler.sendMessage(message);
            }
        }
    }

    static class UIHandler<T> extends Handler {
        private WeakReference weakReference;

        public UIHandler(HttpUtils.Callback callback) {
            super(Looper.getMainLooper());
            weakReference = new WeakReference(callback);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CALLBACK_SUCCESSFUL: {
                    T t = (T) msg.obj;
                    HttpUtils.Callback callback = (HttpUtils.Callback) weakReference.get();
                    if (callback != null) {
                        callback.onResponse(t);
                    }
                    break;
                }
                case CALLBACK_FAILED: {
                    IOException e = (IOException) msg.obj;
                    HttpUtils.Callback callback = (HttpUtils.Callback) weakReference.get();
                    if (callback != null) {
                        callback.onFailure(,e);
                    }
                    break;
                }
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
*/

    public interface StringCallback {
        void onFailure(Request request, IOException e);

        void onResponse(String response);
    }
    /*
    * OKHttp Http POST
    * */
   /* public void postServer(String url)*/
    /*
    根据URL获得图片
    * */
    /*public void getImage(String url, Context context) {
        ImageView imageView =new ImageView(context);

        Picasso.with(context).load(url).into(imageView);
    }*/

}

