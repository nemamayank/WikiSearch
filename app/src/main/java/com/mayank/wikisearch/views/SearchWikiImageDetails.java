package com.mayank.wikisearch.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mayank.wikisearch.R;
import com.mayank.wikisearch.databinding.WikiImageDetailsBindings;
import com.mayank.wikisearch.utilities.Utils;

/**
 * This class is responsible for showing the detail view of image
 * having callback at {@link SearchWikiActivity}
 */

public class SearchWikiImageDetails extends AppCompatActivity {
    private WikiImageDetailsBindings mBindings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBindings = DataBindingUtil.setContentView(this, R.layout.activity_wiki_image_details);
        initialize();
    }

    private void initialize() {
        setupToolbar();
        // Showing Image Details Using Glide library
        showImageDetail();
    }

    private void setupToolbar() {
        getSupportActionBar().setTitle(R.string.title_detail_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void showImageDetail() {
        if (getIntent().hasExtra(Utils.Intent.IMAGE_URL)) {

            Utils.showFullScreenImage(this, getIntent().getStringExtra(Utils.Intent.IMAGE_URL), mBindings.ivImageDetails);

        } else {

            Utils.showErrorImage(this, mBindings.ivImageDetails);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
