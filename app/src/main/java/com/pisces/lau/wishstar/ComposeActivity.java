package com.pisces.lau.wishstar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pisces.lau.wishstar.bean.Message;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Liu Wenyue on 2015/8/5.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class ComposeActivity extends AppCompatActivity {
    /*撰写愿望并发布(到愿望大厅)模块*/
    private EditText editText;
    private AppCompatButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_layout);
        button = (AppCompatButton) findViewById(R.id.send_to_public);
        editText = (EditText) findViewById(R.id.message_to_public);
        send2Public(button);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    //发送消息方法
    public void send2Public(View view) {
        if (view.getId() == R.id.send_to_public) {
            if (editText != null) {
                String message = editText.getText().toString();
                //Bmob逻辑
                Message s = new Message();
                s.setMessage(message);
                //Bmob登录保持？
                BmobUser bmobUser = BmobUser.getCurrentUser(this);
                s.setUsername(bmobUser.getUsername());
                s.save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {

                        Toast.makeText(ComposeActivity.this, "提交成功!", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(int i, String s) {

                        Toast.makeText(ComposeActivity.this, "提交失败!", Toast.LENGTH_LONG).show();

                    }
                });
            }
        }
    }
}
