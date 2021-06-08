package com.example.nguyenvanquang_b17dcat148;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nguyenvanquang_b17dcat148.adapter.SliderAdapter;
import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.models.Product;
import com.example.nguyenvanquang_b17dcat148.models.ProductImage;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView imgBack, imgMinus, imgPlus;
    private TextView tvAlias, tvName, tvPrice, tvShortDescription, tvFullDescription, tvNumber;
    private Button btnAddToCart;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private SliderAdapter sliderAdapter;

    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initUI();

        Intent a = getIntent();
        Product product = (Product) a.getSerializableExtra("product");
        if (product != null) {
            initProductDetail(product);
        }

        List<ProductImage> productImageList = new ArrayList<>(product.getImages());

        ProductImage imageMain = new ProductImage("Main", product.getMainImagePath());
        productImageList.add(imageMain);

        for (ProductImage image : productImageList) {
            System.out.println("Có nhé: " + image.getName());
            System.out.println("URL nhé1: " + image.getImagePath());
        }

        sliderAdapter = new SliderAdapter(this, productImageList);
        viewPager.setAdapter(sliderAdapter);

        circleIndicator.setViewPager(viewPager);
        sliderAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity += 1;
                tvNumber.setText(quantity +"");
                double totalPrice = quantity * product.getPrice();
                tvPrice.setText(totalPrice +"$");
            }
        });

        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity -= 1;
                    tvNumber.setText(quantity +"");
                    double totalPrice = quantity * product.getPrice();
                    tvPrice.setText("$ " + totalPrice);
                }
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct(product);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private void initUI() {
        imgBack = findViewById(R.id.img_back_detail);
        imgMinus = findViewById(R.id.img_minus);
        imgPlus = findViewById(R.id.img_plus);

        tvAlias = findViewById(R.id.tv_alias);
        tvName = findViewById(R.id.tv_name);
        tvPrice = findViewById(R.id.tv_price);
        tvShortDescription = findViewById(R.id.tv_shortDescript);
        tvFullDescription = findViewById(R.id.tv_fullDescript);
        tvNumber = findViewById(R.id.tv_number);

        btnAddToCart = findViewById(R.id.bt_addCart);

        viewPager = findViewById(R.id.view_pager);
        circleIndicator = findViewById(R.id.circle_indicator);
    }

    private void addProduct(Product product) {
        System.out.println(product.getId() +" " + quantity);

        ApiService.apiService.addProductToCart(product.getId(), quantity).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                response = CheckStatusCode.checkToken(response, ProductDetailActivity.this);

                if (response.body() != null) {
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
        tvAlias.setText(product.getAlias());
        tvName.setText(product.getName());
        tvPrice.setText("$" + product.getPrice());
        tvShortDescription.setText(product.getShortDescription());
        tvFullDescription.setText(product.getFullDescription());
    }
}