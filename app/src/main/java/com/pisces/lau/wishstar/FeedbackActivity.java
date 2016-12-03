package com.pisces.lau.wishstar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pisces.lau.wishstar.bean.Feedback;
import com.pisces.lau.wishstar.util.BaseActivity;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Liu Wenyue on 2015/7/29.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class FeedbackActivity extends BaseActivity {
    /*用户反馈界面*/
    TextView titleField;
    EditText nameField;
    EditText emailField;
    EditText feedbackField;
    Spinner feedbackSpinner;
    CheckBox responseCheckbox;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.feedback_layout;
    }

    //初始哈相关组件
    public void init() {

        titleField = (TextView) findViewById(R.id.feedback_title);
        nameField = (EditText) findViewById(R.id.feedback_name);
        emailField = (EditText) findViewById(R.id.feedback_email);
        feedbackField = (EditText) findViewById(R.id.feedback_body);
        feedbackSpinner = (Spinner) findViewById(R.id.feedback_type);
        responseCheckbox = (CheckBox) findViewById(R.id.CheckBox_response);
        submit = (Button) findViewById(R.id.Button_sendFeedback);
    }

    //发送反馈至云服务器
    public void sendFeedback(View view) {

        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String feedback = feedbackField.getText().toString();
        String feedbackType = feedbackSpinner.getSelectedItem().toString();
        Boolean bRequiresResponse = responseCheckbox.isChecked();

        if (name.isEmpty() || email.isEmpty() || feedback.isEmpty() || feedbackType.isEmpty()) {
            return;
        }

        Feedback feedbackObj = new Feedback();
        feedbackObj.setName(name);
        feedbackObj.setEmail(email);
        feedbackObj.setFeedback(feedback);
        feedbackObj.setFeedbackType(feedbackType);
        feedbackObj.setbRequiresResponse(bRequiresResponse);

        feedbackObj.save(this, new SaveListener() {
            @Override
            public void onSuccess() {

                Toast.makeText(FeedbackActivity.this, "提交成功!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(int i, String s) {

                Toast.makeText(FeedbackActivity.this, "提交失败!", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}

