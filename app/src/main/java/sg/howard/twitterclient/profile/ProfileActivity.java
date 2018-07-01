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
import sg.howard.twitterclient.fragment.ImageDialogFragment;
import sg.howard.twitterclient.timeline.TimelineActivity;
import sg.howard.twitterclient.util.EndlessRecyclerViewScrollListener;
import sg.howard.twitterclient.util.ParseRelativeDate;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {
    RecyclerView rvProfile;
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
    String URL_AVATAR = "";
    String URL_COVER = "";

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

        onClick();
    }

    private void onClick() {
        img_cover.setOnClickListener(view -> {
            ImageDialogFragment fragment = ImageDialogFragment.newInstance(
                    7, 7.0f, false, false, URL_COVER);
            fragment.show(getFragmentManager(), "blur_backgroud");
        });
        img_avatar_profile.setOnClickListener(view -> {
            ImageDialogFragment fragment = ImageDialogFragment.newInstance(
                    7, 7.0f, false, false, URL_AVATAR);
            fragment.show(getFragmentManager(), "blur_backgroud");
        });
    }

    @Override
    protected void onResume() {
        bottomNavigationView.setSelectedItemId(R.id.item_profile);
        super.onResume();
        presenter.start(10);
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            presenter.start(10);
            scrollListener.resetState();
        });
    }

    private void initBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.item_compose:
                    startActivity(new Intent(this, ComposeTweetActivity.class));
                    break;
                case R.id.item_home:
                    startActivity(new Intent(this, TimelineActivity.class));
                    break;
            }
            return true;
        });
    }

    private void initRecyclerView() {
        rvProfile = findViewById(R.id.rvProfile);
        tweetAdapter = new TweetAdapter(this);
        rvProfile.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvProfile.setLayoutManager(layoutManager);
        rvProfile.setAdapter(tweetAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                int count = (page + 1)*10;
                presenter.start(count);
            }
        };

        rvProfile.addOnScrollListener(scrollListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onGetStatusesSuccess(List<Tweet> data) {
        tweetAdapter.setData(data);
        Tweet tweet = data.get(0);
        URL_COVER = tweet.user.profileBannerUrl;
        Glide.with(this)
                .load(URL_COVER)
                .into(img_cover);
        URL_AVATAR = tweet.user.profileImageUrl;
        Glide.with(this)
                .load(URL_AVATAR)
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
