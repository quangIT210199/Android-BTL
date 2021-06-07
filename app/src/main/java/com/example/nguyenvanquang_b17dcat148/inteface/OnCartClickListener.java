package com.example.nguyenvanquang_b17dcat148.inteface;

import com.example.nguyenvanquang_b17dcat148.models.CartItem;

public interface OnCartClickListener {

    void onClickRemoveCart(CartItem cartItem);

    void onClickIncrease(CartItem cartItem);

    void onClickReduce(CartItem cartItem);
}
