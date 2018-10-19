package com.marketingknob.mercury.webservices.interfaces;

import com.google.gson.JsonElement;

import retrofit2.Response;

/**
 * Created by Akshay on 18/10/2018.
 */

public interface ApiResponseHelper {

    void onSuccess(Response<JsonElement> response, String typeApi);

    void onFailure(String error);

}
