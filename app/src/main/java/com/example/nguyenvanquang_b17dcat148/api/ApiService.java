package com.example.nguyenvanquang_b17dcat148.api;


import com.example.nguyenvanquang_b17dcat148.data.LoginRequest;
import com.example.nguyenvanquang_b17dcat148.data.LoginResponse;
import com.example.nguyenvanquang_b17dcat148.data.PagingSearchProduct;
import com.example.nguyenvanquang_b17dcat148.data.PasswordReset;
import com.example.nguyenvanquang_b17dcat148.models.Bill;
import com.example.nguyenvanquang_b17dcat148.models.CartItem;
import com.example.nguyenvanquang_b17dcat148.models.Product;
import com.example.nguyenvanquang_b17dcat148.data.SignupRequest;
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
            .connectTimeout(40, TimeUnit.SECONDS)
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

    // update user
    @Multipart
    @POST("api/accounts/account/updateInfo")
    Call<User> updateProfile(@Part("userJson") RequestBody userJson, @Part MultipartBody.Part imageFile); // @Part("username")RequestBody username // S??? d???ng khi k truy???n Object

    //Create User
    @Multipart
    @POST("api/users/user/save")
    Call<User> createUser(@Part("userJson") RequestBody userJson, @Part MultipartBody.Part imageFile);

    //GET all user
    @GET("api/users/users")
    Call<List<User>> listAllUser();
    //Delete user
    @GET("api/users/user/delete")
    Call<Integer> removeUser(@Query("id") Integer id);

    /// For Product
    @GET("api/products/product/page?sortField=name&sortDir=asc&categoryID")
    Call<Product> listProduct(@Query("pageNum") Integer pageNum, @Query("keyword") String keyword);

    @GET("api/products/products")
    Call<List<Product>> listAllProduct();

        //When Scanner QRcoder add produc to cart
    @GET("api/products/product")
    Call<Product> addToCart(@Query("id") Integer id, @Query("name") String name);

    /// For Cart
    // add product to cart
    @GET("api/carts/cart/add")
    Call<Integer> addProductToCart(@Query("pid") Integer pid, @Query("qty") Integer qty);
    // show cart of user
    @GET("api/carts/cart")
    Call<List<CartItem>> getAllCart();
    // remove Cart
    @GET("api/carts/cart/remove")
    Call<Integer> deleteCart(@Query("pid") Integer pid);
    // Update Cart when clicl button
    @POST("api/carts/cart/update")
    Call<Integer> updateQuantity(@Query("pid") Integer pid, @Query("qty") Integer qty);
    //Check out
    @POST("api/bills/bill")
    Call<Integer> createBill(@Body Integer[] cartIds);
    // Search Product
    @GET("api/search/page")
    Call<PagingSearchProduct> searchByName(@Query("pageNum")  Integer pageNum, @Query("keyword") String keyword);

    // Get all bill for user
    @GET("api/bills/bill")
    Call<List<Bill>> listBill();
}
