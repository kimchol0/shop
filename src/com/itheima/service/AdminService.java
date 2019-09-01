package com.itheima.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.AdminDao;
import com.itheima.domain.Category;
import com.itheima.domain.Product;

public class AdminService {

	public List<Category> findAllCategory() {
		AdminDao dao = new AdminDao();
		try {
			return dao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void saveProduct(Product product) throws SQLException {
		AdminDao dao = new AdminDao();
		dao.saveProduct(product);
	}

}
