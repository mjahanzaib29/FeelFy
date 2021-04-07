package com.feelfy.feelfy.modules;

import com.google.gson.annotations.SerializedName;

public class VerticalChatModule {

    @SerializedName("nickname")
    private String userName;
    @SerializedName("image")
    private String image;
    @SerializedName("dob")
    private String unreadmsg;
    @SerializedName("card_user_id")
    private String card_user_id;

    public VerticalChatModule(String userName, String image, String unreadmsg, String card_user_id) {
        this.userName = userName;
        this.image = image;
        this.unreadmsg = unreadmsg;
        this.card_user_id = card_user_id;
    }

    public String getCard_user_id() {
        return card_user_id;
    }

    public void setCard_user_id(String card_user_id) {
        this.card_user_id = card_user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUnreadmsg() {
        return unreadmsg;
    }

    public void setUnreadmsg(String unreadmsg) {
        this.unreadmsg = unreadmsg;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
