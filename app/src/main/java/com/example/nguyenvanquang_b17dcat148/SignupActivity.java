package com.example.nguyenvanquang_b17dcat148;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.databinding.ActivitySignupBinding;
import com.example.nguyenvanquang_b17dcat148.models.SignupRequest;
import com.example.nguyenvanquang_b17dcat148.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        binding.buttonAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = binding.editFName.getText().toString();
                String lastName = binding.editLName.getText().toString();
                String email = binding.editEmail.getText().toString();
                String password = binding.editPass.getText().toString();
                SignupRequest user = new SignupRequest(firstName, lastName, email, password);

                sigupAccount(user);
            }
        });
    }

    private void sigupAccount(SignupRequest signupRequest) {
        ApiService.apiService.createAccount(signupRequest).enqueue(new Callback<SignupRequest>() {
            @Override
            public void onResponse(Call<SignupRequest> call, Response<SignupRequest> response) {
                if (response.body() != null) {
                    Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    fileList();
                }
            }

            @Override
            public void onFailure(Call<SignupRequest> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Error call", Toast.LENGTH_SHORT).show();
            }
        });
    }
}