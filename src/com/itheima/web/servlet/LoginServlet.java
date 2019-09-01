package com.itheima.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.User;
import com.itheima.service.UserService;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("进入servlet");
		
		User user = new User();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		/* UserService service = new UserService(); */
		
		System.out.println("获取的session中的用户名为："+user.getUsername());
		System.out.println("获取的session中的密码为："+user.getPassword());
		try {
			UserService service = new UserService();
			System.out.println("登录成功1：" );
			User userFromDataBase = service.login(user);
			System.out.println(userFromDataBase);
			System.out.println("登录成功2："+userFromDataBase.getUsername());
			System.out.println("登录成功2："+userFromDataBase.getPassword());
	     	request.getSession().setAttribute("user", user);
	     	System.out.println("-----------");
			System.out.println(user.getUsername());
			System.out.println(user.getPassword());
			
			if(user.getUsername().equals(userFromDataBase.getUsername())&&(user.getPassword().equals(userFromDataBase.getPassword()))) {
				
				response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
				
			}
				
			else {
				
				response.sendRedirect(request.getContextPath()+"/jsp/a.jsp");
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			response.sendRedirect(request.getContextPath()+"/jsp/c.jsp");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
