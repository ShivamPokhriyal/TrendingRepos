package com.example.trendingrepo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trendingrepo.R;
import com.example.trendingrepo.models.Repository;
import com.example.trendingrepo.views.RepoDescriptionView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-07.
 */
public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Repository> repositories;
    private int selectedItem;

    public RepositoryAdapter(Context context) {
        this.context = context;
        repositories = new ArrayList<>();
    }

    public void setRepositories(List<Repository> list) {
        repositories = list;
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
//        if (position == selectedItem) {
            holder.repoDescriptionView.setVisibility(View.VISIBLE);
//        } else {
//            holder.repoDescriptionView.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {

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
        }
    }

}
