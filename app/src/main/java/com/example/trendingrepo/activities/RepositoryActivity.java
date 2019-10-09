package com.example.trendingrepo.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.trendingrepo.R;
import com.example.trendingrepo.adapters.RepositoryAdapter;
import com.example.trendingrepo.models.Repository;
import com.example.trendingrepo.services.RepositoryService;

import java.lang.ref.WeakReference;
import java.util.List;

public class RepositoryActivity extends AppCompatActivity {

    enum SortType {
        NAME(0),
        STAR(1),
        UNDEFINED(2);

        private int value;

        SortType(int value) { this.value = value; }

        public int getValue() {
            return value;
        }

        public static SortType getSortType(int value) {
            return values()[value];
        }
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RepositoryAdapter adapter;
    private int savedPosition;
    private SortType sort = SortType.UNDEFINED;

    private View retryLayout;
    private Button retryButton;

    private static final String LIST_SCROLL_POSITION = "position";
    private static final String LIST_EXPANDED_POSITION = "expandedPosition";
    private static final String LIST_SORT_SELECTED = "sortSelected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        retryLayout = findViewById(R.id.retry_layout);
        retryButton = retryLayout.findViewById(R.id.retry_button);
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
            sort = SortType.getSortType(savedInstanceState.getInt(LIST_SORT_SELECTED));
            adapter.setSelectedItem(savedInstanceState.getInt(LIST_EXPANDED_POSITION));
        }
        swipeRefreshLayout.setRefreshing(true);
        fetchRepositories();
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchRepositories();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                fetchRepositoriesFromServer();
            }
        });
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        outState.putInt(LIST_SCROLL_POSITION, layoutManager.findFirstVisibleItemPosition());
        outState.putInt(LIST_EXPANDED_POSITION, adapter.getSelectedItem());
        outState.putInt(LIST_SORT_SELECTED, sort.getValue());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort_stars:
                sort = SortType.STAR;
                adapter.sortByStars();
                adapter.notifyDataSetChanged();
                break;
            case R.id.menu_sort_names:
                sort = SortType.NAME;
                adapter.sortByNames();
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchRepositories() {
        new FetchRepositoryTask(this, false, recyclerView, swipeRefreshLayout, retryLayout, adapter).execute();
    }

    private void fetchRepositoriesFromServer() {
        new FetchRepositoryTask(this, true, recyclerView, swipeRefreshLayout, retryLayout, adapter).execute();
    }

    class FetchRepositoryTask extends AsyncTask<Void, Void, List<Repository>> {

        private RepositoryService repositoryService;
        private boolean isUpdateCall;

        private WeakReference<RecyclerView> recyclerViewWeakReference;
        private WeakReference<SwipeRefreshLayout> swipeRefreshLayoutWeakReference;
        private WeakReference<View> retryLayoutWeakReference;
        private WeakReference<RepositoryAdapter> adapterWeakReference;

        public FetchRepositoryTask(Context context, boolean isUpdateCall, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout, View retryLayout, RepositoryAdapter adapter) {
            repositoryService = new RepositoryService(context);
            this.isUpdateCall = isUpdateCall;
            recyclerViewWeakReference = new WeakReference<>(recyclerView);
            swipeRefreshLayoutWeakReference = new WeakReference<>(swipeRefreshLayout);
            retryLayoutWeakReference = new WeakReference<>(retryLayout);
            adapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        protected List<Repository> doInBackground(Void... voids) {
            try {
                if (isUpdateCall) {
                    return repositoryService.fetchAndUpdateRepositories();
                } else {
                    return repositoryService.getAllRepositories();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Repository> result) {
            super.onPostExecute(result);
            if (result == null) {
                setVisibility(View.VISIBLE, View.GONE);
                return;
            }
            setVisibility(View.GONE, View.VISIBLE);
            if (adapterWeakReference != null && adapterWeakReference.get() != null) {
                RepositoryAdapter adapter = adapterWeakReference.get();
                adapter.setRepositories(result);
                switch (sort) {
                    case NAME:
                        adapter.sortByNames();
                        break;
                    case STAR:
                        adapter.sortByStars();
                        break;
                    case UNDEFINED:
                        break;
                }
                adapter.notifyDataSetChanged();
                if (savedPosition >= 0 && savedPosition < adapter.getItemCount()) {
                    if (recyclerViewWeakReference != null && recyclerViewWeakReference.get() != null) {
                        recyclerViewWeakReference.get().scrollToPosition(savedPosition);
                    }
                }
            }
        }

        private void setVisibility(int retryVisibilty, int viewVisibility) {
            if (swipeRefreshLayoutWeakReference != null && swipeRefreshLayoutWeakReference.get() != null) {
                swipeRefreshLayoutWeakReference.get().setRefreshing(false);
                swipeRefreshLayoutWeakReference.get().setVisibility(viewVisibility);
            }

            if (recyclerViewWeakReference != null && recyclerViewWeakReference.get() != null) {
                recyclerViewWeakReference.get().setVisibility(viewVisibility);
            }

            if (retryLayoutWeakReference != null && retryLayoutWeakReference.get() != null) {
                retryLayoutWeakReference.get().setVisibility(retryVisibilty);
            }
        }
    }


}
