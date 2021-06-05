package com.example.nguyenvanquang_b17dcat148.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nguyenvanquang_b17dcat148.ProductDetailActivity;
import com.example.nguyenvanquang_b17dcat148.R;
import com.example.nguyenvanquang_b17dcat148.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<Product> mlistProduct;
    private Activity activity;

    private IClickAddToCartListener iClickAddToCartListener;

    public interface IClickAddToCartListener{ // Sử dụng để gọi bên ngoài
        void onClickAddToCart(ImageView imgAddToCart, Product product);
    }

    public ProductAdapter(Activity activity){
        mlistProduct = new ArrayList<>();
        this.activity = activity;
    }

    public void setData(List<Product> mlist, IClickAddToCartListener listener) {
        this.mlistProduct = mlist;
        this.iClickAddToCartListener = listener;
        notifyDataSetChanged(); // should be replaced with DiffUtil lib to notifyDataChange
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);

        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = mlistProduct.get(position);

        if (product == null) {
            return;
        }

        setImageProduct(product.getMainImagePath(), holder.imgProduct, holder.itemView.getContext());
//        holder.imgProduct.setImageResource(product.getImgResouce());
        holder.tvProductName.setText(product.getName());
        holder.tvDescription.setText(product.getShortDescription());
        holder.tvPrice.setText(product.getPrice() +"$");
        //Add to cart
        holder.imgAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickAddToCartListener.onClickAddToCart(holder.imgAddToCart, product);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() { // Go to productDetail
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductDetailActivity.class);
                intent.putExtra("product", product);
                activity.startActivity(intent);
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
        if (mlistProduct != null) {
            return mlistProduct.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        // Create view in item_product.xml
        private ImageView imgProduct;
        private TextView tvProductName;
        private TextView tvDescription;
        private TextView tvPrice;
        private ImageView imgAddToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product);
            imgAddToCart = itemView.findViewById(R.id.img_add_to_cart);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
