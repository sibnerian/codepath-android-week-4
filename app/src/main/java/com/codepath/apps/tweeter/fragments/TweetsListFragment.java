package com.codepath.apps.tweeter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.adapters.TweetsAdapter;
import com.codepath.apps.tweeter.models.Tweet;
import com.codepath.apps.tweeter.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

public abstract class TweetsListFragment extends Fragment {
    ArrayList<Tweet> tweets = new ArrayList<>();
    TweetsAdapter tweetsAdapter;
    RecyclerView recyclerView;
    ImageView loadingIndicator;
    EndlessRecyclerViewScrollListener scrollListener;
    View containerview;

    // inflation logic
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        containerview = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        setupViews();
        populateTimeline();
        return containerview;
    }


    // creation lifecycle event
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setupViews() {
        tweetsAdapter = new TweetsAdapter(getActivity(), tweets);
        loadingIndicator = (ImageView) containerview.findViewById(R.id.loadingIndicator);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) containerview.findViewById(R.id.rvTweets);
        recyclerView.setAdapter(tweetsAdapter);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
//        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
//                new ItemClickSupport.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                        launchArticleWebView(position);
//                    }
//                }
//        );
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getNextTweets();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

    }

    protected void populateTimeline() {
        if (!tweets.isEmpty()) {
            clearTimeline();
        }
        getNextTweets();
    }

    protected void clearTimeline() {
        tweets.clear();
        tweetsAdapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    protected void showLoadingIndicator() {
        loadingIndicator.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(R.raw.loading_dots)
                .into(new GlideDrawableImageViewTarget(loadingIndicator));
    }
    protected void hideLoadingIndicator() { loadingIndicator.setVisibility(View.GONE); }


    abstract void getNextTweets();
}
