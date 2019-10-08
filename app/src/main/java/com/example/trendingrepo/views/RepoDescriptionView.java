package com.example.trendingrepo.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trendingrepo.R;
import com.example.trendingrepo.models.Repository;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-08.
 */
public class RepoDescriptionView extends ConstraintLayout {

    private TextView description;
    private View languageImage;
    private TextView languageText;
    private ImageView starImage;
    private TextView starText;
    private ImageView forkImage;
    private TextView forkText;

    private int rating;

    public RepoDescriptionView(Context context) {
        super(context);
    }

    public RepoDescriptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RepoDescriptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void createView(Repository repository) {
        removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_repo_description, null);

        description = view.findViewById(R.id.repo_description);
        LinearLayout layout = view.findViewById(R.id.repo_description_box);
        languageImage = layout.findViewById(R.id.repo_language_image);
        languageText = layout.findViewById(R.id.repo_language_text);
        starImage = layout.findViewById(R.id.repo_star_image);
        starText = layout.findViewById(R.id.repo_star_text);
        forkImage = layout.findViewById(R.id.repo_fork_image);
        forkText = layout.findViewById(R.id.repo_fork_text);

        ((ConstraintLayout) view).removeAllViews();

        addView(description);
        addView(layout);

        if (repository.getLanguage() == null) {
            languageImage.setVisibility(GONE);
            languageText.setVisibility(GONE);
        } else {
            languageImage.setVisibility(VISIBLE);
            languageText.setVisibility(VISIBLE);
        }

        description.setText(repository.getDescription());
        languageText.setText(repository.getLanguage());
        starText.setText(Integer.toString(repository.getStars()));
        forkText.setText(Integer.toString(repository.getForks()));
        if (repository.getLanguageColor() != null) {
            int color = Color.parseColor(repository.getLanguageColor());
            languageImage.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

}
