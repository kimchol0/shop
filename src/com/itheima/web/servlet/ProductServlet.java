package com.itheima.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Category;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;

public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	//模块中的功能同方法进行区分的
	
	//将商品添加到购物车
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		ProductService service = new ProductService();
		
		//获得放到购物车的pid		
		String pid = request.getParameter("pid");
		
		//获得该商品的购买数量
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));
		
		//获得product对象
		Product product = service.findProductByPid(pid);
		
		//计算小计
		double subTotal = product.getShop_price()*buyNum;
		
		//封装CartItem
		CartItem item = new CartItem();
		item.setProduct(product);
		item.setBuyNum(buyNum);
		item.setSubtotal(subTotal);
		
		//获得购物车   判断是否在Session中已经存在购物车
		
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart==null) {
			cart = new Cart();
		}
	
		//将购物项放到车中----key是pid
		
		//判断购物车中是否已经包含此购物项，判断key是否已存在
		//如果购物车中已经存在该商品，将现在买的数量与原有的数据量进行相加操作
		Map<String,CartItem> cartItems = cart.getCartitems();
		
		double newsubTotal = 0.0;
		
		if(cartItems.containsKey(pid)) {
			
			//取出原有商品的数量
			CartItem cartitem = cartItems.get(pid);
			//修改数
			int oldBuyNum = cartitem.getBuyNum();
			oldBuyNum+=buyNum;
			cartitem.setBuyNum(oldBuyNum);
			
			cart.setCartitems(cartItems);
			
			//修改小计
			//原来该商品的小计
			double oldsubTotal = cartitem.getSubtotal();
			//新买的商品的小计
			newsubTotal = buyNum*product.getShop_price();
			cartitem.setSubtotal(oldsubTotal+newsubTotal);
		}else {
			//如果没有该商品
			//获得购物车中的购物项集合
			cart.getCartitems().put(product.getPid(), item);
			newsubTotal = buyNum*product.getShop_price();
		}
		
		//计算总计
		double total = cart.getTotal()+subTotal;
		cart.setTotal(total);
		
		//将车再次放回session
		session.setAttribute("cart", cart);
		
		//直接跳转到购物车页面
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		
}
	
	//删除单一商品
	public void delProFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//获得要删除item的pid
		String pid = request.getParameter("pid");
		//删除session中的购物车中的购物项集合中的item
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart!=null) {
			
			Map<String,CartItem> cartItems = cart.getCartitems();
			//需要修改总价
			cart.setTotal(cart.getTotal()-cartItems.get(pid).getSubtotal());
			//删除
			cartItems.remove(pid);
			cart.setCartitems(cartItems);
		}
		
		session.setAttribute("cart", cart);
		
		//跳转回cart.jsp
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
	
	}
	
	//清空购物车
	public void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.removeAttribute("cart");
		
		//跳转回cart.jsp
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		
	}
	
	
	//显示商品的类别的功能
	public void categoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductService service = new ProductService();
		
		//从缓存中查询categoryList如果有直接使用，如果没有再从数据库中查询，再存到缓存中
		//1.获得jedis对象，链接redis数据库
		Jedis jedis = JedisPoolUtils.getJedis();
		String categoryListJson = jedis.get("categoryListJson");//key名字自己起
		//2.判断categoryListJson是否为空
		if(categoryListJson==null) {
			System.out.println("缓存没有数据，查询数据库");
			List<Category> categoryList = service.findAllCategory();//从数据库查
			//List转换为json
			Gson gson = new Gson();
			categoryListJson = gson.toJson(categoryList);
			jedis.set("categoryListJson",categoryListJson);
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(categoryListJson);
		
	}
	
	//显示首页的功能
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductService service = new ProductService();
		
		//准备热门商品----List<Product>
		List<Product> hotProductList = service.findHotProductList();
		
		//准备最新商品----List<Product>
		List<Product> newProductList = service.findNewProductList();
		
		//准备分类数据
		/*
		 * List<Category> categoryList = service.findAllCategory();
		 * 
		 * request.setAttribute("categoryList", categoryList);
		 */
		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);
		
		request.getRequestDispatcher("/jsp/index.jsp").forward(request,response);
		
	}
	
	//显示商品详细信息
	public void productInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得当前页
		String currentPage = request.getParameter("currentPage");
		//获得商品类别
		String cid = request.getParameter("cid");
				
		//获得要查询商品的pid
		String pid = request.getParameter("pid");
		ProductService service = new ProductService();
		Product product = service.findProductByPid(pid);
				
		//获得客户端携带cookie---获得名字是pids的cookie
		String pids = pid;
		Cookie[]cookies = request.getCookies();
			if(cookies!=null) {
				for(Cookie cookie:cookies) {
					if("pids".equals(cookie.getName())) {
						pids = cookie.getValue();
						//1-3-2本次访问商品pid是0------>0-1-3-2
						//1-3-2本次访问商品pid是3------>3-1-2
						//1-3-2本次访问商品pid是2------>2-1-3
						//将pids拆成一个数组
						String[] spilt = pids.split("-");//3-1-2
						List<String> asList = Arrays.asList(spilt);//[3,1,2]
						LinkedList<String> list = new LinkedList<String>(asList);//[3,1,2]
						//判断集合中是否存在当前pid
						if(list.contains(pid)) {
						//包含当前查看商品的pid
							list.remove(pid);
							list.addFirst(pid);
						}else {
						//不包含当前查看商品的pid，直接将pid放到头上
								list.addFirst(pid);
							}
						//将[3,1,2]转成3-1-2字符串
						StringBuffer sb = new StringBuffer();
						for(int i=0;i<list.size()&&i<7;i++) {
							sb.append(list.get(i));
							sb.append("-");//3-1-2-
						}
						//去掉3-1-2后的-
						pids = sb.substring(0,sb.length()-1);
					}
				}
			}
				
		Cookie cookie_pids = new Cookie("pids",pids);
		response.addCookie(cookie_pids);
		
		request.setAttribute("product", product);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cid", cid);
				
		request.getRequestDispatcher("/jsp/product_info.jsp").forward(request, response);
	}
	
	
	//根据商品的类别获得商品的列表
	public void productList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得cid
				String cid = request.getParameter("cid");
				
				String currentPageStr = request.getParameter("currentPage");
				
				if(currentPageStr==null) currentPageStr="1";
				int currentPage = Integer.parseInt(currentPageStr);
				int currentCount= 12;
				
				ProductService service = new ProductService();
				PageBean pagebean = service.findProductListByCid(cid,currentPage,currentCount);
				
				request.setAttribute("pagebean", pagebean);
				request.setAttribute("cid", cid);
				
				//定义一个集合，记录历史商品信息的集合
				List<Product> historyProductList = new ArrayList<Product>();
				
				
				//获得客户端携带名字叫pids的cookie
				Cookie[] cookies = request.getCookies();
				if(cookies!=null) {
					for(Cookie cookie:cookies) {
						if("pids".equals(cookie.getName())) {
							String pids = cookie.getValue();//3-2-1
							String[] split = pids.split("-");
								for(String pid:split) {
									Product pro = service.findProductByPid(pid);
									historyProductList.add(pro);
								}
						}
					}
				}
				
				//将历史记录的集合放到域中
				request.setAttribute("historyProductList", historyProductList);
				
				request.getRequestDispatcher("/jsp/product_list.jsp").forward(request, response);
		
	}
	
	
}
