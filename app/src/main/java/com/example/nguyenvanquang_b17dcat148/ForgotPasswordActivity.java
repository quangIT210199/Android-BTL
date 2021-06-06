package com.example.nguyenvanquang_b17dcat148;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.databinding.ActivityForgotPasswordBinding;
import com.example.nguyenvanquang_b17dcat148.data.PasswordReset;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forgot_password);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait ...");

        binding.btForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordReset email = new PasswordReset(binding.editEmail.getText().toString());
                sendEmail(email);
            }
        });
    }

    private void sendEmail(PasswordReset email) {
        mProgressDialog.show();

        ApiService.apiService.resetPassword(email).enqueue(new Callback<PasswordReset>() {
            @Override
            public void onResponse(Call<PasswordReset> call, Response<PasswordReset> response) {
                mProgressDialog.dismiss();
                System.out.println("Sao lại k chạy vào đây");
                if (response.body() != null) {
                    System.out.println("Hello");
                    System.out.println(response.body().toString());

                    Toast.makeText(ForgotPasswordActivity.this, "Hãy check email của bạn!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PasswordReset> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "Error call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}