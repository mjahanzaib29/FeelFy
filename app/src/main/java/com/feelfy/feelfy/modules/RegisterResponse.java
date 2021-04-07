package com.feelfy.feelfy.modules;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("response")
    private String response;

    @SerializedName("sub")
    private String sub;

    @SerializedName("purchase_type")
    private String purchase_type;

    @SerializedName("pkg")
    private String pkg;

    @SerializedName("bundledetail")
    private String bundledetail;

    @SerializedName("pkgstatus")
    private String pkgstatus;

    @SerializedName("status")
    private String status;

    @SerializedName("isenable")
    private String isenable;

    public String getIsenable() {
        return isenable;
    }

    public String getStatus() {
        return status;
    }

    public String getPkgstatus() {
        return pkgstatus;
    }

    public String getBundledetail() {
        return bundledetail;
    }

    public String getPkg() {
        return pkg;
    }

    public String getPurchase_type() {
        return purchase_type;
    }

    public String getSub() {
        return sub;
    }

    public String getResponse() {
        return response;
    }
}
