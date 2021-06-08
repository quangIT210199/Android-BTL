package com.example.nguyenvanquang_b17dcat148.data_local;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import com.example.nguyenvanquang_b17dcat148.R;

// Nhớ khai báo trong AndroidManifest.xml and khai báo channel cho noti
public class MyApplication extends Application {
     // File sẽ khởi tạo các Dữ liệu sẽ sử dụng ở toàn bộ Project, sử dụng cho các Activity
    public static final String CHANNEL_ID = "BILL_CHANNEL";

    @Override
    public void onCreate() { // This created Data
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT; // muc do uu tien
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
