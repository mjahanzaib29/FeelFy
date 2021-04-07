package com.feelfy.feelfy.api;

import com.feelfy.feelfy.modules.CardInfo;
import com.feelfy.feelfy.modules.HorizontalChatModule;
import com.feelfy.feelfy.modules.Messages;
import com.feelfy.feelfy.modules.RegisterResponse;
import com.feelfy.feelfy.modules.UploadResponse;
import com.feelfy.feelfy.modules.UserExtraPics;
import com.feelfy.feelfy.modules.UserInfo;
import com.feelfy.feelfy.modules.UserLogin;
import com.feelfy.feelfy.modules.VerticalChatModule;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface ApiInterface {

//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/json"
//    })

//    @POST(EndApi.LOGIN)
//    Call<String> login(@Body UserLogin login);

    @GET(EndApi.REGISTER)
    Call<UserLogin> get_userlogin(
            @Query("email") String email,
            @Query("password") String pass
    );

    @GET(EndApi.LOGIN_API)
    Call<UserInfo>get_userinfo(
            @Query("u_id")             int id

    );


    @POST(EndApi.USERREGISTER)
    Call<RegisterResponse> registration(
            @Query("email") String email,
            @Query("password") String pass
    );

    @POST(EndApi.UPDATE)
    Call<JsonObject> updateProfile(@Body JsonObject login);

    @POST(EndApi.UPLOAD_PHOTO)
    Call<JsonObject> uploadPhotso(@Body JsonObject uploadPhoto);

    @POST(EndApi.REGISTER)
    Call<JsonObject> FTlogin(@Body JsonObject login);

    @POST(EndApi.MATCH_PROFILE)
    Call<JsonObject> matchProfile(@Body JsonObject matchProfile);

    @POST(EndApi.PERSON_INFO)
    Call<JsonObject> personInfo(@Body JsonObject personInfo);
/*
    @Headers({
            "Accept: application/json",
            "Content-Type: application/form-data"
    })*/
//    @POST(EndApi.UPLOAD_PHOTO)
//    Call<UploadResponse> uploadPhoto
//            (@Query("uploadphoto") File uploadphoto,
//             @Query("id") String id,
//             @Query("name") String name);



    @Multipart
    @POST(EndApi.UPLOAD_PHOTO)
    Call<UploadResponse> upload(
            @Part("uploadphoto") RequestBody description,
            @Part MultipartBody.Part file,
            @Query("id") String id,
            @Query("name") String name
    );

//    @Multipart
//    @POST(EndApi.UPLOAD_PHOTO)
//    Call<UploadResponse> postImage(@Part MultipartBody.Part image,
//                                   @Part("uploadphoto") RequestBody requestBody,
//                                   @Part("u_id") RequestBody id,
//                                   @Part("name") RequestBody name);

    @POST(EndApi.GET_PROFILEPHOTO)
    Call<UserLogin> get_profilephoto(
            @Query("u_id") int u_id
    );

    @POST(EndApi.GET_EXTRAPICS)
    Call<UserExtraPics> get_extrapics(
            @Query("u_id") int u_id
    );

    @Multipart
    @POST(EndApi.UPLOAD_PROFILEPIC)
    Call<ResponseBody> post_profilepic(@Part MultipartBody.Part file,
                                       @Part("file") RequestBody name,
                                       @Part("u_id") int id);
///////////////////////////////////////////
    @Multipart
    @POST(EndApi.UPLOAD_EXTRAPIC)
    Call<ResponseBody> post_extraimage(@Part MultipartBody.Part file,
                                       @Part("file") RequestBody name,
                                       @Part("u_id") int id);

    @Multipart
    @POST(EndApi.UPLOAD_EXTRAPIC2)
    Call<ResponseBody> post_extraimage2(@Part MultipartBody.Part file1,
                                       @Part("file1") RequestBody name1,
                                       @Part("u_id") int id);
    @Multipart
    @POST(EndApi.UPLOAD_EXTRAPIC3)
    Call<ResponseBody> post_extraimage3(@Part MultipartBody.Part file2,
                                       @Part("file2") RequestBody name2,
                                       @Part("u_id") int id);
    @Multipart
    @POST(EndApi.UPLOAD_EXTRAPIC4)
    Call<ResponseBody> post_extraimage4(@Part MultipartBody.Part file3,
                                       @Part("file3") RequestBody name3,
                                       @Part("u_id") int id);
    @Multipart
    @POST(EndApi.UPLOAD_EXTRAPIC5)
    Call<ResponseBody> post_extraimage5( @Part MultipartBody.Part file4,
                                       @Part("file4") RequestBody name4,
                                       @Part("u_id") int id);
    @Multipart
    @POST(EndApi.UPLOAD_EXTRAPIC6)
    Call<ResponseBody> post_extraimage6(@Part MultipartBody.Part file5,
                                       @Part("file5") RequestBody name5,
                                       @Part("u_id") int id);

    @GET(EndApi.USERPREFERENCE_ONE)
    Call<ResponseBody> get_preference_one(
            @Query("u_id")             int user_id,
            @Query("u_height")             String height,
            @Query("u_weight")             String weight,
            @Query("u_eyeColor")             String eyeColor,
            @Query("u_hairColor")             String hairColor,
            @Query("u_smoke")            String smoke,
            @Query("u_drink")             String drink,
            @Query("u_haveChildren")             String haveChildren
    );

    @GET(EndApi.USERPREFERENCE_TWO)
    Call<ResponseBody> get_preference_two(
            @Query("u_id")             int user_id,
            @Query("u_nickname")             String nickname,
            @Query("u_dob")             String dob,
            @Query("u_sexualOrientation")             String sexualOrientation,
            @Query("u_gender")             String gender
    );

    @GET(EndApi.USERPREFERENCE_THREE)
    Call<ResponseBody> post_fancyfeeling(
            @Query("u_id") int user_id,
            @Query("u_feeling") String feelingpreference
    );

    @GET(EndApi.USER_ADVANCESEARCH)
    Call<ResponseBody> get_userAdvancesearch(
            @Query("u_id")             int user_id,
            @Query("u_height")             String height,
            @Query("u_weight")             String weight,
            @Query("u_eyeColor")             String eyeColor,
            @Query("u_hairColor")             String hairColor,
            @Query("u_smoke")            String smoke,
            @Query("u_drink")             String drink,
            @Query("u_haveChildren")             String haveChildren
    );
    @GET(EndApi.USER_SEARCH)
    Call<ResponseBody> get_userSearch(
            @Query("u_id") int user_id,
            @Query("u_minage")             String min_age,
            @Query("u_maxage")             String max_age,
            @Query("u_mindistance")            String min_distance,
            @Query("u_maxdistance")            String max_distance,
            @Query("u_sOrientation")             String sOrientation,
            @Query("u_genderIdentity")             String genderIdentity
    );

//
//    @GET(EndApi.PERSON_INFO)
//    Call<JsonElement> get_card_data();

    @GET(EndApi.PERSON_INFO)
    Call<List<CardInfo>> get_card_data(
            @Query("user_id") int user_id
    );


    @GET(EndApi.Right_Swipe)
    Call<ResponseBody> get_rightswiperesponse(
            @Query("user_id") int user_id,
            @Query("card_id") int card_id
    );

    @GET(EndApi.Left_Swipe)
    Call<ResponseBody> get_leftswiperesponse(
            @Query("user_id") int user_id,
            @Query("card_id") int card_id
    );

    @GET(EndApi.GETUSERPREFERENCE_ONE)
    Call<JsonElement> get_user_preference_one(
            @Query("u_id") int user_id
    );
    @GET(EndApi.GETUSERPREFERENCE_TWO)
    Call<JsonElement> get_user_preference_two(
            @Query("u_id") int user_id
    );
    @GET(EndApi.GETUSERPREFERENCE_THREE)
    Call<JsonElement> get_user_preference_three(
            @Query("u_id") int user_id
    );



    //Get match_profile vchatdata
    @GET(EndApi.GETVCHAT)
    Call<ArrayList<VerticalChatModule>> get_Vchat(
            @Query("user_id") int user_id
    );

    @GET(EndApi.GETVKCHAT)
    Call<ArrayList<VerticalChatModule>> get_v1(
            @Query("user_id") int user_id
    );

    @GET(EndApi.GETVK2CHAT)
    Call<ArrayList<VerticalChatModule>> get_v2(
            @Query("user_id") int user_id
    );




    //Get Liked hchatdata
    @GET(EndApi.GETHCHAT)
    Call<ArrayList<HorizontalChatModule>> get_Hchat(
            @Query("user_id") int user_id
    );

    @GET(EndApi.GETMESSEGES)
    Call<List<Messages>> getMesseges(
            @Query("userAid") int log_id,
            @Query("userBid") String matchid
    );
    @GET(EndApi.GETRMESSEGES)
    Call<List<Messages>> getreceivedMesseges(
            @Query("userAid") int log_id,
            @Query("userBid") String matchid
    );

    @POST(EndApi.SENDMESSEGES)
    Call<ResponseBody> sendmessege(
            @Query("userAid") int log_id,
            @Query("userAName") String logname,
            @Query("userBid") String matchid,
            @Query("userBName") String matchname,
            @Query("message") String messege);


    @GET(EndApi.delete_account)
    Call<ResponseBody> get_delete_account(
            @Query("user_id") int user_id
    );

    @POST(EndApi.PKGPURCHASE)
    Call<ResponseBody> pkgpurchase(
            @Query("user_id") int user_id,
            @Query("start_time") String start_time,
            @Query("end_time") String end_time,
            @Query("purchase_type") String purchase_type,
            @Query("point") int point
    );

    @POST(EndApi.INDIVIDUALPURCHASE)
    Call<ResponseBody> purchase(
            @Query("user_id") int user_id,
            @Query("start_time") String start_time,
            @Query("end_time") String end_time,
            @Query("purchase_type") String purchase_type,
            @Query("point") int point
    );

    @GET(EndApi.CheckSub)
    Call<JsonElement> getSub(
            @Query("user_id") int user_id
    );
    @GET(EndApi.CheckSub)
    Call<RegisterResponse> getSub1(
            @Query("user_id") int user_id
    );

    @GET(EndApi.CheckValidity)
    Call<RegisterResponse> checkvalidity(
            @Query("user_id") int user_id);

    @GET(EndApi.GetPKDDetail)
    Call<JsonElement> get_pkgdetail(
            @Query("user_id") int user_id);

    @GET(EndApi.UsePKGDetail)
    Call<RegisterResponse> using_package_switchbtn(
            @Query("user_id") int user_id,
            @Query("start_time") String pkgstart,
            @Query("end_time") String pkgexpire
    );

    @GET(EndApi.package_purchase_status)
    Call<RegisterResponse> updatepkgstatus(
            @Query("user_id") int user_id
    );

    @GET(EndApi.premium_rightswipe)
    Call<ResponseBody> premium_rightswipe(
            @Query("user_id") int user_id,
            @Query("card_id") int card_id
    );

    @GET(EndApi.checkvisibility)
    Call<ResponseBody> isActive(
            @Query("user_id") int user_id,
            @Query("isenable") int is_enable
    );

    @GET(EndApi.getvisibility)
    Call<RegisterResponse> getprofilevisibilty(
            @Query("user_id") int user_id
    );
}

