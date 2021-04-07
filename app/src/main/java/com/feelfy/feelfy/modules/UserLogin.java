package com.feelfy.feelfy.modules;

import com.google.gson.annotations.SerializedName;

public class UserLogin {
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String e_email;
    @SerializedName("password")
    private String password;
    @SerializedName("nickname")
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
    @SerializedName("image")
    private String image;

    public UserLogin(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getE_email() {
        return e_email;
    }

    public void setE_email(String e_email) {
        this.e_email = e_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(String isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public String getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(String isVisible) {
        this.isVisible = isVisible;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
