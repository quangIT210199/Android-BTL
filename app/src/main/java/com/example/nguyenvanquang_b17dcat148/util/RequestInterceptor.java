package com.example.nguyenvanquang_b17dcat148.util;


import com.example.nguyenvanquang_b17dcat148.data_local.DataLocalManager;
import com.example.nguyenvanquang_b17dcat148.data.LoginResponse;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = "";

        LoginResponse loginResponse = DataLocalManager.getLoginResponse();
        if (loginResponse != null) {
            token = loginResponse.getAccessToken();
        }

        Request originalRequest = chain.request();
        RequestBody requestBody = originalRequest.body();


//        Headers headers = new Headers.Builder()
//                .add("Authorization", "Bearer " + token)
//                .add("User-Agent", "Hau Restaurant")
//                .build();

        Request newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer " + token) //thêm một header với name và value.
                .addHeader("User-Agent", "Hau Restaurant")
//                .cacheControl(CacheControl.FORCE_CACHE) //  Đặt kiểm soát header là của request này, replace lên mọi header đã có.
//                .headers(headers) //Removes all headers on this builder and adds headers.
                .method(originalRequest.method(), requestBody) // Adds request method and request body
//                .removeHeader("Authorization") // Removes all the headers with this name
                .build();



        Response response = chain.proceed(newRequest); // là một lời gọi sẽ khởi tạo HTTP work. Lời gọi này sẽ thực hiện request và rả về response tương ứng.

//        Response response = chain.proceed(request);
        return response;
    }
}
