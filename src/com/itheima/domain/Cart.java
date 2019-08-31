package com.itheima.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {

	//该购物车中存储的n个购物项
	private Map <String,CartItem> cartitems = new HashMap<String,CartItem>();
	
	//购物车商品总计
	private double total;

	public Map<String, CartItem> getCartitems() {
		return cartitems;
	}

	public void setCartitems(Map<String, CartItem> cartitems) {
		this.cartitems = cartitems;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
}
