package com.itheima.web.servlet;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("all")//清除编辑器中的红线警告提示
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		try {
			
			//1.获得请求的method名称
			String methodname = request.getParameter("method");
			
			//2.获得当前被访问的对象的字节码对象
			Class clazz = this.getClass();//ProductServlet.class ---- UserServlet.class
			
			//3.获得当前字节码对象中的指定的方法
			Method method = clazz.getMethod(methodname,HttpServletRequest.class,HttpServletResponse.class);
			
			//4.执行相应的功能方法
			method.invoke(this,request,response);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
}
