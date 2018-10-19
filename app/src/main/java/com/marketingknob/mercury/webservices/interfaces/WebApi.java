package com.marketingknob.mercury.webservices.interfaces;

import com.google.gson.JsonElement;
import com.marketingknob.mercury.webservices.WebConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Akshay on 18/10/2018.
 */

public interface WebApi {

    @FormUrlEncoded
    @POST(WebConstants.SIGNUP_USER_URL)
    Call<JsonElement> signUpUser(@Field("name") String name,
                                 @Field("phone") String phone, @Field("device_id") String device_id,
                                 @Field("device_type") String device_type, @Field("email") String email);
    @FormUrlEncoded
    @POST(WebConstants.LOGIN_URL)
    Call<JsonElement> login(@Field("email") String email, @Field("password") String password, @Field("user_type") String user_type,
                            @Field("device_type") String device_type, @Field("device_token") String device_token);
    @FormUrlEncoded
    @POST(WebConstants.VERIFY_OTP)
    Call<JsonElement> verifyOtp(@Field("otp") String otp);

}
