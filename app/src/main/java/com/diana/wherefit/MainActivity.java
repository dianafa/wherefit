package com.diana.wherefit;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.diana.wherefit.api.FabrykaFormyApi;
import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.impl.SiteContentDownloader;
import com.diana.wherefit.impl.SportActivitiesServiceImpl;

public class MainActivity extends AppCompatActivity {
    private SportActivitiesService activitiesService;
    private boolean apisLoaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activitiesService = SportActivitiesServiceImpl.getInstance();

        getLoaderManager().initLoader(0, savedInstanceState,
                new LoaderManager.LoaderCallbacks<FabrykaFormyApi>() {
                    @Override public Loader<FabrykaFormyApi> onCreateLoader(int id, Bundle args) {
                        return new SiteContentDownloader(MainActivity.this);
                    }

                    @Override public void onLoadFinished(Loader<FabrykaFormyApi> loader, FabrykaFormyApi api) {
                        activitiesService.addApi(api);
                        apisLoaded = true;
                    }

                    @Override public void onLoaderReset(Loader<FabrykaFormyApi> loader) {
                        activitiesService.addApi(new FabrykaFormyApi());
                        //activitiesService.addApi(new MockedApiImpl(this));
                        apisLoaded = true;
                    }
                }
        ).forceLoad();
    }

    public void showAll(View view) {
        Intent intent = new Intent(this, ClassListActivity.class);
        if (apisLoaded) {
            startActivity(intent);
        }
    }

    public void showTypeSelection(View view) {
        Intent intent = new Intent(this, TypeListActivity.class);
        if (apisLoaded) {
            startActivity(intent);
        }
    }
}
