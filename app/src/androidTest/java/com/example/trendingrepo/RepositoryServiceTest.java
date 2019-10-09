package com.example.trendingrepo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.trendingrepo.dbUtils.RepositoryDBService;
import com.example.trendingrepo.models.Repository;
import com.example.trendingrepo.networkUtils.RepositoryNetworkService;
import com.example.trendingrepo.services.RepositoryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-09.
 */
@RunWith(AndroidJUnit4.class)
public class RepositoryServiceTest {

    private RepositoryService repositoryService;

    @Mock
    public RepositoryDBService repositoryDBService;

    @Mock
    public RepositoryNetworkService repositoryNetworkService;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        MockitoAnnotations.initMocks(this);
        repositoryService = new RepositoryService(context);
    }

    @Test
    public void testGetAllRepos_WhenLocalReturnsNull_ShouldCallNetworkService() {
        Mockito.doNothing().when(repositoryNetworkService.fetchRepositories());
        when(repositoryDBService.getLocalRepositories()).thenReturn(null);
        repositoryService.getAllRepositories();
        Mockito.verify(repositoryNetworkService.fetchRepositories(), times(1));
    }

    @Test
    public void testGetAllRepos_WhenLocalReturnsEmpty_ShouldCallNetworkService() {
        Mockito.doNothing().when(repositoryNetworkService.fetchRepositories());
        when(repositoryDBService.getLocalRepositories()).thenReturn(new ArrayList<Repository>());
        repositoryService.getAllRepositories();
        Mockito.verify(repositoryNetworkService.fetchRepositories(), times(1));
    }

    @Test
    public void testGetAllRepos_WhenLocalReturnsRepos_ShouldNotCallNetworkService() {
        Mockito.doNothing().when(repositoryNetworkService.fetchRepositories());
        List<Repository> repos = new ArrayList<>();
        repos.add(new Repository());
        when(repositoryDBService.getLocalRepositories()).thenReturn(repos);
        repositoryService.getAllRepositories();
        Mockito.verify(repositoryNetworkService.fetchRepositories(), times(0));
    }

    @Test
    public void testUpdateRepos_WhenServerReturnsNull_ShouldNotUpdateLocalDB() {
        Mockito.doNothing().when(repositoryDBService).deleteAllRepositories();
        when(repositoryNetworkService.fetchRepositories()).thenReturn(null);
        repositoryService.fetchAndUpdateRepositories();
        Mockito.verify(repositoryDBService, times(0)).deleteAllRepositories();
    }
}
