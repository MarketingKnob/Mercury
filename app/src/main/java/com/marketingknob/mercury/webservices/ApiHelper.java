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

    /*/Login Otp/*/
    public void login(String phone, final ApiResponseHelper apiResponseHelper) {

        this.apiResponseHelper = apiResponseHelper;
        Call<JsonElement> call = ApiClient.getClient().create(WebApi.class).login_user(phone);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                apiResponseHelper.onSuccess(response, "login_user");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("Login_Exp", "//" + t.getMessage());
                apiResponseHelper.onFailure(t.getMessage());
            }
        });
    }

    /*Sign Up User*/
    public void signUpUser(String name,String phone,String deviceId,String email,String gender,
                           final ApiResponseHelper apiResponseHelper) {

        this.apiResponseHelper = apiResponseHelper;
        Call<JsonElement> call = ApiClient.getClient().create(WebApi.class).signUpUser(name,phone,deviceId,"android", email,gender);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                apiResponseHelper.onSuccess(response, "signup");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                apiResponseHelper.onFailure(t.getMessage());
            }
        });
    }

     /*Verify Otp*/
    public void verifyOtp(String otp, String userid,final ApiResponseHelper apiResponseHelper) {

        this.apiResponseHelper = apiResponseHelper;
        Call<JsonElement> call = ApiClient.getClient().create(WebApi.class).verifyOtp(otp,userid);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                apiResponseHelper.onSuccess(response, "verifyOTP");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                apiResponseHelper.onFailure(t.getMessage());
            }
        });
    }

    /*Get BANNER*/
    public void getBanner(final ApiResponseHelper apiResponseHelper) {

        this.apiResponseHelper = apiResponseHelper;
        Call<JsonElement> call = ApiClient.getClient().create(WebApi.class).getBanner();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                apiResponseHelper.onSuccess(response, "Banner");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                apiResponseHelper.onFailure(t.getMessage());
            }
        });
    }

  /*Get Drink Category*/
    public void getDrinkCategory(final ApiResponseHelper apiResponseHelper) {

        this.apiResponseHelper = apiResponseHelper;
        Call<JsonElement> call = ApiClient.getClient().create(WebApi.class).getDrinkCategory();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                apiResponseHelper.onSuccess(response, "DrinkCategory");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                apiResponseHelper.onFailure(t.getMessage());
            }
        });
    }

    /*Get All Product Category Wise*/
    public void getCatProducts(final ApiResponseHelper apiResponseHelper,String strCateId) {

        this.apiResponseHelper = apiResponseHelper;
        Call<JsonElement> call = ApiClient.getClient().create(WebApi.class).getCatProduct(strCateId);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                apiResponseHelper.onSuccess(response, "CategoryProducts");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                apiResponseHelper.onFailure(t.getMessage());
            }
        });
    }

    /*/LogOut User/*/
    public void logout( final ApiResponseHelper apiResponseHelper,String phone) {

        this.apiResponseHelper = apiResponseHelper;
        Call<JsonElement> call = ApiClient.getClient().create(WebApi.class).logout_user(phone);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                apiResponseHelper.onSuccess(response, "logout");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("Login_Exp", "//" + t.getMessage());
                apiResponseHelper.onFailure(t.getMessage());
            }
        });
    }

}
