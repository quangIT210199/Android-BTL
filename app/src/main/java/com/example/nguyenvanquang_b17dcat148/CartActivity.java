package com.example.nguyenvanquang_b17dcat148;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenvanquang_b17dcat148.adapter.CartAdapter;
import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.inteface.OnCartClickListener;
import com.example.nguyenvanquang_b17dcat148.models.CartItem;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// Cần làm validate button check out
public class CartActivity extends AppCompatActivity {
    private RecyclerView rcvCartItem;
    private CartAdapter cartAdapter;
    private TextView tvTotalAmount;
    private OnCartClickListener onCartClickListener;
    private Button btnCheckOut;

    private List<CartItem> cartItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvTotalAmount = findViewById(R.id.txt_amount);
        rcvCartItem = findViewById(R.id.rv_cart);
        ImageView imgBack = findViewById(R.id.img_back);
        btnCheckOut = findViewById(R.id.btn_checkOut);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvCartItem.setLayoutManager(linearLayoutManager);

        getAllCartUser();
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvCartItem.addItemDecoration(itemDecoration);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOutCart();
            }
        });
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

                    onCartClickListener = new OnCartClickListener() {
                        @Override
                        public void onClickRemoveCart(CartItem cartItem) {
                            removeCart(cartItem.getProduct().getId());
                            tvTotalAmount.setText(cartAdapter.getTotalAmount() + "$");
                            if (cartItemList.size() < 1 || cartItemList == null) {
                                btnCheckOut.setEnabled(false);
                            }
                        }

                        @Override
                        public void onClickIncrease(CartItem cartItem) {
                            updateQuantity(cartItem.getProduct().getId(), cartItem.getQuantity());
                            tvTotalAmount.setText("$" + cartAdapter.getTotalAmount());
                        }

                        @Override
                        public void onClickReduce(CartItem cartItem) {
                            updateQuantity(cartItem.getProduct().getId(), cartItem.getQuantity());
                            tvTotalAmount.setText(cartAdapter.getTotalAmount() + "$");
                        }
                    };

                    cartAdapter.callBack(onCartClickListener);

                    rcvCartItem.setAdapter(cartAdapter);

                    tvTotalAmount.setText("$" + cartAdapter.getTotalAmount());

                    Toast.makeText(CartActivity.this, "Success call", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Error call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkOutCart() {
        System.out.println("Size này: " + cartItemList.size());
        if (cartItemList.size() > 0 && cartItemList != null) {
            System.out.println("Bấm dc này");
            Integer[] ids = cartAdapter.getCartIds();

            ApiService.apiService.createBill(ids).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    response = CheckStatusCode.checkToken(response, CartActivity.this);

                    if (response.body() != null) {
                        cartItemList = new ArrayList<>();
                        cartAdapter = new CartAdapter(cartItemList, CartActivity.this);
                        rcvCartItem.setAdapter(cartAdapter);
                        tvTotalAmount.setText("$" + cartAdapter.getTotalAmount());
                        Toast.makeText(CartActivity.this, "Thanh toán thành công!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(CartActivity.this, "Error call", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void removeCart(Integer pid) {
        ApiService.apiService.deleteCart(pid).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                response = CheckStatusCode.checkToken(response, CartActivity.this);

                if (response.body() != null) {
                    Toast.makeText(CartActivity.this, "Delete success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Error call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateQuantity(Integer pid, Integer qty) {
        ApiService.apiService.updateQuantity(pid, qty).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                response = CheckStatusCode.checkToken(response, CartActivity.this);

                if (response.body() != null) {
                    Toast.makeText(CartActivity.this, "Success call", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Error call", Toast.LENGTH_SHORT).show();
            }
        });
    }
}