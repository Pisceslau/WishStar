package com.pisces.lau.wishstar;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pisces.lau.wishstar.util.BaseActivity;

/**
 * Created by Liu Wenyue on 2015/8/9.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class WebsiteInfoActivity extends BaseActivity {


    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = (WebView) findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webSetUp();
        String newThreadUrl = "http://www.new-thread.com/";
        webView.loadUrl(newThreadUrl);
    }

    //WebView相关设置
    private void webSetUp() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.website_info_layout;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            //返回键事件
            case KeyEvent.KEYCODE_BACK:
                if (webView.canGoBack() && webView != null) {
                    webView.goBack();
                    return true;
                }


        }
        return super.onKeyDown(keyCode, event);
    }
}
