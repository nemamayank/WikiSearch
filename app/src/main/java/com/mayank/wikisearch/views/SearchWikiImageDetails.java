package com.mayank.wikisearch.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mayank.wikisearch.R;
import com.mayank.wikisearch.databinding.WikiImageDetailsBindings;
import com.mayank.wikisearch.models.ImageDetailModel;
import com.mayank.wikisearch.utilities.Utils;

/**
 * This class is responsible for showing the detail view of image
 * having callback at {@link SearchWikiActivity}
 */

public class SearchWikiImageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WikiImageDetailsBindings mBindings = DataBindingUtil.setContentView(this, R.layout.activity_wiki_image_details);
        mBindings.setImageModel(new ImageDetailModel(getIntent().getStringExtra(Utils.Intent.IMAGE_URL)));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
