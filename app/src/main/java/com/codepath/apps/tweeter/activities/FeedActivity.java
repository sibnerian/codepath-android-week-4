package com.codepath.apps.tweeter.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.fragments.ComposeTweetDialogFragment;
import com.codepath.apps.tweeter.fragments.MentionsFragment;
import com.codepath.apps.tweeter.fragments.TimelineFragment;
import com.codepath.apps.tweeter.models.Tweet;

public class FeedActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupTabs();
    }

    private void setupTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), FeedActivity.this));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void composeTweet(MenuItem mi) {
        final FragmentManager fm = getSupportFragmentManager();
        final ComposeTweetDialogFragment frag = ComposeTweetDialogFragment.newInstance();
        frag.setListener(new ComposeTweetDialogFragment.ComposeTweetDialogListener() {
            @Override
            public void onPostTweet(Tweet tweet) {
                frag.dismiss();
                // TODO
//                tweets.add(0, tweet);
//                tweetsAdapter.notifyItemInserted(0);
            }
        });
        frag.show(fm, "fragment_compose_tweet");
    }


    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[] { "Home", "Mentions" };
        private Context context;

        public TweetsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new TimelineFragment();
            } else if (position == 1) {
                return new MentionsFragment();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }
}
