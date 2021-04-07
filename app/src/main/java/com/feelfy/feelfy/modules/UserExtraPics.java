package com.feelfy.feelfy.modules;

import com.google.gson.annotations.SerializedName;

public class UserExtraPics {
    @SerializedName("user_id")
    private String id;
    @SerializedName("image1")
    private String Eimage1;
    @SerializedName("image2")
    private String Eimage2;
    @SerializedName("image3")
    private String Eimage3;
    @SerializedName("image4")
    private String Eimage4;
    @SerializedName("image5")
    private String Eimage5;
    @SerializedName("image6")
    private String Eimage6;

    public String getId() {
        return id;
    }

    public String getEimage1() {
        return Eimage1;
    }

    public String getEimage2() {
        return Eimage2;
    }

    public String getEimage3() {
        return Eimage3;
    }

    public String getEimage4() {
        return Eimage4;
    }

    public String getEimage5() {
        return Eimage5;
    }

    public String getEimage6() {
        return Eimage6;
    }
}
