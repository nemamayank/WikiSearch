package com.mayank.wikisearch.models;

/**
 * This class holds the data to be manipulate for adapter items
 * {@link com.mayank.wikisearch.views.RecyclerViewAdapter}
 */

public class GridItemModel {

    private String itemUrl;

    public GridItemModel(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
