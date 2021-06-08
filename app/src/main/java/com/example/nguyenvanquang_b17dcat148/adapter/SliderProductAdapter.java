package com.example.nguyenvanquang_b17dcat148.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.nguyenvanquang_b17dcat148.R;
import com.example.nguyenvanquang_b17dcat148.models.PhotoHome;
import com.example.nguyenvanquang_b17dcat148.models.ProductImage;

import java.util.List;

public class SliderProductAdapter extends PagerAdapter {
    private Context mContext;
    private List<PhotoHome> mlist;

    public SliderProductAdapter(Context mContext, List<PhotoHome> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_home, container, false);
        // Khai báo view
        ImageView imageView = view.findViewById(R.id.img_photo_home);
        // Set Data
        PhotoHome photoHome = mlist.get(position);

        if (photoHome != null) { // set image
            Glide.with(mContext).load(photoHome.getResource()).into(imageView);
        }

        //Add View to ViewGroup
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if (mlist != null) {
            return mlist.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // Remove View
        container.removeView((View) object);
    }
}
