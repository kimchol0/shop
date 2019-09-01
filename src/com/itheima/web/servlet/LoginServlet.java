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
		UserService service = new UserService();
		User userFromSession = new User();
		request.getSession().setAttribute("user", userFromSession);
		
		System.out.println("获取的session中的密码为："+userFromSession.getPassword());
		
		try {
			
			User userFromDataBase = service.login(user);
			
			System.out.println("从数据库中获取的数据："+userFromDataBase.getPassword());
			
			if((userFromSession.getUsername()==userFromDataBase.getUsername())&&(userFromSession.getPassword()==userFromDataBase.getPassword())) {
				response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
			}
			else {
				response.sendRedirect(request.getContextPath()+"/jsp/a.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
