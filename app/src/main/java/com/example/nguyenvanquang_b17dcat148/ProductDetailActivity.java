package com.example.nguyenvanquang_b17dcat148;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.databinding.ActivityProductDetailBinding;
import com.example.nguyenvanquang_b17dcat148.fragment.ProductFragment;
import com.example.nguyenvanquang_b17dcat148.models.Product;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    ActivityProductDetailBinding binding;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_product_detail);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        setContentView(v);

        Intent a = getIntent();
        Product product = (Product) a.getSerializableExtra("product");
        if (product != null) {
            initProductDetail(product);
        }

        binding.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity += 1;
                binding.tvNumber.setText(quantity +"");
                double totalPrice = quantity * product.getPrice();
                binding.tvPrice.setText("$ " + totalPrice);
            }
        });

        binding.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity -= 1;
                    binding.tvNumber.setText(quantity +"");
                    double totalPrice = quantity * product.getPrice();
                    binding.tvPrice.setText("$ " + totalPrice);
                }
            }
        });

        binding.btAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct(product);
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void addProduct(Product product) {
        System.out.println(product.getId() +" " + quantity);

        ApiService.apiService.addProductToCart(product.getId(), quantity).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                response = CheckStatusCode.checkToken(response, ProductDetailActivity.this);

                if (response.body() != null) {
                    System.out.println("Số lượng sản phẩm là: " + response.body().toString());
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.product_detail, new ProductFragment()).commit();

                    Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Error Call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initProductDetail(Product product) {
        setImageProduct(product.getMainImagePath(), binding.imgProduct);
        binding.tvAlias.setText(product.getAlias());
        binding.tvName.setText(product.getName());
        binding.tvPrice.setText("$" + product.getPrice());
        binding.tvShortDescript.setText(product.getShortDescription());
        binding.tvFullDescript.setText(product.getFullDescription());
    }

    private void setImageProduct(String url, ImageView imgView) {
        Glide.with(ProductDetailActivity.this)
                .load(url)
                .placeholder(R.drawable.default_user)
                .into(imgView);
    }
}