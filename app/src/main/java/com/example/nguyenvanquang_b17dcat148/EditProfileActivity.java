package com.example.nguyenvanquang_b17dcat148;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.databinding.ActivityEditProfileBinding;
import com.example.nguyenvanquang_b17dcat148.models.User;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;
import com.example.nguyenvanquang_b17dcat148.util.RealPathUtil;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    private Uri mUri;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_profile);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait ...");

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        setProfileUser(user);

        handlingFAB();

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Need validate field user
                user.setFirstName(binding.editFName.getText().toString());
                user.setLastName(binding.editLName.getText().toString());
                user.setAddress(binding.editAddress.getText().toString());
                user.setPhoneNumber(Integer.parseInt(binding.editPhone.getText().toString()));
                user.setPassword(null);
//
//                if (binding.editPassword.getText() != null && !binding.editPassword.getText().toString().isEmpty()) {
//                    user.setPassword(binding.editPassword.getText().toString());
//                }

                updateProfile(user);
            }
        });
    }

    private void updateProfile(User user) {
        mProgressDialog.show();

        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), jsonUser);

        //RequestBody requestBodyUsername = RequestBody.create(MediaType.parse("multipart/form-data"), strUsername); // Sử dụng khi k truyền Object
        MultipartBody.Part multipartBody = null;
        if (mUri != null) {
            String strRealPath = RealPathUtil.getRealPath(this, mUri);
            Log.e("Tên đường dẫn file", strRealPath);
            File file = new File(strRealPath); // get Path of file in gallary

            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file); // Create Object multipart
            multipartBody = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
            multipartBody = MultipartBody.Part.createFormData("imageFile", "", attachmentEmpty);
        }

        ApiService.apiService.updateProfile(body, multipartBody).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                response = CheckStatusCode.checkToken(response, EditProfileActivity.this);
                mProgressDialog.dismiss();
                if (response.body() != null) {
                    User userRes = response.body();

                    setProfileUser(userRes);
                    Toast.makeText(EditProfileActivity.this, "Success call", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mProgressDialog.dismiss(); // whether Fail or Success need turn off Progress
                Toast.makeText(EditProfileActivity.this, "Error call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlingFAB() {
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { // Chỉ từ android 6 trở lên: Just android 6 or higher
            return;
        }
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(EditProfileActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void openImagePicker() {
        TedBottomPicker.with(EditProfileActivity.this)
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected image uri
                        if (uri != null) {
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(EditProfileActivity.this.getContentResolver(),uri);
                                mUri = uri;
                                if (bitmap != null) {
                                    binding.imageviewAccountProfile.setImageBitmap(bitmap);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void setProfileUser(User user) {
        setImageUser(user.getPhotosImagePath(), binding.imageviewAccountProfile);
        binding.editFName.setText(user.getFirstName());
        binding.editLName.setText(user.getLastName());
        binding.editEmail.setText(user.getEmail());
        binding.editAddress.setText(user.getAddress());
        binding.editPhone.setText(user.getPhoneNumber() +"");
    }

    private void setImageUser(String url, ImageView imgView) {
        Glide.with(EditProfileActivity.this)
                .load(url)
                .placeholder(R.drawable.default_user)
                .circleCrop()
                .into(imgView);
    }
}