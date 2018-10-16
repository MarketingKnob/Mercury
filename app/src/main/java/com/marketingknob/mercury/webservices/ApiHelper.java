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

    public void signUpUser(String firstName, String lastName, String email, String password, String phone, String userType,
                           final ApiResponseHelper apiResponseHelper) {

        this.apiResponseHelper = apiResponseHelper;
        Call<JsonElement> call = ApiClient.getClient().create(WebApi.class).signUpUser(firstName+" "+lastName, email, password, phone, userType,"android", "");

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                apiResponseHelper.onSuccess(response, "signup_user");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("Login_Exp", "//" + t.getMessage());
                apiResponseHelper.onFailure(t.getMessage());
            }
        });
    }

}
