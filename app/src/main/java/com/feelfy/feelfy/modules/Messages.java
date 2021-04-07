package com.feelfy.feelfy.modules;

import com.google.gson.annotations.SerializedName;

public class Messages {

    @SerializedName("message")
    private String message;
    @SerializedName("time")
    private String time;
    @SerializedName("date")
    private String date;
    @SerializedName("userAid")
    private int user_id;
    @SerializedName("userBid")
    private int match_id;


    public Messages(String message, String time, String date, int user_id, int match_id) {
        this.message = message;
        this.time = time;
        this.date = date;
        this.user_id = user_id;
        this.match_id = match_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
