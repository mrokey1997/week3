package sg.howard.twitterclient.notification;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

public class NotificationnPresenter implements NotificationContract.Presenter {
    private TwitterApiClient client = null;
    private NotificationContract.View mView;

    public NotificationnPresenter(NotificationContract.View view, TwitterSession session) {
        mView = view;
        mView.setPresenter(this);
        client = new TwitterApiClient(session);
    }

    @Override
    public void start() {
        mView.showLoading(true);

    }
}
