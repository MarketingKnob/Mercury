package com.marketingknob.mercury.webservices.interfaces;

import com.google.gson.JsonElement;
import com.marketingknob.mercury.webservices.WebConstants;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Akshay
 */

public interface WebApi {

    /*SignUp*/
    @FormUrlEncoded
    @POST(WebConstants.SIGNUP_USER_URL)
    Call<JsonElement> signUpUser(@Field("name") String name, @Field("phone") String phone, @Field("device_id") String device_id,
                                 @Field("device_type") String device_type, @Field("email") String email, @Field("gender") String gender);
    /*LogIn*/
    @FormUrlEncoded
    @POST(WebConstants.LOGIN_URL)
    Call<JsonElement> login_user(@Field("phone") String phone);

    /*Verify OTP*/
    @FormUrlEncoded
    @POST(WebConstants.VERIFY_OTP)
    Call<JsonElement> verifyOtp(@Field("otp") String otp,@Field("userid") String userid);

    /*Banner*/
    @GET(WebConstants.GET_BANNER)
    Call<JsonElement> getBanner();

    /*Drink Category*/
    @GET(WebConstants.GET_CATEGORY)
    Call<JsonElement> getDrinkCategory();

    /*All Products By Category*/
    @FormUrlEncoded
    @POST(WebConstants.GET_PRODUCTS)
    Call<JsonElement> getCatProduct(@Field("cat_id") String cat_id);

    /*Logout User*/
    @FormUrlEncoded
    @POST(WebConstants.LOGOUT_USER)
    Call<JsonElement> logout_user(@Field("userid") String phone);

    /*Update Profile*/
    @FormUrlEncoded
    @POST(WebConstants.UPDATE_PROFILE)
    Call<JsonElement> updateProfile(@Field("id") String id,@Field("name") String name,@Field("email") String email);

    /*Update Profile IMAGE*/
    @Multipart
    @POST(WebConstants.UPDATE_PROFILE_IMAGE)
    Call<JsonElement> updateProfileImage(@Part MultipartBody.Part file, @Part("id") RequestBody id);

    /*Get Notification*/
    @FormUrlEncoded
    @POST(WebConstants.GET_NOTIFICATION)
    Call<JsonElement> getNotification(@Field("userid") String id);

    /*Update Notification*/
    @FormUrlEncoded
    @POST(WebConstants.UPDATE_NOTIFICATION)
    Call<JsonElement> updateNotification(@Field("id") String id,@Field("userid") String userid);

}
