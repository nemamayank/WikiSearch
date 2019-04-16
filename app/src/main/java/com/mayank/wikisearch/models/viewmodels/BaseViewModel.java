package com.mayank.wikisearch.models.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ViewDataBinding;

/**
 *
 */

public class BaseViewModel<B extends ViewDataBinding> extends BaseObservable {
    private B binding;
    private Context context;

    public BaseViewModel(Context context, B binding) {
        this.context = context;
        this.binding = binding;
    }

    public B getBinding() {
        return binding;
    }

    public Context getContext() {
        return context;
    }
}
