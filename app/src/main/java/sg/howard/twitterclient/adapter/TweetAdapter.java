package sg.howard.twitterclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.twitter.sdk.android.core.models.Tweet;
import com.varunest.sparkbutton.SparkButton;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.util.ParseRelativeDate;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{
    private List<Tweet> tweets;
    private Context context;

    public TweetAdapter(Context context) {
        tweets = new ArrayList<>();
        this.context = context;
    }


    public void setData(List<Tweet> tweets) {
        this.tweets = tweets;
        notifyDataSetChanged();
    }

    public void clearData() {
        tweets.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_rv_tweet, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);

        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.img_avatar);

        holder.tv_name.setText(tweet.user.name);
        holder.tv_screen_name.setText("@" + tweet.user.screenName);
        holder.tv_time.setText("• " + ParseRelativeDate.getShortcutRelativeTimeAgo(tweet.createdAt));
        holder.tv_text.setText(tweet.text);

        Glide.with(context)
                .load(tweet.extendedEntities.media.size() != 0 ? tweet.extendedEntities.media.get(0).mediaUrlHttps : "")
                .into(holder.img_image);

        holder.tv_retweet.setText(tweet.retweetCount+"");

        holder.btn_heart.setOnClickListener(view -> {
            holder.btn_heart.setChecked(!holder.btn_heart.isChecked());
            holder.btn_heart.playAnimation();
        });

        holder.tv_like.setText(tweet.favoriteCount+"");

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_avatar;
        TextView tv_name;
        TextView tv_screen_name;
        TextView tv_time;
        TextView tv_text;
        ImageView img_image;
        TextView tv_retweet;
        SparkButton btn_heart;
        TextView tv_like;

        public ViewHolder(View itemView) {
            super(itemView);
            img_avatar = itemView.findViewById(R.id.img_avatar);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_screen_name = itemView.findViewById(R.id.tv_screen_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_text = itemView.findViewById(R.id.tv_text);
            img_image = itemView.findViewById(R.id.img_image);
            tv_retweet = itemView.findViewById(R.id.tv_retweet);
            btn_heart = itemView.findViewById(R.id.btn_heart);
            tv_like = itemView.findViewById(R.id.tv_like);
        }
    }
}
