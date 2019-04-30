package com.mayank.wikisearch.views;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
    private static final int VIEW_TYPE_ITEMS = 0;
    private static final int VIEW_TYPE_EMPTY = 1;

    /**
     * Reference, Data & Listener is passed into the constructor
     **/
    RecyclerViewAdapter(ArrayList<String> listItems, IGridItemClickListener IGridItemClickListener) {
        this.mListItems = listItems;
        this.mIGridItemClickListener = IGridItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ITEMS: {
                GridItemBindings binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.adapter_grid_item, parent, false);
                viewHolder =  new GridViewHolder(binding);
                break;
            }
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                if (!mListItems.get(position).isEmpty()) {
                    GridViewHolder mHolder = ((GridViewHolder) holder);
                    mHolder.mBinding.setGridModel(new GridItemModel(mIGridItemClickListener, mListItems.get(position), position));
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

    @Override
    public int getItemViewType(int position) {
        if (mListItems == null || mListItems.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ITEMS;
        }
    }

    /**
     * Assign grid item bindings to the ViewHolder (Excluding boiler plate dependency)
     **/
    private class GridViewHolder extends RecyclerView.ViewHolder {
        private final GridItemBindings mBinding;

        private GridViewHolder(GridItemBindings binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}