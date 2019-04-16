package com.mayank.wikisearch.utilities;

import android.view.View;

/**
 * This listener is responsible for handling the itemClick event in adapter
 * and give the callback to the implementing class:
 * 1. {@link com.mayank.wikisearch.views.SearchWikiActivity}
 *
 */

public interface IGridItemClickListener {
    void onGridItemClickListener(View view, int position, String imageUrl);
}
