package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.CommonsUtils;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		//获得表单数据
		Map<String,String[]> parameterMap = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, parameterMap);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		//封装uid,telephone,state,code
		
		user.setUid(CommonsUtils.getUUID());
		user.setTelephone(null);
		user.setState(0);//代表未激活
		user.setCode(CommonsUtils.getUUID());
		
		//将user传递给service层
		
		UserService service = new UserService();
		boolean isRegisterSuccess = service.regist(user);
		
		//判断是否注册成功
		if(isRegisterSuccess) {
			
			//跳转到注册成功页面
			response.sendRedirect(request.getContextPath()+"/RegisterSuccess.jsp");
			
		}else {
			
			//跳转到注册失败页面
			
			response.sendRedirect(request.getContextPath()+"/RegisterFail.jsp");
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
