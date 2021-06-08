package com.example.nguyenvanquang_b17dcat148;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.data_local.DataLocalManager;
import com.example.nguyenvanquang_b17dcat148.databinding.ActivityMyProFileBinding;
import com.example.nguyenvanquang_b17dcat148.models.User;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProFileActivity extends AppCompatActivity {

    private ActivityMyProFileBinding binding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_pro_file);
        binding = ActivityMyProFileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getUser();

        binding.btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProFileActivity.this, EditProfileActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        binding.imgBackMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private void getUser() {
        Integer id = DataLocalManager.getLoginResponse().getId();

        ApiService.apiService.getUserById(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                response = CheckStatusCode.checkToken(response, MyProFileActivity.this);

                if (response.body() != null) {
                    user = response.body();
                    setImageUser(user.getPhotosImagePath(), binding.imgUser);
                    binding.tvFullName.setText(user.getFullName());
                    binding.tvEmail.setText(user.getEmail());
                    binding.tvPhone.setText(user.getPhoneNumber() +"");
                    binding.tvAddress.setText(user.getAddress());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MyProFileActivity.this, "Error Call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Khởi tạo lại DL làm mất trải nghiệm ng dùng => tạo hàm và gọi bên ViewPager
        // Tránh reload data khi người dùng chưa ra khỏi ứng dụng hẳn.như là cho ứng dụng thoát ra khởi background
        getUser();
//        Toast.makeText(getActivity(), "Reload data", Toast.LENGTH_SHORT).show();
    }

    public void reloadData() {
        getUser();
//        Toast.makeText(getActivity(), "Reload data cc", Toast.LENGTH_SHORT).show();
    }

    private void setImageUser(String img, ImageView imgView) {
        Glide.with(MyProFileActivity.this)
                .load(img)
                .placeholder(R.drawable.default_user)
                .circleCrop()
                .into(imgView);
    }
}