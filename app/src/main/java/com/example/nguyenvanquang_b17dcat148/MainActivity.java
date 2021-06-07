package com.example.nguyenvanquang_b17dcat148;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.nguyenvanquang_b17dcat148.adapter.ViewPagerAdapter;
import com.example.nguyenvanquang_b17dcat148.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

//    ActivityMainBinding binding;
    private ViewPagerAdapter adapter;
    private BottomNavigationView navigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    case R.id.mUser: viewPager.setCurrentItem(1);
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
                    case 1: navigationView.getMenu().findItem(R.id.mUser).setChecked(true);
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
}