<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>${blog.title}</h1>
			<c:import url="/WEB-INF/views/includes/blog-menu.jsp"/>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${post.title}</h4>
					<p>${ fn:replace(post.contents, newLine, "<br>") }</p>
				</div>
				<ul class="blog-list">
					<c:forEach items="${posts}" var="post">
						<li><a href="${pageContext.request.contextPath}/${auth.id}/${post.categoryNo}/${post.no}">${post.title}</a><span>${post.regDate}</span></li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blog.logo}">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items="${categories}" var="category">
					<li><a href="${pageContext.request.contextPath}/${auth.id}/${category.no}">${category.name}</a></li>
				</c:forEach>
			</ul>
		</div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"/>
	</div>
</body>
</html>