package com.example.trendingrepo.models;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-08.
 */
public class Repository implements Serializable {

    @SerializedName("author")
    private String profileName;
    @SerializedName("name")
    private String repoName;
    @SerializedName("avatar")
    private String profileImage;
    private String description;
    private int stars;
    private int forks;
    private String language;
    private String languageColor;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "profileName='" + profileName + '\'' +
                ", repoName='" + repoName + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", description='" + description + '\'' +
                ", stars=" + stars +
                ", forks=" + forks +
                ", language='" + language + '\'' +
                ", languageColor='" + languageColor + '\'' +
                '}';
    }
}
