package com.marketingknob.mercury.webservices.interfaces;

import com.google.gson.JsonElement;

import retrofit2.Response;

public interface ApiResponseHelper {

    void onSuccess(Response<JsonElement> response, String typeApi);

    void onFailure(String error);

}
