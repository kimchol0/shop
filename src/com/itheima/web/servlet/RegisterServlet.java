package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

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
		Map<String,String[]> properties = request.getParameterMap();
		User user = new User();
		try {
			
			//自己指定一个类型转换器（将String转成Date）
			ConvertUtils.register(new Converter() {

				@Override
				public Object convert(Class clazz, Object value) {
					
					//将String转成Date
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date parse = null;
					try {
						parse = format.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return parse;
				}
				
			}, Date.class);
			
			//映射封装
			BeanUtils.populate(user, properties);
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
			
			//发送激活邮件
			
			
			//跳转到注册成功页面
			response.sendRedirect(request.getContextPath()+"/jsp/RegisterSuccess.jsp");
			
		}else {
			
			//跳转到注册失败页面
			response.sendRedirect(request.getContextPath()+"/jsp/RegisterFail.jsp");
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
