package sg.howard.twitterclient.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.adapter.TweetAdapter;
import sg.howard.twitterclient.compose.ComposeTweetActivity;
import sg.howard.twitterclient.util.EndlessRecyclerViewScrollListener;
import sg.howard.twitterclient.util.ParseRelativeDate;

public class UserProfileActivity extends AppCompatActivity implements ProfileContract.View {
    RecyclerView rvUserProfile;
    ProgressBar loader;
    TweetAdapter tweetAdapter;
    BottomNavigationView bottomNavigationView;
    ProfileContract.Presenter presenter;
    ImageView img_cover;
    ImageView img_avatar_profile;
    Toolbar toolbar;
    TextView tv_name_profile;
    TextView tv_screen_name_profile;
    TextView tv_description_profile;
    TextView tv_location_profile;
    TextView tv_link_profile;
    TextView tv_calender_profile;
    TextView tv_nFollowing;
    TextView tv_nFollower;
    SwipeRefreshLayout swipeRefreshLayout;
    EndlessRecyclerViewScrollListener scrollListener;
    long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        loader = findViewById(R.id.loader);
        presenter = new ProfilePresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());

        initRecyclerView();

        initBottomNavigationView();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        img_cover = findViewById(R.id.img_cover);
        img_avatar_profile = findViewById(R.id.img_avatar_profile);
        tv_name_profile = findViewById(R.id.tv_name_profile);
        tv_screen_name_profile = findViewById(R.id.tv_screen_name_profile);
        tv_description_profile = findViewById(R.id.tv_description_profile);
        tv_location_profile = findViewById(R.id.tv_location_profile);
        tv_link_profile = findViewById(R.id.tv_link_profile);
        tv_calender_profile = findViewById(R.id.tv_calender_profile);
        tv_nFollowing = findViewById(R.id.tv_nFollowing);
        tv_nFollower = findViewById(R.id.tv_nFollower);

        initSwipeRefreshLayout();

        userId = getIntent().getLongExtra("userId", 1111111111);
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            presenter.startUser(10, userId);
            scrollListener.resetState();
        });
    }

    private void initBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.item_home);
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

    @Override
    protected void onResume() {
        super.onResume();
        presenter.startUser(10, userId);
    }

    private void initRecyclerView() {
        rvUserProfile = findViewById(R.id.rvProfile);
        tweetAdapter = new TweetAdapter(this);
        rvUserProfile.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvUserProfile.setLayoutManager(layoutManager);
        rvUserProfile.setAdapter(tweetAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                int count = (page + 1)*10;
                presenter.startUser(count, userId);
            }
        };

        rvUserProfile.addOnScrollListener(scrollListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onGetStatusesSuccess(List<Tweet> data) {
        tweetAdapter.setData(data);
        Tweet tweet = data.get(0);
        Glide.with(this)
                .load(tweet.user.profileBannerUrl)
                .into(img_cover);
        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .into(img_avatar_profile);
        tv_name_profile.setText(tweet.user.name);
        tv_screen_name_profile.setText("@" + tweet.user.screenName);
        tv_description_profile.setText(tweet.user.description);
        tv_location_profile.setText(tweet.user.location);
        tv_link_profile.setText(tweet.user.entities.url.urls.get(0).displayUrl);
        tv_calender_profile.setText(ParseRelativeDate.getJoinTweetDate(tweet.user.createdAt));
        tv_nFollowing.setText(tweet.user.friendsCount+"");
        tv_nFollower.setText(tweet.user.followersCount+"");
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        loader.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
