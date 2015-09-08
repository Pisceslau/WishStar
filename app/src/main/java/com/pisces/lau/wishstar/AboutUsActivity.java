package com.pisces.lau.wishstar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pisces.lau.wishstar.util.BaseActivity;

/**
 * Created by Liu Wenyue on 2015/8/7.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class AboutUsActivity extends BaseActivity{
    /*关于我们:开发组和应用相关信息*/
    private TextView appIntro;
    private ImageView appIcon;
    private Button toWebSite,toFeedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appIntro = (TextView) findViewById(R.id.app_intro);
        appIcon = (ImageView) findViewById(R.id.app_icon);
        appIntro.setMovementMethod(new ScrollingMovementMethod());
        toWebSite = (Button) findViewById(R.id.to_website);
        toFeedback = (Button)findViewById(R.id.to_feedback);
        toWebSiteInfo();
        toFeedback.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                toFeedback();
                return true;
            }
        });

    }

    private void toWebSiteInfo() {
        toWebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, WebsiteInfoActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.about_us_layout;
    }



    private void toFeedback() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AboutUsActivity.this);
        builder.setMessage("请把您的反馈发送给我们!");
        builder.setTitle("提示:");
        builder.setPositiveButton("反馈", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(AboutUsActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}
