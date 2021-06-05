package com.example.nguyenvanquang_b17dcat148.data_local;

import android.app.Application;

// Nhớ khai báo trong AndroidManifest.xml
public class MyApplication extends Application { // File sẽ khởi tạo các Dữ liệu sẽ sử dụng ở toàn bộ Project, sử dụng cho các Activity

    @Override
    public void onCreate() { // This created Data
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
