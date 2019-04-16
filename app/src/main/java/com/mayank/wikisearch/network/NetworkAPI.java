package com.mayank.wikisearch.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Declare all network API communication methods here.
 * This class will contains all Interfaces which defines the possible
 * HTTP operations of the application
 */

public interface NetworkAPI {

    @GET("api.php?action=query&prop=pageimages&format=json&piprop=thumbnail&pilimit=100&generator=prefixsearch")
    Call<ResponseBody> getWikiSearchData(@Query("pithumbsize") int pithumbsize,
                                         @Query("gpssearch") String gpssearch);

}
