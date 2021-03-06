package com.codepath.apps.tweeter.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ian_sibner on 4/4/17.
 */
public class User {
    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("screen_name")
    @Expose
    public String screenName;


    @SerializedName("profile_image_url")
    @Expose
    public String profileImageUrl;

    @SerializedName("id")
    @Expose
    public long id;

    @SerializedName("description")
    @Expose
    @Nullable
    public String tagline;

    @SerializedName("followers_count")
    @Expose
    public int followersCount;

    @SerializedName("friends_count")
    @Expose
    public int followingCount;

    public String getFullScreenName() {
        return "@" + screenName;
    }
}
