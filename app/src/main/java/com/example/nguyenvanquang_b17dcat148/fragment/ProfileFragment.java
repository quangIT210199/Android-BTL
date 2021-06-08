package com.example.nguyenvanquang_b17dcat148.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nguyenvanquang_b17dcat148.BillActivity;
import com.example.nguyenvanquang_b17dcat148.EditProfileActivity;
import com.example.nguyenvanquang_b17dcat148.LoginActivity;
import com.example.nguyenvanquang_b17dcat148.MainActivity;
import com.example.nguyenvanquang_b17dcat148.MyProFileActivity;
import com.example.nguyenvanquang_b17dcat148.R;
import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.data_local.DataLocalManager;
import com.example.nguyenvanquang_b17dcat148.databinding.FragmentProfileBinding;
import com.example.nguyenvanquang_b17dcat148.models.User;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    private FragmentProfileBinding binding;
    private MainActivity mainActivity;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mainActivity = (MainActivity) getActivity();

        binding.clEditMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, MyProFileActivity.class);
                startActivity(intent);
            }
        });

        // Changer Password
        binding.clChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Your Bill
        binding.clBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, BillActivity.class);
                startActivity(intent);
            }
        });

        binding.clAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.clSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataLocalManager.setCheckLogged(false);
                DataLocalManager.setLoginResponse(null);
                Intent login = new Intent(mainActivity, LoginActivity.class);
                startActivity(login);

                mainActivity.finish();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Khởi tạo lại DL làm mất trải nghiệm ng dùng => tạo hàm và gọi bên ViewPager
        // Tránh reload data khi người dùng chưa ra khỏi ứng dụng hẳn.như là cho ứng dụng thoát ra khởi background

//        Toast.makeText(getActivity(), "Reload data", Toast.LENGTH_SHORT).show();
    }

    public void reloadData() {

//        Toast.makeText(getActivity(), "Reload data cc", Toast.LENGTH_SHORT).show();
    }
}