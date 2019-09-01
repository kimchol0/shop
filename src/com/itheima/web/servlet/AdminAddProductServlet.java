package com.itheima.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.service.AdminService;
import com.itheima.utils.CommonsUtils;

public class AdminAddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public AdminAddProductServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Product product = new Product();
		//收集数据的容器
		Map<String,Object> map = new HashMap<String,Object>();
		
		//目的：收集表单的数据并封装成一个Product实体 将上传图片存到服务器上
		try {
			//创建磁盘文件项工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//创建文件上传核心对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解析request获得文件项对象的集合
			List <FileItem> parseRequest = upload.parseRequest(request);
				for(FileItem item:parseRequest) {
					//判断是否是普通表单项
					boolean fromField = item.isFormField();
					if (fromField) {
						//普通表单项获得表单的数据，封装到Product实体中
						String fieldName = item.getFieldName();
						String fieldValue = item.getString("UTF-8");
						map.put(fieldName, fieldValue);
					}
					else {
						//文件上传项 获得文件名称 获得文件的内容
						String fileName = item.getName();
						String path = this.getServletContext().getRealPath("upload");
						InputStream in = item.getInputStream();
						OutputStream out = new FileOutputStream(path+"/"+fileName);
						IOUtils.copy(in, out);
						in.close();
						out.close();
						item.delete();		
						
						map.put("pimage", "upload/"+fileName);
						
					}
				}
				
				BeanUtils.populate(product, map);
				//是否product对象封装数据完全
				product.setPid(CommonsUtils.getUUID());
				product.setPdate(new Date());
				product.setPflag(0);
				Category category = new Category();
				category.setCid(map.get("cid").toString());
				product.setCatgory(category);
				//将product传递给service层
				AdminService service = new AdminService();
				service.saveProduct(product);
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
		
	}

}
