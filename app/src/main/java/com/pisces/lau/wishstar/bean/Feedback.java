package com.pisces.lau.wishstar.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Liu Wenyue on 2015/7/30.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class Feedback extends BmobObject {

    private String name;
    private String email;
    private Boolean bRequiresResponse;
    private String feedbackType;
    private String feedback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getbRequiresResponse() {
        return bRequiresResponse;
    }

    public void setbRequiresResponse(Boolean bRequiresResponse) {
        this.bRequiresResponse = bRequiresResponse;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
