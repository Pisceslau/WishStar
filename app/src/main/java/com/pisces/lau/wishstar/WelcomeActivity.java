package com.pisces.lau.wishstar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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
    TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        dateView = (TextView) findViewById(R.id.date_view);
        //设置时间显示在欢迎界面TextView中
        dateView.setText(getCurrentDate());

    }

    //获取当日日期
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return dateFormat.format(new Date());
    }
}
