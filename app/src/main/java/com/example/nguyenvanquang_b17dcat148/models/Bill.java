package com.example.nguyenvanquang_b17dcat148.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Integer id;

    @SerializedName("createTime")
    private Date createTime;

    @SerializedName("amountBill")
    private Float amountBill;

    @SerializedName("cartItems")
    List<CartItem> cartItems;

    @SerializedName("enabled")
    private boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Float getAmountBill() {
        return amountBill;
    }

    public void setAmountBill(Float amountBill) {
        this.amountBill = amountBill;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
