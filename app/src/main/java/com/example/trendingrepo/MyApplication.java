package com.example.trendingrepo;

import android.app.Application;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.trendingrepo.services.SyncRepoWorker;

import java.util.concurrent.TimeUnit;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-09.
 */
public class MyApplication extends Application {

    private static final int TIME_INTERVAL_HRS = 2;
    private static final String WORK_NAME = "SyncRepos";

    @Override
    public void onCreate() {
        super.onCreate();
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                .Builder(SyncRepoWorker.class, TIME_INTERVAL_HRS, TimeUnit.HOURS)
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork(WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
    }
}
