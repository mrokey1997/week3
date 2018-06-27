package sg.howard.twitterclient.notification;

import sg.howard.twitterclient.base.BasePresenter;
import sg.howard.twitterclient.base.BaseView;

public interface NotificationContract {
    interface View extends BaseView<Presenter> {
        void onGetStatusesSuccess();
    }

    interface Presenter extends BasePresenter {

    }
}
