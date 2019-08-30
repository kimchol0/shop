package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
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
		
		request.setAttribute("product", product);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cid", cid);
		
		request.getRequestDispatcher("/jsp/product_info.jsp").forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
