package com.mayank.wikisearch.models.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Query {

    @SerializedName("pages")
    @Expose
    private Map<String, PageDetails> pageDetailsMap;

    public Map<String, PageDetails> getPageDetailsMap() {
        return pageDetailsMap;
    }

    public void setPageDetailsMap(Map<String, PageDetails> pageDetailsMap) {
        this.pageDetailsMap = pageDetailsMap;
    }
}
