package com.example.media_lesson;

import com.google.gson.annotations.SerializedName;

public class ImageModel {
    @SerializedName("urls")
    ImageUrls imageUrls;

    public String getRegular() {
        if(imageUrls == null) {
            return null;
        }
        return imageUrls.regularUrl;
    }
}
