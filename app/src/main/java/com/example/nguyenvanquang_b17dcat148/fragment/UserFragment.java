package com.example.nguyenvanquang_b17dcat148.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenvanquang_b17dcat148.AddUserActivity;
import com.example.nguyenvanquang_b17dcat148.CartActivity;
import com.example.nguyenvanquang_b17dcat148.MainActivity;
import com.example.nguyenvanquang_b17dcat148.R;
import com.example.nguyenvanquang_b17dcat148.adapter.UserAdapter;
import com.example.nguyenvanquang_b17dcat148.api.ApiService;
import com.example.nguyenvanquang_b17dcat148.inteface.IUserClickListener;
import com.example.nguyenvanquang_b17dcat148.models.User;
import com.example.nguyenvanquang_b17dcat148.util.CheckStatusCode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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

    private IUserClickListener iUserClickListener;
    private RecyclerView rcvUser;
    private MainActivity mainActivity;
    private FloatingActionButton floatingActionButton;

    private UserAdapter userAdapter;
    private List<User> listUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        mainActivity = (MainActivity) getActivity();
        rcvUser = v.findViewById(R.id.recycler_User);
        floatingActionButton = v.findViewById(R.id.floatingActionButton_user);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        rcvUser.setLayoutManager(linearLayoutManager);

        userAdapter = new UserAdapter(mainActivity);

        rcvUser.setAdapter(userAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL);
        rcvUser.addItemDecoration(itemDecoration);

        getAllUser();
        iUserClickListener = new IUserClickListener() {
            @Override
            public void onClickRemoveUser(User user) {
                deleteUser(user);
            }
        };

        userAdapter.callBack(iUserClickListener);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, AddUserActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Chạy luồng này");
                getAllUser();
            }
        }).start();
    }

    private void deleteUser(User user) {
        Integer id = user.getId();
        System.out.println("User id nhé: " + id);
        ApiService.apiService.removeUser(id).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                response = CheckStatusCode.checkToken(response, mainActivity);
                if (response.body() != null) {
                    Toast.makeText(mainActivity, "Delete success", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(mainActivity, "Error call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllUser() {
        ApiService.apiService.listAllUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                response = CheckStatusCode.checkToken(response, mainActivity);

                if (response.body() != null) {
                    listUser = response.body();

                    userAdapter.setData(listUser);

                    rcvUser.setAdapter(userAdapter);
                    Toast.makeText(mainActivity, "Success Call!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(mainActivity, "Error call", Toast.LENGTH_SHORT).show();
            }
        });
    }
}