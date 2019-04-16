package com.mayank.wikisearch.views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayank.wikisearch.R;
import com.mayank.wikisearch.databinding.GridItemBindings;
import com.mayank.wikisearch.databinding.ProgressBinding;
import com.mayank.wikisearch.utilities.IGridItemClickListener;
import com.mayank.wikisearch.utilities.Utils;

import java.util.ArrayList;

/**
 * RecyclerViewAdapter responsible for showing the images into the
 * gridLayout from {@link SearchWikiActivity}
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mListItems;
    private IGridItemClickListener mIGridItemClickListener;

    /**
     *  Reference, Data & Listener is passed into the constructor
     **/
    RecyclerViewAdapter(Context context, ArrayList<String> listItems, IGridItemClickListener IGridItemClickListener) {
        this.mContext = context;
        this.mListItems = listItems;
        this.mIGridItemClickListener = IGridItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case Utils.ViewType.TYPE_ITEM: {
                GridItemBindings binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.adapter_grid_item, parent, false);
                return new GridViewHolder(binding);
            }

            case Utils.ViewType.TYPE_LOADER: {
                ProgressBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.adapter_progress_loader, parent, false);
                return new ProgressViewHolder(binding.getRoot());
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case Utils.ViewType.TYPE_ITEM: {
                if (!mListItems.get(position).isEmpty()) {

                    Utils.showAdapterImageUsingGlide(mContext, mListItems.get(position), ((GridViewHolder) holder).mBinding.ivGridImageItem);

                } else {

                    Utils.showErrorImage(mContext, ((GridViewHolder) holder).mBinding.ivGridImageItem);
                }
            }
            break;
        }
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    /**
     * Assign grid item bindings to the ViewHolder (Excluding boiler plate dependency)
     **/
    private class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final GridItemBindings mBinding;

        private GridViewHolder(GridItemBindings binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            this.mBinding.setHandler(this);
        }

        @Override
        public void onClick(View view) {
            if (mIGridItemClickListener != null)
                mIGridItemClickListener.onGridItemClickListener(view, getAdapterPosition(), mListItems.get(getAdapterPosition()));
        }
    }

    /**
     * Assign progress dialog bindings to the ViewHolder
     **/
    private class ProgressViewHolder extends RecyclerView.ViewHolder {
        private ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
}