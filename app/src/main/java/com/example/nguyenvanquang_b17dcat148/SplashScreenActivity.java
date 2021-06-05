package com.example.nguyenvanquang_b17dcat148;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.data_local.DataLocalManager;
import com.example.nguyenvanquang_b17dcat148.models.User;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!DataLocalManager.getFirstInstalled()) { // Lần đầu tiên cài app
            Toast.makeText(SplashScreenActivity.this, "First Install App", Toast.LENGTH_SHORT).show();
            DataLocalManager.setFirstInstall(true);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (DataLocalManager.getCheckLogged() == true || DataLocalManager.getLoginResponse() != null) {
                    checkLogged();
                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2500); // 2,5s
    }

    private void checkLogged() {
        if (DataLocalManager.getCheckLogged()) {
            Integer id = DataLocalManager.getLoginResponse().getId();
            ApiService.apiService.getUserById(id).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    response = CheckStatusCode.checkToken(response, SplashScreenActivity.this);

                    if (response == null) {
                        DataLocalManager.setCheckLogged(false);
                        DataLocalManager.setLoginResponse(null);

                        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Khi finish là k back được về tk này nữa
                    }
                    else {
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Khi finish là k back được về tk này nữa
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    DataLocalManager.setCheckLogged(false);
                    DataLocalManager.setLoginResponse(null);
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Khi finish là k back được về tk này nữa
                    Toast.makeText(SplashScreenActivity.this, "Error Call", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}