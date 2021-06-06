package com.example.nguyenvanquang_b17dcat148.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.nguyenvanquang_b17dcat148.R;
import com.example.nguyenvanquang_b17dcat148.models.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> mlist;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public CartAdapter(List<CartItem> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) { // Set Data
        CartItem cartItem = mlist.get(position);
        if (cartItem == null) {
            return;
        }

        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(cartItem.getId()));
        setImageProduct(cartItem.getProduct().getMainImagePath() ,holder.imgProduct, holder.itemView.getContext());
        holder.tvProductName.setText(cartItem.getProduct().getName());
        holder.tvProductPrice.setText("$" + String.valueOf(cartItem.getSubtotal()));
        holder.tvProductAmount.setText(String.valueOf(cartItem.getQuantity()));

        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlist.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        holder.imgReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.imgIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setImageProduct(String img, ImageView imgView, Context mcontext) {
        Glide.with(mcontext)
                .load(img)
                .placeholder(R.drawable.default_user)
                .into(imgView);
    }

    @Override
    public int getItemCount() {
        if (mlist != null){
            return mlist.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeRevealLayout;
        private LinearLayout layoutDelete;
        private ImageView imgProduct;
        private TextView tvProductName;
        private TextView tvProductPrice;
        private TextView tvProductAmount;
        private ImageView imgReduce;
        private ImageView imgIncrease;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);
            layoutDelete = itemView.findViewById(R.id.layout_delete);
            imgProduct = itemView.findViewById(R.id.product_image);
            tvProductName = itemView.findViewById(R.id.txt_product_name);
            tvProductPrice = itemView.findViewById(R.id.txt_product_price);
            tvProductAmount = itemView.findViewById(R.id.txt_product_amount);
            imgReduce = itemView.findViewById(R.id.btn_reduce);
            imgIncrease = itemView.findViewById(R.id.btn_increase);
        }
    }
}
