package com.example.nguyenvanquang_b17dcat148.data_local;

import android.content.Context;


import com.example.nguyenvanquang_b17dcat148.data.LoginResponse;
import com.example.nguyenvanquang_b17dcat148.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataLocalManager {
    // Singleton Design Pattern

    private static final String PRE_FIRST_INSTALL = "PRE_FIRST_INSTALL";
    private static final String PRE_TOKEN ="PRE_TOKEN";
    private static final String PRE_USER_RESPONSE="PRE_USER_RESPONSE";
    private static final String PRE_USER_LIST="PRE_USER_LIST";

    private static final String PRE_CHECK_LOGGED = "PRE_CHECK_LOGGED"; // Logged

    private static DataLocalManager instance = null;
    private MySharedPreferences mySharedPreferences;

    private DataLocalManager() {
    }

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance() { // Singleton Design Pattern
        if (instance != null) return instance;

        instance = new DataLocalManager();
        return instance;
    }

    // Save Object in Share Preferences
    public static void setLoginResponse(LoginResponse loginResponse) {
        Gson gson = new Gson(); // convert sang StringJson
        String strJsonLoginResponse = gson.toJson(loginResponse);

        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PRE_USER_RESPONSE, strJsonLoginResponse);
    }

    public static LoginResponse getLoginResponse() {
        String strJsonUser = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PRE_USER_RESPONSE);
        Gson gson = new Gson();
        LoginResponse loginResponse = gson.fromJson(strJsonUser, LoginResponse.class);

        return loginResponse;
    }

    // Save List<>
    public static void setListUser(List<User> users) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(users).getAsJsonArray();

        String strJsonArray = jsonArray.toString();
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PRE_USER_LIST, strJsonArray);
    }

    public static List<User> getListUser() {
        String strJsonList = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PRE_USER_LIST);
        List<User> users = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(strJsonList);
            JSONObject jsonObject;
            Gson gson = new Gson();
            User user;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                user = gson.fromJson(jsonObject.toString(), User.class);
                users.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }
    // Check xem có phải lần đầu cài đặt app hay không
    public static void setFirstInstall(boolean isFirst) {
        DataLocalManager.getInstance().mySharedPreferences.putBooleanValue(PRE_FIRST_INSTALL, isFirst);
    }

    public static boolean getFirstInstalled() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(PRE_FIRST_INSTALL);
    }
    // Check logged
    public static void setCheckLogged(boolean isLogged) {
            DataLocalManager.getInstance().mySharedPreferences.putBooleanValue(PRE_CHECK_LOGGED, isLogged);
    }

    public static boolean getCheckLogged() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(PRE_CHECK_LOGGED);
    }
    //

    public static void setToken(String token) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PRE_TOKEN, token);
    }

    public static String getToken() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PRE_TOKEN);
    }
}
