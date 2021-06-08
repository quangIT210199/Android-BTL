package com.example.nguyenvanquang_b17dcat148.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nguyenvanquang_b17dcat148.CartActivity;
import com.example.nguyenvanquang_b17dcat148.MainActivity;
import com.example.nguyenvanquang_b17dcat148.R;
import com.example.nguyenvanquang_b17dcat148.ScannerQRActivity;
import com.example.nguyenvanquang_b17dcat148.adapter.ProductAdapter;
import com.example.nguyenvanquang_b17dcat148.adapter.SliderProductAdapter;
import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.data.PagingSearchProduct;
import com.example.nguyenvanquang_b17dcat148.models.PhotoHome;
import com.example.nguyenvanquang_b17dcat148.models.Product;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

     // using for get results

    private RecyclerView rcvProduct;
    private View mView;
    private MainActivity mainActivity;
    private ProductAdapter adapter;

    private SearchView searchView;

    private ImageButton btnCart;
    private ImageButton btnScanner;
    private List<Product> mlist;

    // ViewPager and Indicator
    private ViewPager viewPagerSlide;
    private CircleIndicator circleIndicatorHome;
    private SliderProductAdapter sliderProductAdapter;
    private List<PhotoHome> mlistPhoto;
    // Create Timer for slider
    private Timer mTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_product, container, false);
        mainActivity = (MainActivity) getActivity();

        rcvProduct = mView.findViewById(R.id.rcv_product);
        btnCart = mView.findViewById(R.id.btn_cart);
        btnScanner = mView.findViewById(R.id.scanner_qr);
        searchView = mView.findViewById(R.id.search_view);
        viewPagerSlide = mView.findViewById(R.id.view_pager_home);
        circleIndicatorHome = mView.findViewById(R.id.circle_indicator_home);

        // Set for slide photo
        mlistPhoto = getListPhoto();
        sliderProductAdapter = new SliderProductAdapter(mainActivity, mlistPhoto);
        viewPagerSlide.setAdapter(sliderProductAdapter);

        circleIndicatorHome.setViewPager(viewPagerSlide);
        sliderProductAdapter.registerDataSetObserver(circleIndicatorHome.getDataSetObserver());
        autoSlideImage();
        // Set for slide photo

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        // Set layout
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        rcvProduct.setLayoutManager(gridLayoutManager);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        adapter = new ProductAdapter(mainActivity);
        rcvProduct.setAdapter(adapter);

        getAllProduct();

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        searchView.findViewById(R.id.search_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);
                searchView.clearFocus();

                getAllProduct();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProductByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, ScannerQRActivity.class);
                // Using this
                startActivity(intent);
            }
        });


        return mView;
    }

    private List<PhotoHome> getListPhoto() {
        List<PhotoHome> photoHomes = new ArrayList<>();
        photoHomes.add(new PhotoHome(R.drawable.banner1));
        photoHomes.add(new PhotoHome(R.drawable.banner2));
        photoHomes.add(new PhotoHome(R.drawable.banner3));
        photoHomes.add(new PhotoHome(R.drawable.banner4));

        return photoHomes;
    }

    private void searchProductByName(String query) {
        ApiService.apiService.searchByName(1,query).enqueue(new Callback<PagingSearchProduct>() {
            @Override
            public void onResponse(Call<PagingSearchProduct> call, Response<PagingSearchProduct> response) {
                mlist = response.body().getListSearchProducts(); // OldList
                adapter.setData(mlist);
                rcvProduct.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<PagingSearchProduct> call, Throwable t) {
                Toast.makeText(mainActivity, "Error Call", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getAllProduct() {
        ApiService.apiService.listAllProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                response = CheckStatusCode.checkToken(response, mainActivity);

                if (response.body() != null) {
                    mlist = response.body();
                    adapter.setData(mlist);

                    rcvProduct.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(mainActivity, "Error Call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //////
    private void autoSlideImage() {
        if (mlistPhoto == null || mlistPhoto.isEmpty() || viewPagerSlide == null) {
            return;
        }

        if (mTimer == null) {
            mTimer = new Timer();
        }

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPagerSlide.getCurrentItem();
                        int totalItem = mlistPhoto.size() - 1;

                        if (currentItem < totalItem) {
                            currentItem++;
                            viewPagerSlide.setCurrentItem(currentItem);
                        } else { // >= total
                            viewPagerSlide.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 1000, 2500);
    }

    // Need cancel Thread Handler when activity dont use
    @Override
    public void onDestroyView() { // When view destroying need cancel Timer
        super.onDestroyView();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}