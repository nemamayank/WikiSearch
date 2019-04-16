package com.mayank.wikisearch.models.viewmodels;

import android.content.Context;

import com.mayank.wikisearch.databinding.SearchWikiBindings;
import com.mayank.wikisearch.models.responsemodels.PageDetails;
import com.mayank.wikisearch.models.responsemodels.WikiSearchModel;

import java.util.ArrayList;
import java.util.Map;

/**
 *  ViewModel holding the business logic & returning the data
 * {@link com.mayank.wikisearch.views.SearchWikiActivity}
 */

public class SearchWikiViewModel extends BaseViewModel<SearchWikiBindings> {

    public SearchWikiViewModel(Context context, SearchWikiBindings binding) {
        super(context, binding);
    }

    public ArrayList<String> getImageList(Object response){
        final WikiSearchModel wikiSearchModel = (WikiSearchModel) response;
        ArrayList<String> mListImages = new ArrayList<>();
        Map<String, PageDetails> pageDetails = wikiSearchModel.getQuery().getPageDetailsMap();
        for (Map.Entry<String, PageDetails> entry : pageDetails.entrySet()) {
            if (entry.getValue().getThumbnail() != null) {
                mListImages.add(entry.getValue().getThumbnail().getSource());
            }
        }
        return mListImages;
    }
}

