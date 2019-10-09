package com.example.trendingrepo.services;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.trendingrepo.models.Repository;
import com.example.trendingrepo.utils.Utils;

import java.util.List;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-09.
 */
public class SyncRepoWorker extends Worker {

    private static final String TAG = "SyncRepoWorker";
    public SyncRepoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        RepositoryService service = new RepositoryService(getApplicationContext());
        List<Repository> repos = service.fetchAndUpdateRepositories();
        if (repos != null) {
            Utils.printLog(TAG, "Worker updated repos successfully");
            return Result.success();
        } else {
            Utils.printLog(TAG, "Worker couldn't update repos");
            return Result.failure();
        }
    }
}
