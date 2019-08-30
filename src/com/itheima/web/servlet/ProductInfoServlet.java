package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Product;
import com.itheima.service.ProductService;

public class ProductInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ProductInfoServlet() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
