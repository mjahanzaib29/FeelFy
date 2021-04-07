package com.feelfy.feelfy.modules;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestProfileUpdate implements Serializable {
    private String email;
    private String id;
    private String You;
    private String name;
    private String Dob;
    private String profilephoto;
    private String Sexual;
    @SerializedName("response")
    private String response;
    private int Card_id;

    public int getCard_id() {
        return Card_id;
    }

    public void setCard_id(int card_id) {
        Card_id = card_id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }

    private String Gender_identify;
    private String Feeling;
    private String Fminsearchage;
    private String height;
    private String Fmaxsearchage;
    private String Fmindisttance;
    private String Fmaxdistance;
    private String FsexString;
    private String gender;
    private String Fheight;
    private String Fweight;
    private String Fecolor;
    private String Fhcolor;
    private String Dfsmoke;
    private String Ddalcohal;
    private String Fhchildren;
    private String Yage;
    private String Ysex;
    private String Ygender;
    private String Yheight;
    private String Yweight;
    private String Yecolor;
    private String Yhcolor;
    private String Ydsmoke;
    private String Ydalcohal;
    private String Yhchildren;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYou() {
        return You;
    }

    public void setYou(String You) {
        this.You = You;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getSexual() {
        return Sexual;
    }

    public void setSexual(String sexual) {
        Sexual = sexual;
    }

    public String getGender_identify() {
        return Gender_identify;
    }

    public void setGender_identify(String gender_identify) {
        Gender_identify = gender_identify;
    }

    public String getFeeling() {
        return Feeling;
    }

    public void setFeeling(String feelingString) {
        Feeling = feelingString;
    }

    public String getFminsearchage() {
        return Fminsearchage;
    }

    public void setFminsearchage(String fminsearchage) {
        Fminsearchage = fminsearchage;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getFmaxsearchage() {
        return Fmaxsearchage;
    }

    public void setFmaxsearchage(String fmaxsearchage) {
        Fmaxsearchage = fmaxsearchage;
    }

    public String getFmindisttance() {
        return Fmindisttance;
    }

    public void setFmindisttance(String fmindisttance) {
        Fmindisttance = fmindisttance;
    }

    public String getFmaxdistance() {
        return Fmaxdistance;
    }

    public void setFmaxdistance(String fmaxdistance) {
        Fmaxdistance = fmaxdistance;
    }

    public String getFsexString() {
        return FsexString;
    }

    public void setFsexString(String fsexString) {
        FsexString = fsexString;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFheight() {
        return Fheight;
    }

    public void setFheight(String fheight) {
        Fheight = fheight;
    }

    public String getFweight() {
        return Fweight;
    }

    public void setFweight(String fweight) {
        Fweight = fweight;
    }

    public String getFecolor() {
        return Fecolor;
    }

    public void setFecolor(String fecolor) {
        Fecolor = fecolor;
    }

    public String getFhcolor() {
        return Fhcolor;
    }

    public void setFhcolor(String fhcolor) {
        Fhcolor = fhcolor;
    }

    public String getDfsmoke() {
        return Dfsmoke;
    }

    public void setDfsmoke(String dfsmoke) {
        Dfsmoke = dfsmoke;
    }

    public String getDdalcohal() {
        return Ddalcohal;
    }

    public void setDdalcohal(String ddalcohal) {
        Ddalcohal = ddalcohal;
    }

    public String getFhchildren() {
        return Fhchildren;
    }

    public void setFhchildren(String fhchildren) {
        Fhchildren = fhchildren;
    }

    public String getYage() {
        return Yage;
    }

    public void setYage(String yage) {
        Yage = yage;
    }

    public String getYsex() {
        return Ysex;
    }

    public void setYsex(String ysex) {
        Ysex = ysex;
    }

    public String getYgender() {
        return Ygender;
    }

    public void setYgender(String ygender) {
        Ygender = ygender;
    }

    public String getYheight() {
        return Yheight;
    }

    public void setYheight(String yheight) {
        Yheight = yheight;
    }

    public String getYweight() {
        return Yweight;
    }

    public void setYweight(String yweight) {
        Yweight = yweight;
    }

    public String getYecolor() {
        return Yecolor;
    }

    public void setYecolor(String yecolor) {
        Yecolor = yecolor;
    }

    public String getYhcolor() {
        return Yhcolor;
    }

    public void setYhcolor(String yhcolor) {
        Yhcolor = yhcolor;
    }

    public String getYdsmoke() {
        return Ydsmoke;
    }

    public void setYdsmoke(String ydsmoke) {
        Ydsmoke = ydsmoke;
    }

    public String getYdalcohal() {
        return Ydalcohal;
    }

    public void setYdalcohal(String ydalcohal) {
        Ydalcohal = ydalcohal;
    }

    public String getYhchildren() {
        return Yhchildren;
    }

    public void setYhchildren(String yhchildren) {
        Yhchildren = yhchildren;
    }



    private String user_id;
    private String weight;
    private String eyeColor;
    private String hairColor;
    private String smoke;
    private String drink;
    private String haveChildren;
    private String nickname;
    private String sexualOrientation;
    private String description;
    private String sexualPreference;
    private String issubscribed;
    private String isvisible;
    private String usertype;
    private String isactive;

    public String getIssubscribed() {
        return issubscribed;
    }

    public void setIssubscribed(String issubscribed) {
        this.issubscribed = issubscribed;
    }

    public String getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(String isvisible) {
        this.isvisible = isvisible;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getHaveChildren() {
        return haveChildren;
    }

    public void setHaveChildren(String haveChildren) {
        this.haveChildren = haveChildren;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSexualOrientation() {
        return sexualOrientation;
    }

    public void setSexualOrientation(String sexualOrientation) {
        this.sexualOrientation = sexualOrientation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSexualPreference() {
        return sexualPreference;
    }

    public void setSexualPreference(String sexualPreference) {
        this.sexualPreference = sexualPreference;
    }

    public RequestProfileUpdate() {
    }

    public RequestProfileUpdate(int user_id, String fheight, String fweight, String fecolor, String fhcolor, String dfsmoke, String ddalcohal, String fhchildren) {
        Fheight = fheight;
        Fweight = fweight;
        Fecolor = fecolor;
        Fhcolor = fhcolor;
        Dfsmoke = dfsmoke;
        Ddalcohal = ddalcohal;
        Fhchildren = fhchildren;
        user_id = user_id;
    }

    public RequestProfileUpdate(String name, String dob, String sexual, String gender_identify) {
        this.name = name;
        Dob = dob;
        Sexual = sexual;
        Gender_identify = gender_identify;
    }
}
