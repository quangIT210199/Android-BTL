package com.example.nguyenvanquang_b17dcat148.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import com.example.nguyenvanquang_b17dcat148.LoginActivity;
import com.example.nguyenvanquang_b17dcat148.data_local.DataLocalManager;

import retrofit2.Response;

public final class CheckStatusCode<T> {

    public static <T> Response<T> checkToken(Response<T> response, Context context) {
        Intent intent;

        if (response.code() == 401) {
            DataLocalManager.setCheckLogged(false);
            DataLocalManager.setLoginResponse(null);
            intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);

            ((Activity)context).finish();
            return null;
        }

        return response;
    }
}
