package com.pisces.lau.wishstar;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.glomadrian.materialanimatedswitch.MaterialAnimatedSwitch;
import com.pisces.lau.wishstar.bean.User;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Liu Wenyue on 2015/7/16.
 * 应用注册
 */
public class RegisterActivity extends AppCompatActivity {
    EditText userNameEditText;
    EditText userPasswordEditText;
    EditText userEmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        final TextInputLayout userName = (TextInputLayout) findViewById(R.id.name_input);
        final TextInputLayout userPassword = (TextInputLayout) findViewById(R.id.password_input);
        final TextInputLayout userEmail = (TextInputLayout) findViewById(R.id.email_input);

        Button registerButton = (Button) findViewById(R.id.user_register);

        MaterialAnimatedSwitch materialAnimatedSwitch = (MaterialAnimatedSwitch) findViewById(R.id.pin);

        /*userNameEditText = (EditText) findViewById(R.id.user_name);
        userPasswordEditText = (EditText) findViewById(R.id.user_password);
        userEmailEditText = (EditText) findViewById(R.id.user_email);*/
        userNameEditText = userName.getEditText();
        userPasswordEditText = userPassword.getEditText();
        userEmailEditText = userEmail.getEditText();


        //点击注册按钮
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //如果输入的用户名,密码,邮箱非空则进行注册过程,有一个为空则弹出Toast
                String name = userNameEditText.getText().toString();
                String password = userPasswordEditText.getText().toString();
                String email = userEmailEditText.getText().toString();
                if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "请填完整注册信息!", Toast.LENGTH_LONG).show();
                }
                userRegister(name, password, email);

            }
        });

        materialAnimatedSwitch.setOnCheckedChangeListener(new MaterialAnimatedSwitch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                Toast.makeText(RegisterActivity.this, "记住密码", Toast.LENGTH_LONG).show();
                //SharedPreferences记住密码功能方法

            }
        });


        //用户输入用户名时的监听器
        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 10) {
                    userName.setErrorEnabled(true);
                    userName.setError("姓名长度不能超过10个字");
                } else {
                    userName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //用户输入密码时的监听
        userPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 10) {
                    userPassword.setErrorEnabled(true);
                    userPassword.setError("密码长度不能超过10个字");
                } else {
                    userPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //用户输入邮件时的监听
        userEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //如果该字符串中没有@符号则
                if (!s.toString().contains(String.valueOf('@'))) {
                    userEmail.setErrorEnabled(true);
                    userEmail.setError("请输入正确的邮箱地址格式");
                } else {
                    userEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void userRegister(String name, String password, String email) {
        //注册过程,采用Bmob云存储注册的用户名 密码 邮箱信息
        User user = new User();

        user.setUsername(name);
        user.setPassword(password);
        user.setEmail(email);
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {

                Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getApplicationContext(), "注册失败!,请进行检查!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
