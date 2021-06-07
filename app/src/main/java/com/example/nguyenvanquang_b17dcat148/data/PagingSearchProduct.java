package com.example.nguyenvanquang_b17dcat148.data;

import com.example.nguyenvanquang_b17dcat148.models.Product;

import java.util.ArrayList;
import java.util.List;

public class PagingSearchProduct {
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private long startCount;
    private long endCount;
    private String keyword;
    private List<Product> listSearchProducts = new ArrayList<>();

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public long getStartCount() {
        return startCount;
    }

    public void setStartCount(long startCount) {
        this.startCount = startCount;
    }

    public long getEndCount() {
        return endCount;
    }

    public void setEndCount(long endCount) {
        this.endCount = endCount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Product> getListSearchProducts() {
        return listSearchProducts;
    }

    public void setListSearchProducts(List<Product> listSearchProducts) {
        this.listSearchProducts = listSearchProducts;
    }
}
