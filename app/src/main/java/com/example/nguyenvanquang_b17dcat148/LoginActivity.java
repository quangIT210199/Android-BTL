package com.example.nguyenvanquang_b17dcat148;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.data_local.DataLocalManager;
import com.example.nguyenvanquang_b17dcat148.databinding.ActivityLoginBinding;
import com.example.nguyenvanquang_b17dcat148.data.LoginRequest;
import com.example.nguyenvanquang_b17dcat148.data.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        if (!DataLocalManager.getFirstInstalled()) { // Lần đầu tiên cài app
//            Toast.makeText(Login.this, "First Install App", Toast.LENGTH_SHORT).show();
//            DataLocalManager.setFirstInstall(true);
//        }
//        if (DataLocalManager.getCheckLogged() == true && DataLocalManager.getLoginResponse() != null) {
//            checkLogged();
//        }

        binding.createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        binding.forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest loginRequest = new LoginRequest(binding.email.getText().toString(), binding.password.getText().toString());
                System.out.println(binding.email.getText().toString() +" " +  binding.password.getText().toString());

                loginApi(loginRequest);
            }
        });
    }

    private void loginApi(LoginRequest loginRequest) {
        ApiService.apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "Error Call", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.body() != null) {
                    DataLocalManager.setCheckLogged(true);
                    DataLocalManager.setLoginResponse(response.body());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    startActivity(intent);
                    finish(); // Khi finish là k back được về tk này nữa
                    Toast.makeText(LoginActivity.this, "Successs Call", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Sai thông tin tài khoản", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}