package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.itheima.domain.Category;
import com.itheima.service.ProductService;
import com.itheima.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;


public class CategoryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CategoryListServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
