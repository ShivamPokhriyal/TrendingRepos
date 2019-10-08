package com.example.trendingrepo.activities;

import android.os.PersistableBundle;
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
    private int savedPosition;

    private static final String LIST_SCROLL_POSITION = "position";
    private static final String LIST_EXPANDED_POSITION = "expandedPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        recyclerView = findViewById(R.id.repo_list);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));

        adapter = new RepositoryAdapter(this);
        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            savedPosition = savedInstanceState.getInt(LIST_SCROLL_POSITION);
            adapter.setSelectedItem(savedInstanceState.getInt(LIST_EXPANDED_POSITION));
        }
        fetchRepositories();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        outState.putInt(LIST_SCROLL_POSITION, layoutManager.findFirstVisibleItemPosition());
        outState.putInt(LIST_EXPANDED_POSITION, adapter.getSelectedItem());
        super.onSaveInstanceState(outState);
    }

    private void fetchRepositories() {
        new FetchRepositoryTask(this, new FetchRepositoryTask.Delegate() {
            @Override
            public void onSuccess(List<Repository> repos) {
                adapter.setRepositories(repos);
                adapter.notifyDataSetChanged();
                if (savedPosition >= 0 && savedPosition < adapter.getItemCount()) {
                    recyclerView.scrollToPosition(savedPosition);
                }
            }

            @Override
            public void onFailure() {

            }
        }).execute();
    }
}
