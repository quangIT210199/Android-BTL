package com.example.nguyenvanquang_b17dcat148.api;


import com.example.nguyenvanquang_b17dcat148.models.LoginRequest;
import com.example.nguyenvanquang_b17dcat148.models.LoginResponse;
import com.example.nguyenvanquang_b17dcat148.models.PasswordReset;
import com.example.nguyenvanquang_b17dcat148.models.Product;
import com.example.nguyenvanquang_b17dcat148.models.SignupRequest;
import com.example.nguyenvanquang_b17dcat148.models.User;
import com.example.nguyenvanquang_b17dcat148.util.RequestInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(new RequestInterceptor())// This is used to add ApplicationInterceptor.
            .addNetworkInterceptor(new RequestInterceptor())// This is used to add NetworkInterceptor.
            .build();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.0.104:8081/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/users/user")
    Call<User> getUserById(@Query("id") Integer id);

    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/auth/reset_password")
    Call<PasswordReset> resetPassword(@Body PasswordReset email);

    @POST("api/auth/create")
    Call<SignupRequest> createAccount(@Body SignupRequest signupRequest);

    @Multipart
    @POST("/api/accounts/account/updateInfo")
    Call<User> updateProfile(@Part("userJson") RequestBody userJson, @Part MultipartBody.Part imageFile); // @Part("username")RequestBody username // Sử dụng khi k truyền Object

    /// For Product
    @GET("api/products/product/page?sortField=name&sortDir=asc&categoryID")
    Call<Product> listProduct(@Query("pageNum") Integer pageNum, @Query("keyword") String keyword);

    @GET("api/products/products")
    Call<List<Product>> listAllProduct();

    @GET("api/carts/cart/add")
    Call<Integer> addProductToCart(@Query("pid") Integer pid, @Query("qty") Integer qty);
}
