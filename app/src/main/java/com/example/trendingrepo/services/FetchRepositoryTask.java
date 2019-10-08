package com.example.trendingrepo.services;

import android.content.Context;
import android.os.AsyncTask;

import com.example.trendingrepo.models.Repository;
import com.example.trendingrepo.networkUtils.HttpRequestUtils;
import com.example.trendingrepo.utils.Utils;

import java.util.List;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-08.
 */
public class FetchRepositoryTask extends AsyncTask<Void, Void, List<Repository>> {

    public interface Delegate {
        void onSuccess(List<Repository> questions);
        void onFailure();
    }

    private HttpRequestUtils requestUtils;
    private Delegate delegate;
    private RepositoryService repositoryService;

    private static final String TAG = "FetchRepositoryTask";

    public FetchRepositoryTask(Context context, Delegate delegate) {
        requestUtils = new HttpRequestUtils();
        repositoryService = new RepositoryService(context);
        this.delegate = delegate;
    }

    @Override
    protected List<Repository> doInBackground(Void... voids) {
        try {
            return repositoryService.getAllRepositories();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Repository> result) {
        super.onPostExecute(result);
        if (delegate == null) {
            Utils.printLog(TAG, "Delegate is null");
            return;
        }
        if (result == null) {
            delegate.onFailure();
            return;
        }
        delegate.onSuccess(result);
    }
}