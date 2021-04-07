package com.feelfy.feelfy.modules;

import com.google.gson.annotations.SerializedName;

public class UserLogin_response {
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String e_email;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_picture")
    private String profile_picture;
    @SerializedName("isSubscribed")
    private String isSubscribed;
    @SerializedName("isVisible")
    private String isVisible;
    @SerializedName("userType")
    private String userType;
    @SerializedName("isActive")
    private String isActive;

    public String getId() {
        return id;
    }

    public String getE_email() {
        return e_email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public String getIsSubscribed() {
        return isSubscribed;
    }

    public String getIsVisible() {
        return isVisible;
    }

    public String getUserType() {
        return userType;
    }

    public String getIsActive() {
        return isActive;
    }
}
