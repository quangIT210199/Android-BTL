package com.example.nguyenvanquang_b17dcat148;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenvanquang_b17dcat148.adapter.CartAdapter;
import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.models.CartItem;
import com.example.nguyenvanquang_b17dcat148.models.User;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private List<CartItem> cartItemList = new ArrayList<>();
    private RecyclerView rcvCartItem;
    private CartAdapter cartAdapter;
    private TextView tvTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvTotalAmount = findViewById(R.id.txt_amount);
        rcvCartItem = findViewById(R.id.rv_cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvCartItem.setLayoutManager(linearLayoutManager);

        getAllCartUser();

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvCartItem.addItemDecoration(itemDecoration);
    }

    private void getAllCartUser(){
        ApiService.apiService.getAllCart().enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                response = CheckStatusCode.checkToken(response, CartActivity.this);

                if (response.body() != null) {
                    cartItemList = response.body();
                    for (CartItem cartItem : cartItemList) {
                        System.out.println(cartItem.getProduct().getName());
                        System.out.println(cartItem.getProduct().getMainImagePath());
                    }

                    cartAdapter = new CartAdapter(cartItemList, CartActivity.this);
                    rcvCartItem.setAdapter(cartAdapter);

                    tvTotalAmount.setText("$" + cartAdapter.totalAmount());

                    Toast.makeText(CartActivity.this, "Success call", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Error call", Toast.LENGTH_SHORT).show();
            }
        });
    }
}