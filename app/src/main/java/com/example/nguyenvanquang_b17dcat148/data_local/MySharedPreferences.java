package com.example.nguyenvanquang_b17dcat148.data_local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

// Gọi hàm edit() để tạo ra một object của SharedPreferences.Editor()
public class MySharedPreferences {

    private static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES"; // Bạn chỉ cần nhập tên file thôi chứ không cần nhập đuôi file .xml. Vì mặc định sẽ có đuôi là .xml.
    private Context mContext;

    public MySharedPreferences(Context loginContext) { // Mỗi SharedPreferences đều đi kèm với một Context.
        this.mContext = loginContext;
    }

    // For String
    public void putStringValue(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, ""); // key, giá trị default
    }
    // For String Set
    public void putStringSetValue(String key, Set<String> values) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, values);

        editor.apply();
    }

    public Set<String> getStringSetValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> stringSet = new HashSet<>();
        return sharedPreferences.getStringSet(key, stringSet);
    }
    // For Boolean
    public void putBooleanValue(String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply(); // k trả về giá trị true false để xem kq như commit()
    }

    public boolean getBooleanValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }
}
