package com.pisces.lau.wishstar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pisces.lau.wishstar.util.BaseActivity;

/**
 * Created by Liu Wenyue on 2015/8/7.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class AboutUsActivity extends BaseActivity {
    /*关于我们:开发组和应用相关信息*/
 /*   private TextView appIntro;*/
    private Toolbar toolbar;
    private ImageView header;


    CollapsingToolbarLayout collapsingToolbarLayout;
    private Button toWebSite, toFeedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        header = (ImageView) findViewById(R.id.header);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        /*appIntro = (TextView) findViewById(R.id.app_intro);*/
        //  appIcon = (ImageView) findViewById(R.id.app_icon);
       /* appIntro.setMovementMethod(new ScrollingMovementMethod());*/
        toWebSite = (Button) findViewById(R.id.to_website);
        toFeedback = (Button) findViewById(R.id.to_feedback);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("关于我们");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.us_text);


        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch muteColor = palette.getMutedSwatch();
                if (muteColor != null) {

                    collapsingToolbarLayout.setContentScrimColor(muteColor.getRgb());
                } else {
                    collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#2196F3"));

                }
            }
        });


        toWebSiteInfo();
        toFeedback.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                toFeedback();
                return true;
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.about_us_layout;
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.detailed_share) ;
        //分享应用给朋友


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailed_info, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
