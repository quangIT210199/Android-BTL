package com.example.nguyenvanquang_b17dcat148;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.nguyenvanquang_b17dcat148.models.User;

public class EditUserActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        textView = findViewById(R.id.tv_user_edit);
        Intent intent = getIntent();

        User user = (User) intent.getSerializableExtra("user");

        textView.setText(user.getId() +"");
    }
}