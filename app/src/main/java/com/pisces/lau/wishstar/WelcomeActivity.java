package com.pisces.lau.wishstar;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.pisces.lau.wishstar.util.HttpUtils;
import com.squareup.picasso.Picasso;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //获得窗口无标题特性
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_layout);
        /*dateView = (TextView) findViewById(R.id.date_view);*/
        imageView = (ImageView) findViewById(R.id.splash_view);
        //设置时间显示在欢迎界面TextView中
      /*  dateView.setText(getCurrentDate());
*/
        //初始化欢迎图片
        InitImage();
    }

    private void InitImage() {
        File dir = getFilesDir();
        final File imgFile = new File(dir, "start.jpg");
        if (imgFile.exists()) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));

        } else {
            imageView.setImageResource(R.drawable.welcome);
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
                if (HttpUtils.isNetworkConnected(WelcomeActivity.this)) {
                    try {
                        String jsonData = HttpUtils.getString(AppConstants.SPLASH_PHOTO_API);

                        JSONObject jsonObject = new JSONObject(jsonData);
                        //获得图片的URL
                        String url = jsonObject.getString("img");
                        Log.v("url", url);

                        Picasso.with(WelcomeActivity.this).load(url).placeholder(R.drawable.welcome).into(imageView);
                        // saveImage(imgFile, bytes);
                        toNextActivity();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

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
