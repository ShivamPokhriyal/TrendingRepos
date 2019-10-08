package com.example.trendingrepo.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.trendingrepo.R;
import com.example.trendingrepo.adapters.RepositoryAdapter;
import com.example.trendingrepo.models.Repository;
import com.example.trendingrepo.services.FetchRepositoryTask;

import java.util.List;

public class RepositoryActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RepositoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        recyclerView = findViewById(R.id.repo_list);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RepositoryAdapter(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        recyclerView.setAdapter(adapter);
        fetchRepositories();
    }

    private void fetchRepositories() {
        new FetchRepositoryTask(this, new FetchRepositoryTask.Delegate() {
            @Override
            public void onSuccess(List<Repository> repos) {
                adapter.setRepositories(repos);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        }).execute();
    }
}
