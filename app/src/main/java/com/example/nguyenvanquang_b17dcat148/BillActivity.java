package com.example.nguyenvanquang_b17dcat148;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.nguyenvanquang_b17dcat148.adapter.BillAdapter;
import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.databinding.ActivityBillBinding;
import com.example.nguyenvanquang_b17dcat148.models.Bill;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillActivity extends AppCompatActivity {

    private ActivityBillBinding binding;
    private BillAdapter billAdapter;
    private List<Bill> mlist;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bill);
        binding = ActivityBillBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rcvBill.setLayoutManager(linearLayoutManager);

        billAdapter = new BillAdapter(this);

        binding.rcvBill.setAdapter(billAdapter);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait ...");

        getAllBill();

        binding.imgBackBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private void getAllBill() {
        mProgressDialog.show();

        ApiService.apiService.listBill().enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                mProgressDialog.dismiss();

                response = CheckStatusCode.checkToken(response, BillActivity.this);
                if (response.body() != null) {
                    mlist = response.body();

                    billAdapter.setData(mlist);

                    for (Bill b : response.body()) {
                        System.out.println("Có này: " + b.getId());
                    }

                    binding.rcvBill.setAdapter(billAdapter);

                    binding.txtAmountBill.setText(billAdapter.getTotalAllBill() +"$");

                    Toast.makeText(BillActivity.this, "Call success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(BillActivity.this, "Error call", Toast.LENGTH_SHORT).show();
            }
        });
    }
}