package com.example.nguyenvanquang_b17dcat148;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.example.nguyenvanquang_b17dcat148.adapter.ViewPagerAdapter;
import com.example.nguyenvanquang_b17dcat148.databinding.ActivityMainBinding;
import com.example.nguyenvanquang_b17dcat148.fragment.CartFragment;
import com.example.nguyenvanquang_b17dcat148.fragment.ProductFragment;
import com.example.nguyenvanquang_b17dcat148.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

//    ActivityMainBinding binding;

//    private AHBottomNavigation ahBottomNavigation;
//    private AHBottomNavigationViewPager ahBottomNavigationViewPager;
    private ViewPagerAdapter adapter;
    private BottomNavigationView navigationView;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ahBottomNavigation = findViewById(R.id.AHBottomNavigation);
//        ahBottomNavigationViewPager = findViewById(R.id.AHBottomNavigationViewPager);
//
//        adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//
//        ahBottomNavigationViewPager.setAdapter(adapter);
//        ahBottomNavigationViewPager.setPagingEnabled(true); // This have you swipe
//
//        initItem(ahBottomNavigation);
//
//        // Like setOnNavigationItemSelectedListener: Get action swipe of user to change fragment tab
//        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
//            @Override
//            public boolean onTabSelected(int position, boolean wasSelected) {
//                System.out.println("Tab thứ: " + position);
//                ahBottomNavigationViewPager.setCurrentItem(position);
////                if (position == 0) {
////                    ProductFragment productFragment = new ProductFragment();
////                    getSupportFragmentManager().beginTransaction().replace(R.id.content_id, productFragment).commit();
////                } else if (position == 1) {
////                    CartFragment cartFragment = new CartFragment();
////                    getSupportFragmentManager().beginTransaction().replace(R.id.content_id, cartFragment).commit();
////                } else if (position == 2) {
////                    ProfileFragment profileFragment = new ProfileFragment();
////                    getSupportFragmentManager().beginTransaction().replace(R.id.content_id, profileFragment).commit();
////                }
//                return true;
//            }
//        });
//        // Get action swipe of user to change index Bottom navigation
//        ahBottomNavigationViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                ahBottomNavigation.setCurrentItem(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        navigationView = findViewById(R.id.navigation);
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        //viet de chon
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mProduct: viewPager.setCurrentItem(0);
                        break;
                    case R.id.mCart: viewPager.setCurrentItem(1);
                        break;
                    case R.id.mProfile: viewPager.setCurrentItem(2);
                        ProfileFragment profileFragment = (ProfileFragment) viewPager.getAdapter().instantiateItem(viewPager, 2);
                        profileFragment.reloadData();
                        break;
                }
                return true;
            }
        });

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: navigationView.getMenu().findItem(R.id.mProduct).setChecked(true);
                        break;
                    case 1: navigationView.getMenu().findItem(R.id.mCart).setChecked(true);
                        break;
                    case 2: navigationView.getMenu().findItem(R.id.mProfile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initItem(AHBottomNavigation ahBottomNavigation) {
        // Create items. (Title Tab (like menu), icon, color)
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.outline_home_24, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.outline_shopping_cart_24, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.outline_person_24, R.color.color_tab_3);

        // Add items
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);

        ahBottomNavigation.setCurrentItem(0);
    }
}