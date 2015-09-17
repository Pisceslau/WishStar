package com.pisces.lau.wishstar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.pisces.lau.wishstar.util.BaseActivity;

/**
 * Created by Liu Wenyue on 2015/9/16.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class PressInfoActivity extends BaseActivity {
    TextView textView;
    StringBuilder pressInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textView = (TextView) findViewById(R.id.pressInfo_text);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("pressInfo");
        String[] pressInfoArray = bundle.getStringArray("pressInfo");


        if (pressInfoArray != null) {
            for (String pressInfoArrayItem : pressInfoArray) {
                Log.v("pressInfo2", pressInfoArrayItem);
                pressInfo.append(pressInfoArrayItem).append("\n");
                textView.setText(pressInfo);
            }
        }
    }

    //出版信息
    @Override
    protected int getLayoutResourceId() {
        return R.layout.press_info_layout;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.detailed_share) {
            //点击该图标,会进行分享.社会化分享shareSDK相关

        }
        return super.onOptionsItemSelected(item);

    }
}
