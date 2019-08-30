package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.PageBean;
import com.itheima.service.ProductService;


public class ProductListByCidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductListByCidServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		
		request.getRequestDispatcher("/jsp/product_list.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
