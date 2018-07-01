package sg.howard.twitterclient.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import sg.howard.twitterclient.R;


import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

public class ImageDialogFragment extends BlurDialogFragment {
    /**
     * Bundle key used to start the blur dialog with a given scale factor (float).
     */
    private static final String BUNDLE_KEY_DOWN_SCALE_FACTOR = "bundle_key_down_scale_factor";

    /**
     * Bundle key used to start the blur dialog with a given blur radius (int).
     */
    private static final String BUNDLE_KEY_BLUR_RADIUS = "bundle_key_blur_radius";

    /**
     * Bundle key used to start the blur dialog with a given dimming effect policy.
     */
    private static final String BUNDLE_KEY_DIMMING = "bundle_key_dimming_effect";

    /**
     * Bundle key used to start the blur dialog with a given debug policy.
     */
    private static final String BUNDLE_KEY_DEBUG = "bundle_key_debug_effect";

    private static final String BUNDLE_IMAGE_URL = "bundle_image_url";

    private int mRadius;
    private float mDownScaleFactor;
    private boolean mDimming;
    private boolean mDebug;
    private String mURL;

    public static ImageDialogFragment newInstance(int radius,
                                                   float downScaleFactor,
                                                   boolean dimming,
                                                   boolean debug,
                                                   String url) {
        ImageDialogFragment fragment = new ImageDialogFragment();
        Bundle args = new Bundle();
        args.putInt( BUNDLE_KEY_BLUR_RADIUS, radius);
        args.putFloat(BUNDLE_KEY_DOWN_SCALE_FACTOR, downScaleFactor);
        args.putBoolean(BUNDLE_KEY_DIMMING, dimming);
        args.putBoolean(BUNDLE_KEY_DEBUG, debug);
        args.putString(BUNDLE_IMAGE_URL, url);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle args = getArguments();
        mRadius = args.getInt(BUNDLE_KEY_BLUR_RADIUS);
        mDownScaleFactor = args.getFloat(BUNDLE_KEY_DOWN_SCALE_FACTOR);
        mDimming = args.getBoolean(BUNDLE_KEY_DIMMING);
        mDebug = args.getBoolean(BUNDLE_KEY_DEBUG);
        mURL = args.getString(BUNDLE_IMAGE_URL);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_image, null);
        ImageView img_large = view.findViewById(R.id.img_large);
        Glide.with(view)
                .load(getURL(mURL))
                .into(img_large);
        builder.setView(view);
        return builder.create();
    }

    private String getURL(String url) {
        String[] s = url.split("_normal");
        if (s.length == 2)
            return s[0] + s[1];
        else return url;
    }

    @Override
    protected boolean isDebugEnable() {
        return mDebug;
    }

    @Override
    protected boolean isDimmingEnable() {
        return mDimming;
    }

    @Override
    protected boolean isActionBarBlurred() {
        return true;
    }

    @Override
    protected float getDownScaleFactor() {
        return mDownScaleFactor;
    }

    @Override
    protected int getBlurRadius() {
        return mRadius;
    }
}
