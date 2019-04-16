package com.mayank.wikisearch.views;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.mayank.wikisearch.R;
import com.mayank.wikisearch.controller.GetWikiSearchController;
import com.mayank.wikisearch.databinding.SearchWikiBindings;
import com.mayank.wikisearch.models.responsemodels.WikiSearchModel;
import com.mayank.wikisearch.models.viewmodels.SearchWikiViewModel;
import com.mayank.wikisearch.network.AppError;
import com.mayank.wikisearch.utilities.DialogUtils;
import com.mayank.wikisearch.utilities.IGridItemClickListener;
import com.mayank.wikisearch.utilities.Utils;

/**
 * SearchWikiActivity Class is responsible / features the SEARCH entered by the user,
 * - Communicate to the network layer for queried data
 *   and on success {@link #onGetWikiSearchSuccess} else {@link #onGetWikiSearchFailure(AppError)}
 * - Display the parsed data into GridLayout
 * - Modifies the search if queried again into SearchView {@link #onSearchTextSubmitted(String)}
 * - On Click to any item show's the detail view {@link SearchWikiImageDetails}
 */

public class SearchWikiActivity extends AppCompatActivity implements GetWikiSearchController.GetWikiSearchListener, IGridItemClickListener {
    private static final String TAG = SearchWikiActivity.class.getSimpleName();
    private SearchWikiBindings mBindings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBindings = DataBindingUtil.setContentView(this, R.layout.activity_wiki_search);
        // Initializing bindings
        initializeBindings();
    }

    private void initializeBindings() {
        mBindings.setSearchModel(new SearchWikiViewModel(this, mBindings));
        // Setting up gridLayout
        mBindings.rvImageList.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void onSearchTextSubmitted(String query) {
        callAPI(1000, query.trim());
    }

    public void callAPI(int piThumbSize, String searchText) {
        DialogUtils.showProgressLoader(SearchWikiActivity.this, R.string.loader_loading);
        GetWikiSearchController<WikiSearchModel> getWikiSearchController = new GetWikiSearchController<>(WikiSearchModel.class, this);
        getWikiSearchController.requestWikiSearch(piThumbSize, searchText);
    }

    @Override
    public void onGetWikiSearchSuccess(Object response) {
        DialogUtils.hideLoader();
        /**
         * Checking if list has data
         * Setting the Adapter for parsed image list from server
         **/
        if(mBindings.getSearchModel().getImageList(response).isEmpty()){
            Utils.showFailureMessage(this, getString(R.string.no_records));
        }
        mBindings.rvImageList.setAdapter(new RecyclerViewAdapter(this, mBindings.getSearchModel().getImageList(response), SearchWikiActivity.this));
    }

    @Override
    public void onGetWikiSearchFailure(AppError error) {
        Utils.showNetworkFailureError(this, error);
    }

    @Override
    public void onGridItemClickListener(View view, int position, String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent goDetails = new Intent(SearchWikiActivity.this, SearchWikiImageDetails.class);
            goDetails.putExtra(Utils.Intent.IMAGE_URL, url);
            startActivity(goDetails);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.search_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.hint_search));
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onSearchTextSubmitted(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}