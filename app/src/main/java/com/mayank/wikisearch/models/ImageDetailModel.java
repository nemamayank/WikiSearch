package com.mayank.wikisearch.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;

import com.mayank.wikisearch.utilities.Utils;

/**
 * This class holds the data to be manipulate at ScreenWikiImageDetailScreen
 * {@link com.mayank.wikisearch.views.SearchWikiImageDetails}
 */

public class ImageDetailModel extends BaseObservable {
    private String imageUrl;

    public ImageDetailModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(AppCompatImageView imageView, String imageUrl) {
        if ((imageUrl != null) || (!TextUtils.isEmpty(imageUrl))) {
            Utils.showFullScreenImage(imageView.getContext(), imageUrl, imageView);
        } else {
            Utils.showErrorImage(imageView.getContext(), imageView);
        }
    }
}
