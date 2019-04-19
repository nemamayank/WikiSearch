package com.mayank.wikisearch.views;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayank.wikisearch.R;
import com.mayank.wikisearch.databinding.GridItemBindings;
import com.mayank.wikisearch.models.GridItemModel;
import com.mayank.wikisearch.utilities.IGridItemClickListener;

import java.util.ArrayList;

/**
 * RecyclerViewAdapter responsible for showing the images into the
 * gridLayout from {@link SearchWikiActivity}
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> mListItems;
    private IGridItemClickListener mIGridItemClickListener;

    /**
     * Reference, Data & Listener is passed into the constructor
     **/
    RecyclerViewAdapter(ArrayList<String> listItems, IGridItemClickListener IGridItemClickListener) {
        this.mListItems = listItems;
        this.mIGridItemClickListener = IGridItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GridItemBindings binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.adapter_grid_item, parent, false);
        return new GridViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!mListItems.get(position).isEmpty()) {
            ((GridViewHolder) holder).mBinding.setGridModel(new GridItemModel(mListItems.get(position)));
        }
    }

    void updateGridList(ArrayList<String> gridList) {
        this.mListItems.clear();
        if (gridList != null || !gridList.isEmpty()) {
            this.mListItems.addAll(gridList);
        }
        notifyDataSetChanged();
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
}