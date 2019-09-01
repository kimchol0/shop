package com.itheima.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Category;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.utils.DataSourceUtils;

public class ProductService {
	
	//获得热门商品
	public List<Product> findHotProductList() {
		
		ProductDao dao = new ProductDao();
		List<Product> hotProductList = null;
		try {
			hotProductList = dao.findHotProductList();
		} catch (SQLException e) {
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

	public List<Category> findAllCategory() {
		ProductDao dao = new ProductDao();
		List<Category> categoryList  = null;
		try {
			categoryList = dao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

	public PageBean findProductListByCid(String cid,int currentPage,int currentCount) {
		
		ProductDao dao = new ProductDao();
		
		//封装一个PageBean 返回给web
		
		PageBean<Product> pagebean = new PageBean<Product>();
		
		/* 这两行移到了web层。（就是ProductListByCidServlet）
		 * int currentPage = 1; 
		 * int currentCount= 12;
		 */
		
		//1.封装当前页
		pagebean.setCurrentPage(currentPage);
		
		//2.封装每页显示的条数
		pagebean.setCurrentCount(currentCount);
		
		//3.封装总条数
		int totalCount = 0;
		try {
			totalCount = dao.getCount(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pagebean.setTotalCount(totalCount);
		//4封装总页数
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pagebean.setTotalPage(totalPage);
		//5.当前页显示的数据
		// select * from product where cid=? limit ?,?
		//当前页与起始索引index的关系
		int index = (currentPage-1)*currentCount;
		List<Product> list = null;
		try {
			list = dao.findProductByPage(cid,index,currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pagebean.setList(list);
		return pagebean;
	}

	public Product findProductByPid(String pid) {
		ProductDao dao = new ProductDao();
		Product product = null;
		try {
			product = dao.findProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}
	//提交订单，将订单的数据和订单项的数据存储到数据库中
	public void submitOrder(Order order) {
		
		ProductDao dao = new ProductDao();
		
		try {
			//开启事务
			DataSourceUtils.startTransaction();
			//调用dao存储order表数据的方法
			dao.addOrders(order);
			//调用dao存储orderitem表数据的方法
			/* dao.addOrderItem(order.getOrderitems()); */
			dao.addOrderItem(order);
			
		} catch (SQLException e) {
			DataSourceUtils.rollbackAndClose();
			e.printStackTrace();
		} finally {
			DataSourceUtils.commitAndClose();
		}
		
	}

	public void updateOrderAddr(Order order) throws SQLException {
		ProductDao dao = new ProductDao();
		dao.updateOrderAddr(order);
		
	}
	
	//获得指定用户的订单
	public List<Order> findAllOrders(String uid) {
		ProductDao dao = new ProductDao();
		List<Order> orderList = null;
		try {
			orderList = dao.findAllOrders(uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}
	
	//
	public List<Map<String,Object>> findAllOrderItemsByOid(String oid) {
		ProductDao dao = new ProductDao();
		List<Map<String,Object>> mapList = null;
		try {
			mapList = dao.findAllOrderItemsByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	}
	
}
