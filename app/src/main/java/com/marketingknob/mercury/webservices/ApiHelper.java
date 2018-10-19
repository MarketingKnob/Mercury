package com.marketingknob.mercury.webservices;

import android.util.Log;
import com.google.gson.JsonElement;
import com.marketingknob.mercury.webservices.interfaces.ApiResponseHelper;
import com.marketingknob.mercury.webservices.interfaces.WebApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiHelper {

    private ApiResponseHelper apiResponseHelper;

    //Login Otp
    public void login(String email, String password, String userType, final ApiResponseHelper apiResponseHelper) {

        this.apiResponseHelper = apiResponseHelper;
        Call<JsonElement> call = ApiClient.getClient().create(WebApi.class).login(email, password, userType, "android", "");

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                apiResponseHelper.onSuccess(response, "login");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("Login_Exp", "//" + t.getMessage());
                apiResponseHelper.onFailure(t.getMessage());
            }
        });
    }

    //Sign Up User
    public void signUpUser(String name,String phone,String deviceId,String email,
                           final ApiResponseHelper apiResponseHelper) {

        this.apiResponseHelper = apiResponseHelper;
        Call<JsonElement> call = ApiClient.getClient().create(WebApi.class).signUpUser(name,phone,deviceId,"android", email);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                apiResponseHelper.onSuccess(response, "signup");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("Login_Exp", "//" + t.getMessage());
                apiResponseHelper.onFailure(t.getMessage());
            }
        });
    }

    //Sign Up User
    public void verifyOtp(String otp,
                           final ApiResponseHelper apiResponseHelper) {

        this.apiResponseHelper = apiResponseHelper;
        Call<JsonElement> call = ApiClient.getClient().create(WebApi.class).verifyOtp(otp);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                apiResponseHelper.onSuccess(response, "verifyOTP");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("Login_Exp", "//" + t.getMessage());
                apiResponseHelper.onFailure(t.getMessage());
            }
        });
    }

}
