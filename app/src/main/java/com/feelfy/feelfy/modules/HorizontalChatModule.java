package com.feelfy.feelfy.modules;

import com.google.gson.annotations.SerializedName;

public class HorizontalChatModule {

    @SerializedName("nickname")
    private String userName;
    @SerializedName("image")
    private String image;
    @SerializedName("dob")
    private String dob;


    public HorizontalChatModule(String userName, int image , String dob){
       this.userName = userName;
       this.image= String.valueOf(image);
       this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
