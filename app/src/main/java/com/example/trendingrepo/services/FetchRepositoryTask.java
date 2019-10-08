package com.example.trendingrepo.services;

import android.os.AsyncTask;

import com.example.trendingrepo.models.Repository;
import com.example.trendingrepo.networkUtils.HttpRequestUtils;
import com.example.trendingrepo.networkUtils.RepositoryNetworkService;
import com.example.trendingrepo.utils.Utils;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-08.
 */
public class FetchRepositoryTask extends AsyncTask<Void, Void, Repository[]> {

    public interface Delegate {
        void onSuccess(Repository[] questions);
        void onFailure();
    }

    private HttpRequestUtils requestUtils;
    private Delegate delegate;
    private RepositoryNetworkService repositoryNetworkService;

    private static final String TAG = "FetchRepositoryTask";

    public FetchRepositoryTask(Delegate delegate) {
        requestUtils = new HttpRequestUtils();
        repositoryNetworkService = new RepositoryNetworkService();
        this.delegate = delegate;
    }

    @Override
    protected Repository[] doInBackground(Void... voids) {
        try {
            return repositoryNetworkService.fetchRepositories();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Repository[] result) {
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