<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>State Admin Unit Types tree</title>
	<link rel="stylesheet" href="css/style.css" type="text/css">
 	<link rel="stylesheet" href="css/jquery.treeview.css" /> 
 	
	<script	src="js/jquery-1.8.3.js"></script>
	<script	src="js/jquery-ui-1.9.2.custom.js"></script>
	<script src="js/jquery.cookie.js" type="text/javascript"></script>
	<script src="js/jquery.treeview.js" type="text/javascript"></script>
	<script src="js/jquery.treeview.async.js" type="text/javascript"></script>
</head>
      <body>
		 
		 <c:set var="i" value="1"/>
		 
			<c:forEach var="liik" items="${liigid}">
				<li><c:forEach var="num" begin="0" end="${liik.alluv_id}" step="1"></li>
				<ul>
				</c:forEach>
				<c:out value="${liik.nimetus}" /></ul>
			</c:forEach>
			<div align="right">
			
				<input type="button"
					value="Tagasi" onclick='window.location = "Index.jsp"'>
			</div>
			
	</div>
	</div>
	</body>
</html>