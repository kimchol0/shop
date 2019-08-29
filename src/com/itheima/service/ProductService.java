package com.itheima.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Product;

public class ProductService {
	
	//获得热门商品
	public List<Product> findHotProductList() {
		
		ProductDao dao = new ProductDao();
		List<Product> hotProductList = null;
		try {
			hotProductList = dao.findHotProductList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hotProductList;
	}
	
	//获得最新商品
	public List<Product> findNewProductList() {
		
		ProductDao dao = new ProductDao();
		List<Product> newProductList = null;
		try {
			newProductList = dao.findnewProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newProductList;
	}
	
}
