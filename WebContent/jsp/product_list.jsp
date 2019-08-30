<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>商品列表</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
				width: 100%;
			}
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>
		
			<!-- 引入header.jsp -->
			<jsp:include page="/jsp/header.jsp"></jsp:include>


		<div class="row" style="width:1210px;margin:0 auto;">
			<div class="col-md-12">
				<ol class="breadcrumb">
					<li><a href="#">首页</a></li>
				</ol>
			</div>
			
			<c:forEach items="${pagebean.list}" var="pro">
				<div class="col-md-2" style="height:250px">
				<a href="${pageContext.request.contextPath}/jsp/product_info.jsp">
					<img src="${pageContext.request.contextPath}/${pro.pimage}" width="170" height="170" style="display: inline-block;">
				</a>
				<p><a href="${pageContext.request.contextPath}/jsp/product_info.jsp" style='color:green'>${pro.pname}</a></p>
				<p><font color="#FF0000">商城价：&yen; ${pro.shop_price} </font></p>
			</div>
			</c:forEach>

		</div>

		<!--分页 -->
		<div style="width:380px;margin:0 auto;margin-top:50px;">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
			
			<!-- 上一页 -->
			<c:if test="${pagebean.currentPage==1 }">
				<li class="disabled">
					<a href="javascript:void(0);" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
					</a>
			</li>
			</c:if>
			<c:if test="${pagebean.currentPage!==1 }">
				<li>
				<a href="${pageContext.request.contextPath}/productListByCid?cid=${cid }&currentPage=${pagebean.currentPage-1 }" aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
				</a>
			</li>
			</c:if>
			
			<!-- 显示每一页 -->
			<c:forEach begin="1" end="${pagebean.totalPage }" var="page">
				<!-- 判断是否是当前页 -->
				<c:if test="${page==pagebean.currentPage }">
					<li class="active"><a href="javascript:void(0);">${page }</a></li>
				</c:if>
				<c:if test="${page!=pagebean.currentPage }">
					<li><a href="${pageContext.request.contextPath}/productListByCid?cid=${cid }&currentPage=${page }">${page }</a></li>
				</c:if>
			
			</c:forEach>
			
			<!-- 下一页 -->
			<c:if test="${pagebean.currentPage==pagebean.totalPage }">
				<li class="disabled">
					<a href="javascript:void(0);" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
			</li>
			</c:if>
			<c:if test="${pagebean.currentPage!==pagebean.totalPage }">
				<li>
					<a href="${pageContext.request.contextPath}/productListByCid?cid=${cid }&currentPage=${pagebean.currentPage+1 }" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</c:if>
			
			</ul>
		</div>
		<!-- 分页结束=======================        -->

		<!--
       		商品浏览记录:
        -->
		<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

			<h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">浏览记录</h4>
			<div style="width: 50%;float: right;text-align: right;"><a href="">more</a></div>
			<div style="clear: both;"></div>

			<div style="overflow: hidden;">

				<ul style="list-style: none;">
					<li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;"><img src="${pageContext.request.contextPath}/products/1/cs10001.jpg" width="130px" height="130px" /></li>
				</ul>

			</div>
		</div>
		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/img/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a href="${pageContext.request.contextPath}/jsp/info.jsp">关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>

	</body>

</html>