package sg.howard.twitterclient.compose;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.models.Tweet;

import sg.howard.twitterclient.base.BasePresenter;
import sg.howard.twitterclient.base.BaseView;

public interface ComposeTweetContract {
    interface View extends BaseView<Presenter>{

        void sendTweetSuccess(Result<Tweet> result);
    }

    interface Presenter extends BasePresenter{

        void sendTweet(String text);
    }
}
