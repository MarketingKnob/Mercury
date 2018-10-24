package com.marketingknob.mercury.webservices.webresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Akshay on 24/10/2018.
 */

public class BannerResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("images")
    @Expose
    private Images images;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }


    public class Images {

        @SerializedName("banner")
        @Expose
        private List<String> banner = null;

        public List<String> getBanner() {
            return banner;
        }

        public void setBanner(List<String> banner) {
            this.banner = banner;
        }

    }
}