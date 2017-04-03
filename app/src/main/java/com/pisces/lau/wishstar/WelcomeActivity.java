package com.pisces.lau.wishstar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

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


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String bingPic = prefs.getString("bing_pic", null);

        if (bingPic != null) {
            Picasso.with(this).load(bingPic).into(imageView);

        } else {
            loadImages();
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

                try {
                    toNextActivity();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(scaleAnimation);

    }

    private void loadImages() {
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

                final String bingPic = result;
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WelcomeActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(WelcomeActivity.this).load(bingPic).into(imageView);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });


    }

    private void toNextActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.PREFERENCES, Context.MODE_PRIVATE);
        String e = sharedPreferences.getString("username", "");
        String p = sharedPreferences.getString("password", "");
        Intent intent;
        if (!e.equals("") && !p.equals("")) {
            intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);

        } else {
            intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);

        }
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        finish();

    }

    //获取当日日期
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return dateFormat.format(new Date());
    }
}
