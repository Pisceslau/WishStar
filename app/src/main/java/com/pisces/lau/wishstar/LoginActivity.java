package com.pisces.lau.wishstar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pisces.lau.wishstar.bean.User;
import com.pisces.lau.wishstar.util.Util;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by Liu Wenyue on 2015/7/16.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
/*
* 登录*/
public class LoginActivity extends AppCompatActivity {

    public Button normalLogin, qqLogin;
    public ImageView userLogo;
    public EditText loginUsername, loginPassword;
    public TextView toRegister;


    public Tencent mTencent;
    public String nicknameString;

    public QQAuth mQQAuth;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);
        mTencent = Tencent.createInstance(AppConstants.QQ_Login_APP_ID, this.getApplicationContext());
        normalLogin = (Button) findViewById(R.id.normalLogin);
        qqLogin = (Button) findViewById(R.id.qqLogin);
        toRegister = (TextView) findViewById(R.id.to_register);
        userLogo = (ImageView) findViewById(R.id.user_logo);
        loginUsername = (EditText) findViewById(R.id.login_username);
        loginPassword = (EditText) findViewById(R.id.login_password);


        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到RegisterActivity注册
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

    }


    public void normalLogin(View view) {
        //点击正常登录的按钮触发的方法,若成功则Intent进入下一个Activity
        final String username = loginUsername.getText().toString();
        final String password = loginPassword.getText().toString();
        BmobUser.loginByAccount(this, username, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (user != null) {
                    Log.v(username, "登录成功!");
                    BmobUser bmobUser = BmobUser.getCurrentUser(getApplicationContext());
                    if (bmobUser != null) {
                        //允许用户使用应用,待写
                    }
                    Intent intent = new Intent();
                    //Intent传递用户名,密码跳转到MainActivity
                    intent.putExtra(username, password);
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);

                } else {
                    Log.v(username, "登录失败!");
                }
            }
        });


    }


    public void qqLogin(View view) {
        //点击QQ登录的按钮触发的方法,调用腾讯开放中心相关
        //这里的APP_ID请换成你应用申请的APP_ID
        mTencent.login(LoginActivity.this, "all", new BaseUilListener());


    }

    private class BaseUilListener implements IUiListener {
        public BaseUilListener() {
            super();
        }

        @Override
        public void onComplete(Object response) {

            Toast.makeText(getApplicationContext(), "登录成功!", Toast.LENGTH_SHORT).show();
            //获得的数据是JSON格式的,获得你想获得的内容
            Log.d(AppConstants.LoginActivity_TAG, response.toString());


            //获得用户信息,例如头像和昵称等
            QQToken qqToken = mTencent.getQQToken();
            UserInfo info = new UserInfo(getApplicationContext(), qqToken);
            info.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(final Object response) {
                    Log.d(AppConstants.LoginActivity_TAG, "UserInfo");
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    Log.d(AppConstants.LoginActivity_TAG, response.toString());

                    new Thread() {
                        @Override
                        public void run() {

                            JSONObject json = (JSONObject) response;
                            try {
                                bitmap = Util.getBitmap(json.getString("figureurl_qq_2"));
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                            Message msg = new Message();
                            msg.obj = bitmap;
                            msg.what = 1;
                            mHandler.sendMessage(msg);

                        }
                    }.start();
                }

                @Override
                public void onError(UiError uiError) {
                    Log.e(AppConstants.LoginActivity_TAG, "--111113" + ":" + uiError);
                }

                @Override
                public void onCancel() {
                    Log.e(AppConstants.LoginActivity_TAG, "-111112");
                }
            });
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }
   /* super.handleMessage(msg);
    if(msg.what==0){
        JSONObject response=(JSONObject)msg.obj;
        if(response.has("nickname")){
            try{
                nicknameString=response.getString("nickname");
                Log.d("用户名",nicknameString);
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
    }else if (msg.what==1){
        Bitmap bitmap=(Bitmap)msg.obj;
        userLogo.setImageBitmap(bitmap);
    }*/


    private Handler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<Activity> mActivity;

        public MyHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (mActivity.get() == null) {
                return;
            }
        }
    }

}






