package com.diana.wherefit.impl;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class SiteContentDownloader extends AsyncTaskLoader<FabrykaFormyApi> {

    public SiteContentDownloader(Context context) {
        super(context);
    }

    @Override public FabrykaFormyApi loadInBackground() {
        return new FabrykaFormyApi();
    }
}