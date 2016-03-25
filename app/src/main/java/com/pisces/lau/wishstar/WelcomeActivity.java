package com.pisces.lau.wishstar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Liu Wenyue on 2015/10/3.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
/*欢迎界面,当用户登录后以后的进入应用时候*/
public class WelcomeActivity extends AppCompatActivity {
    /* TextView dateView;*/
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //获得窗口无标题特性
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_layout);
        /*dateView = (TextView) findViewById(R.id.date_view);*/
        imageView = (ImageView) findViewById(R.id.splash_view);
        textView = (TextView) findViewById(R.id.photoInfo);
        //设置时间显示在欢迎界面TextView中
      /*  dateView.setText(getCurrentDate());
*/
        //初始化欢迎图片
        InitImage();
    }

    private void InitImage() {
        try {
        File dir = getFilesDir();
            final File imgFile = new File(dir, "start.jpg");

            /*OkHttpClient okHttpClient=new OkHttpClient();
            HttpUtils.StringParser parser=new HttpUtils.StringParser();
            Request request = new Request.Builder().url("https://www.baidu.com").build();
            okHttpClient.newCall(request).enqueue(Callback<String>(parser) {
                @Override
                public void onResponse(String s) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }

            });*/
       /*     Log.v("json",json);
            JSONObject jsonObject = new JSONObject(json);
            String url = jsonObject.getString("img");
            Log.v("url", url);
            Picasso.with(WelcomeActivity.this).load(url).into(imageView);*/
            FinalHttp fh = new FinalHttp();
            fh.get(AppConstants.SPLASH_PHOTO_API, new AjaxCallBack<String>() {
                @Override
                public void onStart() {

                }

                @Override
                public void onLoading(long count, long current) {

                }

                @Override
                public void onSuccess(String result) {
                    try {
                        Log.v("json", result);
                        JSONObject jsonObject = new JSONObject(result);
                        String url = jsonObject.getString("img");
                        String text = jsonObject.getString("text");
                        Log.v("url", url);

                        Picasso.with(WelcomeActivity.this).load(url).into(imageView);
                        textView.setText(text);

                        //    parseResult(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    super.onFailure(t, errorNo, strMsg);
                }
            });
            // Log.v("json",json);
         /*   JSONObject jsonObject = new JSONObject(re);
            String url = jsonObject.getString("img");
            Log.v("url", url);
         Picasso.with(WelcomeActivity.this).load(url).into(imageView);*/
            //saveImage(imgFile, url);
   /*         if (imgFile.exists()) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));

            } else {
                imageView.setImageResource(R.drawable.welcome);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }


        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //如果网络连接的话
             /*   if (HttpUtils.isNetworkConnected(WelcomeActivity.this))
                {*/
                    try {
                        //   byte[] bytes = HttpUtils.getString(AppConstants.SPLASH_PHOTO_API);

           /*             OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(AppConstants.SPLASH_PHOTO_API).build();
                        Response response = client.newCall(request).execute();
                        byte[] bytes = response.body().bytes();

                        JSONObject jsonObject = new JSONObject(new String(bytes));
                        String url = jsonObject.getString("img");
                        Log.v("url", url);

                        Picasso.with(WelcomeActivity.this).load(url).into(imageView);
                        saveImage(imgFile,bytes);*/
                        toNextActivity();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
           /* }*/

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(scaleAnimation);
    }

    private void toNextActivity() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        finish();

    }

    public void saveImage(File file, byte[] bytes) {
        try {
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //获取当日日期
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return dateFormat.format(new Date());
    }
}
