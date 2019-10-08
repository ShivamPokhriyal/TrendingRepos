package com.example.trendingrepo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trendingrepo.R;
import com.example.trendingrepo.activities.RepositoryActivity;
import com.example.trendingrepo.models.Repository;
import com.example.trendingrepo.utils.Utils;
import com.example.trendingrepo.views.RepoDescriptionView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-07.
 */
public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Repository> repositories;
    private int selectedItem = -1;

    private static final String TAG = "Repository Adapter";

    public RepositoryAdapter(Context context) {
        this.context = context;
        repositories = new ArrayList<>();
    }

    public void setRepositories(List<Repository> list) {
        repositories = list;
    }

    public void sortByStars() {
        Collections.sort(repositories, new Comparator<Repository>() {
            @Override
            public int compare(Repository first, Repository second) {
                return first.getStars() - second.getStars();
            }
        });
    }

    public void sortByNames() {
        Collections.sort(repositories, new Comparator<Repository>() {
            @Override
            public int compare(Repository first, Repository second) {
                return first.getProfileName().toUpperCase().compareTo(second.getProfileName().toUpperCase());
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            View view = inflater.inflate(R.layout.item_repository, parent, false);
            return new RepoViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        RepoViewHolder holder = (RepoViewHolder) viewHolder;
        Repository current = repositories.get(position);
        holder.profileName.setText(current.getProfileName());
        Glide.with(context)
                .load(current.getProfileImage())
                .into(holder.profileImage);
        holder.repoName.setText(current.getRepoName());
        holder.repoDescriptionView.createView(current);
        if (position == selectedItem) {
            holder.repoDescriptionView.setVisibility(View.VISIBLE);
        } else {
            holder.repoDescriptionView.setVisibility(View.GONE);
        }
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int pos) {
        selectedItem = pos;
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView profileImage;
        private TextView profileName;
        private TextView repoName;
        private RepoDescriptionView repoDescriptionView;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            profileName = itemView.findViewById(R.id.profile_name);
            repoName = itemView.findViewById(R.id.repo_name);
            repoDescriptionView = itemView.findViewById(R.id.item_repo_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int itemPos = this.getLayoutPosition();
            if (itemPos > repositories.size() || itemPos < 0) {
                Utils.printLog(TAG, "Invalid item position on click :: " + itemPos + " when list size :: " + repositories.size());
                return;
            }
            selectedItem = (itemPos == selectedItem) ? -1 : itemPos;
            TransitionManager.beginDelayedTransition(repoDescriptionView);
            notifyDataSetChanged();
        }
    }

}
