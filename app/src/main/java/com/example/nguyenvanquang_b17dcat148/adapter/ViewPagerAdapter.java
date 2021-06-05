package com.example.nguyenvanquang_b17dcat148.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.nguyenvanquang_b17dcat148.fragment.CartFragment;
import com.example.nguyenvanquang_b17dcat148.fragment.ProductFragment;
import com.example.nguyenvanquang_b17dcat148.fragment.ProfileFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numPage = 3;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProductFragment();
            case 1:
                return new CartFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new ProductFragment();
        }
    }

    @Override
    public int getCount() {
        return numPage;
    }
}
