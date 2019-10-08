package com.example.trendingrepo.services;

import android.content.Context;
import com.example.trendingrepo.dbUtils.RepositoryDBService;
import com.example.trendingrepo.models.Repository;
import com.example.trendingrepo.networkUtils.RepositoryNetworkService;
import com.example.trendingrepo.utils.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-08.
 */
public class RepositoryService {

    private RepositoryDBService repositoryDBService;
    private RepositoryNetworkService repositoryNetworkService;

    private static final String TAG = "RepositoryService";

    public RepositoryService(Context context) {
        repositoryDBService = new RepositoryDBService(context);
        repositoryNetworkService = new RepositoryNetworkService();
    }

    public List<Repository> getAllRepositories() {
        List<Repository> repositories = repositoryDBService.getLocalRepositories();
        if (repositories == null) {
            /// Fetch and update from server
            Utils.printLog(TAG, "Fetching repositories from server and updating local db");
            Repository[] repos = repositoryNetworkService.fetchRepositories();
            if (repos != null) {
                repositoryDBService.addRepositories(repos);
                return Arrays.asList(repos);
            }
        }
        return repositories;
    }

}