package sg.howard.twitterclient.timeline;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.adapter.TweetAdapter;
import sg.howard.twitterclient.compose.ComposeTweetActivity;
import sg.howard.twitterclient.profile.ProfileActivity;
import sg.howard.twitterclient.util.EndlessRecyclerViewScrollListener;

public class TimelineActivity extends AppCompatActivity implements TimelineContract.View {
    private static String TAG = TimelineActivity.class.getSimpleName();
    RecyclerView rvTimeline;
    ProgressBar loader;
    TimelineContract.Presenter presenter;
    TweetAdapter tweetAdapter;
    BottomNavigationView bottomNavigationView;
    SwipeRefreshLayout swipeRefreshLayout;
    EndlessRecyclerViewScrollListener scrollListener;
    List<Tweet> list_tweet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        loader = findViewById(R.id.loader);
        presenter = new TimelinePresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());

        list_tweet = new ArrayList<>();

        initRecyclerView();
        initSwipeRefreshLayout();
        initBottomNavigationView();
    }

    private void initBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.item_compose:
                    startActivity(new Intent(this, ComposeTweetActivity.class));
                    break;
                case R.id.item_profile:
                    startActivity(new Intent(this, ProfileActivity.class));
                    break;
            }
            return true;
        });
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
                swipeRefreshLayout.setRefreshing(false);
                presenter.start(10);
                scrollListener.resetState();
        });
    }

    private void initRecyclerView() {
        rvTimeline = findViewById(R.id.rvTimeline);
        tweetAdapter = new TweetAdapter(this);
        rvTimeline.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTimeline.setLayoutManager(layoutManager);
        rvTimeline.setAdapter(tweetAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                int count = (page + 1)*10;
                presenter.start(count);
            }
        };

        rvTimeline.addOnScrollListener(scrollListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.item_home);
        if(tweetAdapter.getItemCount() == 0)
            presenter.start(10);
        else tweetAdapter.setData(list_tweet);
    }

    @Override
    public void setPresenter(TimelineContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        loader.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onGetStatusesSuccess(List<Tweet> data) {
        list_tweet = data;
        tweetAdapter.setData(data);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
