package com.diana.wherefit.impl;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.diana.wherefit.api.FabrykaFormyApi;

public class DownloadSiteContent extends AsyncTaskLoader<FabrykaFormyApi> {

    public DownloadSiteContent(Context context) {
        super(context);
    }

    @Override public FabrykaFormyApi loadInBackground() {
        return new FabrykaFormyApi();
    }
}