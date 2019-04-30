package com.mayank.wikisearch.models.viewmodels;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;

import com.mayank.wikisearch.databinding.SearchWikiBindings;
import com.mayank.wikisearch.models.responsemodels.PageDetails;
import com.mayank.wikisearch.models.responsemodels.WikiSearchModel;
import com.mayank.wikisearch.utilities.Utils;

import java.util.ArrayList;
import java.util.Map;

/**
 * ViewModel holding the business logic & returning the data
 * Also holds the adapter binding for loading images from url
 * {@link com.mayank.wikisearch.views.SearchWikiActivity}
 */

public class SearchWikiViewModel extends BaseViewModel<SearchWikiBindings> {

    public SearchWikiViewModel(Context context, SearchWikiBindings binding) {
        super(context, binding);
    }

    public ArrayList<String> getImageList(Object response) {
        final WikiSearchModel wikiSearchModel = (WikiSearchModel) response;
        ArrayList<String> mListImages = new ArrayList<>();
        if (wikiSearchModel.getQuery() != null) {
            if (wikiSearchModel.getQuery().getPageDetailsMap() != null) {
                Map<String, PageDetails> pageDetails = wikiSearchModel.getQuery().getPageDetailsMap();
                for (Map.Entry<String, PageDetails> entry : pageDetails.entrySet()) {
                    if (entry.getValue().getThumbnail() != null) {
                        mListImages.add(entry.getValue().getThumbnail().getSource());
                    }
                }
            }
        }
        return mListImages;
    }

    /**
     * Binds the data to the {@link android.support.v7.widget.RecyclerView.Adapter}, sets the
     * recycler view on the adapter, and performs some other recycler view initialization.
     *
     * @param imageView passed in automatically with the data binding
     * @param itemUrl   passed for image url
     */
    @BindingAdapter("bind:itemUrl")
    public static void loadAdapterImage(AppCompatImageView imageView, String itemUrl) {
        if ((itemUrl != null) || (!TextUtils.isEmpty(itemUrl))) {
            Utils.showAdapterImageUsingGlide(imageView.getContext(), itemUrl, imageView);
        } else {
            Utils.showErrorImage(imageView.getContext(), imageView);
        }
    }

    @BindingAdapter("query")
    public static void setQuery(SearchView searchView, String queryText) {
        searchView.setQuery(queryText, false);
    }

    @BindingAdapter("queryTextListener")
    public static void setOnQueryTextListener(SearchView searchView, SearchView.OnQueryTextListener listener) {
        searchView.setOnQueryTextListener(listener);
    }
}