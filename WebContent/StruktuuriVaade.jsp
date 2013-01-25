<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="./style.css" type="text/css">
<link rel="stylesheet" href="./jquery.treeview.css" />
<link rel="stylesheet"
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
<script src="./jquery.cookie.js" type="text/javascript"></script>
<script src="./jquery.treeview.js" type="text/javascript"></script>
<script src="./jquery.treeview.async.js" type="text/javascript"></script>
<script type="text/javascript">
</script>
		<title>Admin üksus!</title>
	</head>
	<body>	
 <div id="sisu">
  	<div id="vorm">
      
		 <u>&#187;Admin üksuse liigi struktuur</u><p>
		 <c:set var="i" value="1"/>
			<c:forEach var="liik" items="${liigid}">
				<c:forEach var="num" begin="0" end="${liik.alluv_id}" step="1">
				</c:forEach>
				&nbsp;<c:out value="${liik.nimetus}" /><br>
			</c:forEach>
			<div align="right">
			
				<input type="button"
					value="Tagasi" onclick='window.location = "Index.jsp"'>
			</div>
			
	</div>
	</div>
	</body>
</html>