package com.example.trendingrepo.services;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

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

    @VisibleForTesting
    public void setDbService(RepositoryDBService service) {
        repositoryDBService = service;
    }

    @VisibleForTesting
    public void setNetworkService(RepositoryNetworkService service) {
        repositoryNetworkService = service;
    }

    public List<Repository> getAllRepositories() {
        List<Repository> repositories = repositoryDBService.getLocalRepositories();
        if (repositories == null || repositories.size() == 0) {
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

    public List<Repository> fetchAndUpdateRepositories() {
        Repository[] repos = repositoryNetworkService.fetchRepositories();
        if (repos != null && repos.length > 0) {
            repositoryDBService.deleteAllRepositories();
            repositoryDBService.addRepositories(repos);
            return Arrays.asList(repos);
        }
        return repositoryDBService.getLocalRepositories();
    }

}