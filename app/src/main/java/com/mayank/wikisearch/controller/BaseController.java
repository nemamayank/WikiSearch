package com.mayank.wikisearch.controller;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.mayank.wikisearch.MyApplication;
import com.mayank.wikisearch.R;
import com.mayank.wikisearch.network.AppError;
import com.mayank.wikisearch.network.NetworkAPI;
import com.mayank.wikisearch.utilities.Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Base class for all the http/https communication. This holds some common code which is being
 * used to communicate with server.
 * The class provides below features:
 * - Check for internet connection.
 * - Initialize Retrofit objects for communication.
 * - Handles the callback of the http/https request and send the callbacks back to the caller sub-class/method.
 * - Gather the information of error; occurred during communication with server.
 */

public abstract class BaseController implements Callback<ResponseBody> {

    private static final String TAG = BaseController.class.getSimpleName();
    private static String BASE_URL="https://en.wikipedia.org/w/";
    private final static long TIMEOUT = 60;
    protected NetworkAPI networkAPI;
    private Class mResponseClass;

    protected BaseController(Class responseClass) {
        mResponseClass = responseClass;
        initializeRetrofit();
    }

    protected void setResponseClass(Class responseClass) {
        mResponseClass = responseClass;
    }

    /*Method to configure Retrofit with header(s)*/
    private void initializeRetrofit() {
        OkHttpClient.Builder builderOkHttp = new OkHttpClient.Builder();
        builderOkHttp.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builderOkHttp.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builderOkHttp.writeTimeout(TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builderOkHttp.networkInterceptors().add(httpLoggingInterceptor);

        builderOkHttp.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient httpClient = builderOkHttp.build();
        networkAPI = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
                .create(NetworkAPI.class);
    }

    /**
     * Checks the initial requirements to make the http/https API call.
     * The method will check below things:
     * 1. Internet connectivity
     * If one of the above condition fails then {@link #onFailure(AppError)} method will be called.
     * This method is responsible to refresh the auth token (if expired). The caller class/method
     * will wait until the refresh API completes
     */
    protected void checkNetworkRequirements() {
        if (!Utils.isOnline(MyApplication.GET_MY_WIKI_APP_INSTANCE())) {
            // No internet Connection, send failure to the caller.
            AppError error = new AppError(AppError.NO_INTERNET, MyApplication.GET_MY_WIKI_APP_INSTANCE().getString(R.string.no_internet_message));
            error.setResponseClass(mResponseClass);
            onFailure(error);
            return;
        }
        onReadyToExecute(mResponseClass);
    }


    @Override
    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
        try {
            if (response.isSuccessful()) {
                String responseString = response.body().string();
                // Uncomment if response trace needed
                /*Log.d(TAG, "response.isSuccessful(): " + responseString);*/

                if (TextUtils.isEmpty(responseString)) {
                    onFailure(getSomethingWrongError());
                    return;
                }

                Gson gson = new Gson();
                onSuccess(gson.fromJson(responseString, mResponseClass));
            } else {

                onFailure(getAppError(response));
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse(): " + e.getMessage(), e);
            onFailure(getSomethingWrongError());
        }
    }

    private AppError getAppError(retrofit2.Response<ResponseBody> response) {
        AppError appError = getSomethingWrongError();
        appError.setErrorCode(Integer.toString(response.code()));
        String message = response.message();
        appError.setResponseClass(mResponseClass);
        return appError;
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        onFailure(getAppError(t));
    }

    /**
     * Called when all requirements of the HTTP action are satisfied.
     */
    protected abstract void onReadyToExecute(Class responseClassName);

    /**
     * Called when success of the API is received from server side.
     *
     * @param response - The response object; this will be up to the caller class/method.
     *                 They will set it up from the constructor of specific class
     */
    protected abstract void onSuccess(Object response);

    /**
     * Called when failure of the API is received from server side.
     *
     * @param error - The {@link AppError} object
     */
    protected abstract void onFailure(AppError error);

    private AppError getAppError(Throwable error) {
        String errorMessage = error.getMessage();
        AppError appError;
        Log.e(TAG, "handleCommonHttpError() : " + errorMessage, error);

        if (errorMessage != null && errorMessage.contains("timeout")) {
            appError = new AppError(AppError.TIMEOUT, MyApplication.GET_MY_WIKI_APP_INSTANCE().getString(R.string.connection_timeout_message));
        } else {
            appError = getSomethingWrongError();
        }

        appError.setResponseClass(mResponseClass);
        return appError;
    }

    protected AppError getSomethingWrongError() {
        AppError error = new AppError(AppError.SOMETHING_WRONG, MyApplication.GET_MY_WIKI_APP_INSTANCE().getString(R.string.something_is_not_working_message));
        error.setResponseClass(mResponseClass);
        return error;
    }
}