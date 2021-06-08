package com.example.nguyenvanquang_b17dcat148.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nguyenvanquang_b17dcat148.R;
import com.example.nguyenvanquang_b17dcat148.models.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder>{

    private List<Bill> mlist;
    private Context mContext;

    public BillAdapter(Context mContext) {
        this.mContext = mContext;
        mlist = new ArrayList<>();
    }

    public void setData(List<Bill> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.BillViewHolder holder, int position) {
        Bill bill = mlist.get(position);
        if (bill == null) {
            return;
        }
        System.out.println("Tên tôi: " + bill.getId());
        holder.tvBillDate.setText("Date: " + bill.getCreateTime());
        String state = "Trạng thái: ";
        if (bill.isEnabled()) {
            state += "đã thanh toán";
        } else {
            state += "chưa thanh toán";
        }
        holder.tvBillState.setText(state);
        holder.tvBillAmount.setText("Tổng tiền: " + bill.getAmountBill());
    }

    public float getTotalAllBill() {
        float total = 0;
        for (Bill b : mlist) {
            total += b.getAmountBill();
        }

        return total;
    }

    @Override
    public int getItemCount() {
        System.out.println("Size t là: " + mlist.size());
        if (mlist != null) {
            return mlist.size();
        }
        return 0;
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBillDate;
        private TextView tvBillState;
        protected TextView tvBillAmount;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBillDate = itemView.findViewById(R.id.txt_bill_date);
            tvBillState = itemView.findViewById(R.id.txt_bill_state);
            tvBillAmount = itemView.findViewById(R.id.txt_bill_amount);
        }
    }
}
