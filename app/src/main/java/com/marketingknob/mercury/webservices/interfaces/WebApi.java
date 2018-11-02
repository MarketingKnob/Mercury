package com.marketingknob.mercury.webservices.interfaces;

import com.google.gson.JsonElement;
import com.marketingknob.mercury.webservices.WebConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Akshay on 18/10/2018.
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
    Call<JsonElement> verifyOtp(@Field("otp") String otp,@Field("user_id") String userid);

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
    Call<JsonElement> logout_user(@Field("phone") String phone);


}
