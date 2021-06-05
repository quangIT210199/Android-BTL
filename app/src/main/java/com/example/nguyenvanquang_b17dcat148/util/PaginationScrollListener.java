package com.example.nguyenvanquang_b17dcat148.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager linearLayoutManager;

    public PaginationScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = linearLayoutManager.getChildCount(); // Số bản ghi xuất hiện
        int totalItemCount = linearLayoutManager.getItemCount(); // Tổng số bản ghi 1 page
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();// Vị trí phần tử đầu tiên

        // Thuật toán phân trang
        if (isLoading() || isLastPage()) {// Đang tải dữ liệu hoặc là trang cuối cùng r. Không xử lý phân trang nữa
            return;
        }

        if (firstVisibleItemPosition >= 0 && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
            loadMore();
        }
    }

    public abstract void loadMore();
    public abstract boolean isLoading();
    public abstract boolean isLastPage();
}
