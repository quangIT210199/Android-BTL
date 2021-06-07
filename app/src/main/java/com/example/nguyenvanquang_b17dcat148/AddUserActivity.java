package com.example.nguyenvanquang_b17dcat148;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.databinding.ActivityAddUserBinding;
import com.example.nguyenvanquang_b17dcat148.models.Role;
import com.example.nguyenvanquang_b17dcat148.models.User;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;
import com.example.nguyenvanquang_b17dcat148.util.RealPathUtil;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {

    private ActivityAddUserBinding binding;
    private Uri mUri;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_user);
        binding = ActivityAddUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait ...");

        handlingFAB();

        binding.imgBackAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        binding.cancelCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        binding.createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                String firstName = binding.addFName.getText().toString();
                String lastName = binding.addLName.getText().toString();
                String email = binding.addEmail.getText().toString();
                String password = binding.addPassword.getText().toString();
                String address = binding.addAddress.getText().toString();
                int phoneNumber = Integer.parseInt(binding.addPhone.getText().toString());

                Set<Role> roles = new HashSet<>();

                if(binding.raAdmin.isChecked()) {
                    roles.add(new Role(1, "ADMIN"));
                }
                if(binding.raUser.isChecked()) {
                    roles.add(new Role(1, "USER"));
                }

                //Set View to Object
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPassword(password);
                user.setAddress(address);
                user.setPhoneNumber(phoneNumber);
                user.setRoles(roles);
                //Call API
                createUser(user);
            }
        });
    }

    private void createUser(User user) {
        mProgressDialog.show();
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), jsonUser);

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

        ApiService.apiService.createUser(body, multipartBody).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                response = CheckStatusCode.checkToken(response, AddUserActivity.this);
                mProgressDialog.dismiss();
                if (response.body() != null) {
                    User userRes = response.body();
                    System.out.println("Xong " + userRes.getId());
                    Toast.makeText(AddUserActivity.this, "Success call", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mProgressDialog.dismiss(); // whether Fail or Success need turn off Progress
                Toast.makeText(AddUserActivity.this, "Error call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlingFAB() {
        binding.floatingActionButtonAddUser.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(AddUserActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void openImagePicker() {
        TedBottomPicker.with(AddUserActivity.this)
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected image uri
                        if (uri != null) {
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddUserActivity.this.getContentResolver(),uri);
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
}