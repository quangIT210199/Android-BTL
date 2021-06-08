package com.example.nguyenvanquang_b17dcat148;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.fragment.ProductFragment;
import com.example.nguyenvanquang_b17dcat148.models.Product;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerQRActivity extends AppCompatActivity {
    private CodeScanner codeScanner;
    private CodeScannerView scannerView;
    private TextView resultScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_qractivity);

        scannerView = findViewById(R.id.scanner_view);
        resultScanner = findViewById(R.id.results_scanner);

        codeScanner = new CodeScanner(this, scannerView);

        //Decode QRcode
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.getText() != null) {
                            resultScanner.setText(result.getText());
                            String[] array = result.getText().split(":");

                            Integer id = Integer.parseInt(array[0]);
                            String name = array[1];
                            System.out.println("Đây nhé: " + id +" " + name);

                            addToCart(id);
                            Intent intent = new Intent(ScannerQRActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });
    }

    private void addToCart(Integer id) {

        ApiService.apiService.addProductToCart(id, 1).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                response = CheckStatusCode.checkToken(response, ScannerQRActivity.this);
                if (response != null) {
                    System.out.println("Tao là: " + response.body());
                    Toast.makeText(ScannerQRActivity.this, "Success Call", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(ScannerQRActivity.this, "Error Call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
//        requestPermission();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    private void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(ScannerQRActivity.this, "Camera permission is required", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
}