package com.example.nguyenvanquang_b17dcat148.adapter;

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
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.nguyenvanquang_b17dcat148.EditUserActivity;
import com.example.nguyenvanquang_b17dcat148.R;
import com.example.nguyenvanquang_b17dcat148.inteface.IUserClickListener;
import com.example.nguyenvanquang_b17dcat148.models.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> mlist;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private Context mContext;

    private IUserClickListener iUserClickListener;

    public void callBack(IUserClickListener iUserClickListener) {
        this.iUserClickListener = iUserClickListener;
    }

    public UserAdapter(Context mContext) {
        mlist = new ArrayList<>();
        this.mContext = mContext;
    }

    public void setData(List<User> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User user = mlist.get(position);
        if (user == null) {
            return;
        }
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(user.getId()));

        setImageUser(user.getPhotosImagePath(),holder.circleImageViewUser ,mContext);
        holder.tvUserName.setText(user.getFullName());
        holder.tvUserEmail.setText(user.getEmail());
        holder.tvUserPhone.setText(String.valueOf(user.getPhoneNumber()));
        holder.tvUserAddress.setText(user.getAddress());

        holder.layoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditUserActivity.class);
                intent.putExtra("user", user);
                mContext.startActivity(intent);
            }
        });

        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlist.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
                iUserClickListener.onClickRemoveUser(user);
            }
        });
    }

    private void setImageUser(String img, ImageView imgView, Context mcontext) {
        Glide.with(mcontext)
                .load(img)
                .placeholder(R.drawable.default_user)
                .circleCrop()
                .into(imgView);
    }

    @Override
    public int getItemCount() {
        if (mlist != null) {
            return mlist.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private SwipeRevealLayout swipeRevealLayout;
        private TextView layoutDelete;
        private TextView layoutEdit;
        private CircleImageView circleImageViewUser;
        private TextView tvUserName, tvUserEmail, tvUserPhone, tvUserAddress;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeRevealLayout = itemView.findViewById(R.id.swipeReveal);
            layoutDelete = itemView.findViewById(R.id.layout_delete_user);
            layoutEdit = itemView.findViewById(R.id.layout_edit_user);
            circleImageViewUser = itemView.findViewById(R.id.user_image);
            tvUserName = itemView.findViewById(R.id.txt_user_name);
            tvUserEmail = itemView.findViewById(R.id.txt_user_email);
            tvUserPhone = itemView.findViewById(R.id.txt_user_phone);
            tvUserAddress = itemView.findViewById(R.id.txt_user_address);
        }
    }
}
