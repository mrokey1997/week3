package sg.howard.twitterclient.compose;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.profile.ProfileActivity;
import sg.howard.twitterclient.timeline.TimelineActivity;

public class ComposeTweetActivity extends AppCompatActivity implements ComposeTweetContract.View{
    ImageView btnSend;
    EditText edtCompose;
    ProgressBar loader;
    ComposeTweetContract.Presenter presenter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        btnSend = findViewById(R.id.btnSend);
        edtCompose = findViewById(R.id.edtCompose);
        loader = findViewById(R.id.loader);
        presenter = new ComposeTweetPresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());

        initBottomNavigationView();

        btnSend.setOnClickListener( view -> presenter.sendTweet(edtCompose.getText().toString()));
    }

    private void initBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.item_compose);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.item_home:
                    startActivity(new Intent(this, TimelineActivity.class));
                    break;
                case R.id.item_profile:
                    startActivity(new Intent(this, ProfileActivity.class));
                    break;
            }
            return true;
        });
    }

    @Override
    public void setPresenter(ComposeTweetContract.Presenter presenter) {
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

    @Override
    public void sendTweetSuccess(Result<Tweet> result) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        bottomNavigationView.setSelectedItemId(R.id.item_home);
        finish();
    }
}
