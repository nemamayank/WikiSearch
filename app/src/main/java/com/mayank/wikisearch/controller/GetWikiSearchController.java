package com.mayank.wikisearch.controller;

import android.text.TextUtils;
import android.util.Log;

import com.mayank.wikisearch.models.responsemodels.WikiSearchModel;
import com.mayank.wikisearch.network.AppError;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 *  Controller for handling the Search API
 * {@link com.mayank.wikisearch.views.SearchWikiActivity}
 */

public class GetWikiSearchController<T> extends BaseController {
    private final String TAG = GetWikiSearchController.class.getCanonicalName();
    private GetWikiSearchListener mWikiSearchListener;
    private Class responseClass;
    private String mGpsSearch;
    private int mPiThumbsize;

    public interface GetWikiSearchListener {
        void onGetWikiSearchSuccess(final Object response);

        void onGetWikiSearchFailure(final AppError error);
    }

    public GetWikiSearchController(final Class<T> tClass, GetWikiSearchListener wikiSearchListener) {
        super(tClass);
        this.responseClass = tClass;
        this.mWikiSearchListener = wikiSearchListener;
    }

    @Override
    protected void onReadyToExecute(Class responseClassName) {
        Call<ResponseBody> call = networkAPI.getWikiSearchData(mPiThumbsize, mGpsSearch);
        call.enqueue(this);
    }

    @Override
    protected void onSuccess(Object response) {
        /*
        * Here we can validate the response according to the business logic
        * */
        if (response == null) {
            mWikiSearchListener.onGetWikiSearchFailure(getSomethingWrongError());
            return;
        }

        /* Casting the parsed object to response model class */
        final WikiSearchModel wikiSearchModel = (WikiSearchModel) response;

        /**
         *  Checking if WikiSearchModel is NULL or not
         * If NULL then, returning with {@link #getSomethingWrongError}
         */
        if (wikiSearchModel == null) {
            mWikiSearchListener.onGetWikiSearchFailure(getSomethingWrongError());
            return;
        }

        mWikiSearchListener.onGetWikiSearchSuccess(wikiSearchModel);

    }

    @Override
    protected void onFailure(AppError error) {
        mWikiSearchListener.onGetWikiSearchFailure(error);
    }

    public void requestWikiSearch(int pithumbsize, String gpssearch) {
        this.mGpsSearch = gpssearch;
        this.mPiThumbsize = pithumbsize;

        if ((!TextUtils.isEmpty(gpssearch)) && (pithumbsize != 0))
            checkNetworkRequirements();
        else
            Log.e(TAG, "Invalid Parameters!");
    }
}