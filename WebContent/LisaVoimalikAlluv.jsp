<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	

			Lisa alluvusi:
			<p>
			<form method='POST' action='' accept-charset="UTF-8">
				<table>
					<tr class="Alluv">
					
					</tr>
					<c:forEach var="yksuseLiik" items="${yksuseLiigid}">
						<tr>
							<td><input type="checkbox" name="alluv_id"
								value="<c:out value='${yksuseLiik.riigi_admin_yksuse_lik_id}' />"></td>
							
							<td><a href="AdminLiigiRedaktor?ID=<c:out value='${yksuseLiik.riigi_admin_yksuse_lik_id}' />">
									<c:out value="${yksuseLiik.nimetus}" />
							</a></td>
							<td><input type="textarea" name="kommentaar"
								value="<c:out value='${yksuseLiik.kommentaar}' />"></td>
						</tr>
						
						<tr>
							<td></td>
							<td></td>
							<td><c:if test="${not empty yksuseLiik}">
									<input type='submit' value='lisa'>
								</c:if></td>
						</tr>
					</c:forEach>
				</table>

			</form>
		
</body>
</html>