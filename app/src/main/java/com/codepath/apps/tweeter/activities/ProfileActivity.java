package com.codepath.apps.tweeter.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.TweeterApplication;
import com.codepath.apps.tweeter.fragments.UserTimelineFragment;
import com.codepath.apps.tweeter.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {
    ImageView profilePicture;
    TextView screenNameView;
    TextView tagline;
    TextView followers;
    TextView following;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // passed in from launching activity
        String screenName = getIntent().getStringExtra("screenName");
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        profilePicture = (ImageView) findViewById(R.id.profilePicture);
        screenNameView = (TextView) findViewById(R.id.screenName);
        tagline = (TextView) findViewById(R.id.tagline);
        followers = (TextView) findViewById(R.id.followers);
        following = (TextView) findViewById(R.id.following);

        loadUserProfileInfo(screenName);
        if (screenName != null) {
            setScreenName(screenName);
        }

        
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.userTimelineFragmentContainer, userTimelineFragment);
        ft.commit();
    }

    private void loadUserProfileInfo(String screenName) {
        TweeterApplication.getRestClient().getUserInfo(screenName, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                throwable.printStackTrace();
                // TODO: toast or snack bar
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new GsonBuilder().create();
                User user = gson.fromJson(responseString, User.class);
                loadProfilePicImage(user.profileImageUrl);
                setScreenName(user.screenName);
                tagline.setText(user.tagline);
                followers.setText(user.followersCount + " followers");
                following.setText(user.followingCount + " following");
            }
        });
    }

    private void loadProfilePicImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(new ColorDrawable(this.getResources().getColor(R.color.imageLoadingBackground)))
                .centerCrop()
                .bitmapTransform(new RoundedCornersTransformation(this, 5, 0))
                .into(profilePicture);
    }

    private void setScreenName(String screenName) {
        toolbar.setTitle("@" + screenName);
        screenNameView.setText("@" + screenName);
    }
}
