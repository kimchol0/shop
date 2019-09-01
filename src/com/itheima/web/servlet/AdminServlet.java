package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.itheima.domain.Category;
import com.itheima.service.AdminService;

public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//提供一个List<Category>转成json字符串
		
		AdminService service = new AdminService();
		List<Category> categoryList = service.findAllCategory();
		
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().write(json);
	}

}
