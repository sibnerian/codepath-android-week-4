package com.codepath.apps.tweeter.fragments;

import com.codepath.apps.tweeter.TweeterApplication;
import com.codepath.apps.tweeter.models.Tweet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class TimelineFragment extends TweetsListFragment {
    @Override
    void getNextTweets() {
        showLoadingIndicator();
        long lastId = tweets.isEmpty() ? -1 : tweets.get(tweets.size() - 1).id;
        TweeterApplication.getRestClient().getHomeTimeline(lastId, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                throwable.printStackTrace();
                // TODO: toast or snack bar
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new GsonBuilder().create();
                Tweet[] tweetsArr = gson.fromJson(responseString, Tweet[].class);
                int curSize = tweetsAdapter.getItemCount();
                tweets.addAll(Arrays.asList(tweetsArr));
                tweetsAdapter.notifyItemRangeInserted(curSize, tweetsArr.length);
                hideLoadingIndicator();
            }
        });
    }

    public void manuallyInsertTweet(Tweet tweet) {
        tweets.add(0, tweet);
        tweetsAdapter.notifyDataSetChanged();
    }
}
