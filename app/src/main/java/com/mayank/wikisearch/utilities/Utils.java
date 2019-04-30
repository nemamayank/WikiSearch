package com.mayank.wikisearch.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mayank.wikisearch.R;
import com.mayank.wikisearch.network.AppError;

/**
 * - This class contains all the method(s) commonly used
 * across the application
 * - Also includes constant variables used across the app
 */
public class Utils {

    private static String TAG = Utils.class.getSimpleName();

    public static class ViewType {
        public static final int TYPE_ITEM = 0;
        public static final int TYPE_LOADER = 1;
    }

    public static class Intent {
        public static final String IMAGE_URL = "imageUrl";
    }

    /**
     * Method to check Internet connectivity
     *
     * @return true if connection is available, false otherwise.
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
        } else {
            return false;
        }
    }

    /**
     * Method to get Aspect Width According to screen size
     *
     * @return half width of actual size of device.
     **/
    public static int getAspectWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width / 2;
    }

    /**
     * Using Glide Library Showing the Image on the grid items
     * Here to maintain aspect ratio of IMAGE screen width is taken and set to view
     **/
    public static void showAdapterImageUsingGlide(Context context, String imageURL, AppCompatImageView imageView) {
        Glide.with(context)
                .load(imageURL)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.fitCenterTransform().override((getAspectWidth(context)))
                        .error(R.drawable.ic_loading_progress)
                        .placeholder(R.drawable.ic_loading_progress))
                .into(imageView)
                .onLoadFailed(context.getResources().getDrawable(R.drawable.ic_loading_progress));
    }

    /**
     * Showing Error Image if not found
     **/
    public static void showErrorImage(Context context, AppCompatImageView imageView) {
        Glide.with(context).load(R.drawable.ic_loading_progress)
                .apply(RequestOptions.centerCropTransform()
                        .error(R.drawable.ic_error)
                        .placeholder(R.drawable.ic_loading_progress))
                .into(imageView);
    }

    /**
     * Using Glide Library Showing the Image on the grid items
     * Here to maintain aspect ratio of IMAGE screen width is taken and set to view
     **/
    public static void showFullScreenImage(Context context, String imageURL, AppCompatImageView imageView) {
        Glide.with(context)
                .load(imageURL)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.fitCenterTransform()
                        .error(R.drawable.ic_loading_progress)
                        .placeholder(R.drawable.ic_loading_progress))
                .into(imageView)
                .onLoadFailed(context.getResources().getDrawable(R.drawable.ic_loading_progress));
    }

    /**
     * Showing network failure error in dialog
     **/
    public static void showNetworkFailureError(Activity activity, AppError error) {
        Log.d(TAG, error.getErrorMessage());
        DialogUtils.hideLoader();
        DialogUtils.showAlertDialog(activity, error.getErrorMessage(), R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    /**
     * Showing failure dialog with custom message
     **/
    public static void showFailureMessage(Activity activity, String message) {
        Log.d(TAG, message);
        DialogUtils.hideLoader();
        DialogUtils.showAlertDialog(activity, message, R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    /**
     * Showing failure dialog with custom message
     **/
    public static void setEmptyView(View view, boolean setView) {
        if (setView) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }
}
