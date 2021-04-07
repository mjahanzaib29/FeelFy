package com.feelfy.feelfy.modules;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CardInfo {
    @SerializedName("user_id")
    String user_id;
    @SerializedName("image")
    String image;
    @SerializedName("nickname")
    String nickname;
    @SerializedName("dob")
    String dob;

    public CardInfo() {
    }

    public CardInfo(String user_id, String image, String nickname, String dob) {
        this.user_id = user_id;
        this.image = image;
        this.nickname = nickname;
        this.dob = dob;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return '{'+
                "user_id='" + user_id + '\'' +
                ", image='" + image + '\'' +
                ", nickname=" + nickname +'\'' +
                ", dob=" + dob+
                '}';
    }
}
