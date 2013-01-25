<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="style.css">
		<title>Admin Alluvus Raport</title>
	</head>
	<body>	
 <div id="sisu">
   <div id="vorm">

		
		 <form method='POST' action='' accept-charset="UTF-8">
		 <table width="300px">
		 	<tr>
		 		<td>
		 			Kuupäev
		 		</td>
		 		<td>
		 			Liik
		 		</td>
		 		<td>
		 			
		 		</td>
		 	</tr>
		 	<tr>
		 		<td><input type="text" name="kuupaev" value="" /></td>
		 		<td>
					<select name='liik'>
						<c:forEach var="liik" items="${liigid}">
						 	<option value="<c:out value='${liik.riigi_admin_yksuse_lik_id}' />">
						  		<c:out value="${liik.nimetus}" />
						  	</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<input type="submit" value="Värskenda">
				</td>
			</tr>
		</table>
		</form>
		<p>
        
		<table border="0" width="300px">
        <tr>
        <td>
			<c:forEach  var="yksus" items="${yksused}">
			<tr class="rida" onmouseover="this.className='rida1'" onmouseout="this.className='rida'">
				<td>
				
				<input type="hidden" name="alluv" value="<c:out value='${yksus.riigi_admin_yksuse_liik_id}' />"><c:out value="${yksus.nimetus}" />
</td>
<td align = 'right'>
<input type="button" value="Vaata" onclick='window.location = "AdminYksuseRedaktor?id=<c:out value='${yksus.riigi_admin_yksuse_liik_id}' />"'>
				
				</td>				
			
			</tr>
			</c:forEach>
		</table>
		<div align="right">
			
				<input type="button"
					value="Tagasi" onclick='window.location = "Index.jsp"'>
			</div>
	</div>
	</div>
	</body>
</html>