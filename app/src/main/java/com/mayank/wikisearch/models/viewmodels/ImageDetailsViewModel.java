package com.mayank.wikisearch.models.viewmodels;

import android.content.Context;

import com.mayank.wikisearch.databinding.WikiImageDetailsBindings;

/**
 * This class holds the business logic for details screen
 * intentionally kept Blank as now no data / logic contains on these screen
 * having callback at {@link com.mayank.wikisearch.views.SearchWikiImageDetails}
 */

public class ImageDetailsViewModel extends BaseViewModel<WikiImageDetailsBindings> {

    public ImageDetailsViewModel(Context context, WikiImageDetailsBindings binding) {
        super(context, binding);
    }
}
