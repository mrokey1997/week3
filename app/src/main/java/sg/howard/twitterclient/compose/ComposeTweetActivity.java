package sg.howard.twitterclient.compose;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    View parent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        btnSend = findViewById(R.id.btnSend);
        edtCompose = findViewById(R.id.edtCompose);
        loader = findViewById(R.id.loader);
        parent = findViewById(R.id.parent);
        presenter = new ComposeTweetPresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());

        initBottomNavigationView();

        btnSend.setOnClickListener( view -> presenter.sendTweet(edtCompose.getText().toString()));
    }

    @Override
    public void onBackPressed() {
        exitReveal();
    }

    private void exitReveal() {
        int startRadius = Math.max(parent.getWidth(), parent.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(parent, parent.getWidth()/2, parent.getHeight()/2, startRadius, 0);
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                parent.setVisibility(View.INVISIBLE);
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        animator.start();
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
        exitReveal();
    }
}
