package com.itheima.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

	private String oid;//该订单的订单号
	private Date orderTime;//下单时间
	private double total;//该订单总金额
	private int state;//订单支付状态。1代表已付款，0代表未付款
	private String address;//收货地址
	private String name;//收货人
	private String telephone;//收货人电话
	private User user;//该订单输入哪个用户
	
	//该订单中有多少个订单项
	List<OrderItem> orderitems = new ArrayList<OrderItem>();
	
	public List<OrderItem> getOrderitems() {
		return orderitems;
	}
	public void setOrderitems(List<OrderItem> orderitems) {
		this.orderitems = orderitems;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
