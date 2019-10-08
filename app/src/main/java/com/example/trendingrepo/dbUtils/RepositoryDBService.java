package com.example.trendingrepo.dbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.trendingrepo.models.Repository;
import com.example.trendingrepo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-08.
 */
public class RepositoryDBService {

    private DBHelper dbHelper;

    private static final String TAG = "RepositoryDBService";

    public RepositoryDBService(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    public List<Repository> getLocalRepositories() {
        List<Repository> repositories = null;
        Cursor cursor = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor = db.query(DBHelper.REPO_TABLE_NAME, null, null, null, null, null, DBHelper._ID + " asc");

            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                repositories = new ArrayList<>();
                do {
                    repositories.add(getRepository(cursor));
                } while (cursor.moveToNext());
                Utils.printLog(TAG, "Successfully Fetched repos from server :: " + repositories.size());
            }
            return repositories;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dbHelper.close();
        }
        return repositories;
    }

    public void addRepositories(Repository[] repositories) {
        for (Repository repository: repositories) {
            try {
                ContentValues values = prepareContentValues(repository);
                long inserted = dbHelper.getWritableDatabase().insertWithOnConflict(DBHelper.REPO_TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dbHelper.close();
            }
        }
    }

    private Repository getRepository(Cursor cursor) {
        Repository repository = new Repository();
        repository.setProfileImage(cursor.getString(cursor.getColumnIndex(DBHelper.AUTHOR)));
        repository.setRepoName(cursor.getString(cursor.getColumnIndex(DBHelper.REPO_NAME)));
        repository.setProfileImage(cursor.getString(cursor.getColumnIndex(DBHelper.AVATAR)));
        repository.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.DESCRIPTION)));
        repository.setStars(cursor.getInt(cursor.getColumnIndex(DBHelper.STARS)));
        repository.setForks(cursor.getInt(cursor.getColumnIndex(DBHelper.FORKS)));
        repository.setLanguage(cursor.getString(cursor.getColumnIndex(DBHelper.LANGUAGE)));
        repository.setLanguageColor(cursor.getString(cursor.getColumnIndex(DBHelper.LANGUAGE_COLOR)));
        repository.setUrl(cursor.getString(cursor.getColumnIndex(DBHelper.REPO_URL)));
        return repository;
    }

    private ContentValues prepareContentValues(Repository repository) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.AUTHOR, repository.getProfileName());
        contentValues.put(DBHelper.REPO_NAME, repository.getRepoName());
        contentValues.put(DBHelper.AVATAR, repository.getProfileImage());
        contentValues.put(DBHelper.DESCRIPTION, repository.getDescription());
        contentValues.put(DBHelper.STARS, repository.getStars());
        contentValues.put(DBHelper.FORKS, repository.getForks());
        contentValues.put(DBHelper.LANGUAGE, repository.getLanguage());
        contentValues.put(DBHelper.LANGUAGE_COLOR, repository.getLanguageColor());
        contentValues.put(DBHelper.REPO_URL, repository.getUrl());
        return contentValues;
    }

}
