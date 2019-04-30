package com.mayank.wikisearch.models;

import com.mayank.wikisearch.utilities.IGridItemClickListener;

/**
 * This class holds the data to be manipulate for adapter items
 * {@link com.mayank.wikisearch.views.RecyclerViewAdapter}
 */

public class GridItemModel {
    private IGridItemClickListener IGridItemClickListener;
    private String itemUrl;
    private int position;

    public GridItemModel(IGridItemClickListener gridItemClickListener, String itemUrl, int position) {
        this.IGridItemClickListener = gridItemClickListener;
        this.itemUrl = itemUrl;
        this.position = position;
    }

    public IGridItemClickListener getIGridItemClickListener() {
        return IGridItemClickListener;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public int getPosition() {
        return position;
    }
}
