package com.example.trendingrepo.utils;

import android.util.Log;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-08.
 */
public class Utils {

    public static void printLog(String tag, String message) {
        try {
            Log.d(tag, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
