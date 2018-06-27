package sg.howard.twitterclient.notification;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import sg.howard.twitterclient.R;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView rvNotificationn;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_notification);

        rvNotificationn = findViewById(R.id.rvNotification);
    }
}
