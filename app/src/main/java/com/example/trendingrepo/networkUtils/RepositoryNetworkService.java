package com.example.trendingrepo.networkUtils;

import com.example.trendingrepo.models.Repository;
import com.example.trendingrepo.utils.GsonUtils;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-08.
 */
public class RepositoryNetworkService {

    private static final String REPOSITORY_URL = "https://github-trending-api.now.sh/repositories";

    public Repository[] fetchRepositories() {
        HttpRequestUtils requestUtils = new HttpRequestUtils();
        try {
            String response = requestUtils.getResponse(REPOSITORY_URL, "FETCH_REPOSITORY");
            JsonParser parser = new JsonParser();
            String jsonArray = parser.parse(response).getAsJsonArray().toString();
            Repository[] repositories = (Repository[]) GsonUtils.getObjectFromJson(jsonArray, Repository[].class);
            return repositories;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
