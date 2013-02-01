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
	<div id="sisu">
		<div id="vorm">
			
			Lisa alluvusi:
			<p>
			<form method='POST' action='' accept-charset="UTF-8">
				<table>
									<c:forEach var="yksuseLiik" items="${yksuseLiigid}">
				
					<tr class="Alluv">
					
					</tr>
						<tr>
							<td>Kood</td>
											<td><input type="text"
												value="<c:out value='${yksuseLiik.kood}' />" name="kood">
											</td>
										</tr>
										<tr>
											<td>Nimetus</td>
											<td><input name="nimetus" type="text"
												value="<c:out value='${yksuseLiik.nimetus}' />"></td>
										</tr>
										<tr>
											<td>Kommentaar</td>
											<td><textarea rows="4" cols="30" name="kommentaar"><c:out value="${yksuseLiik.kommentaar}" /></textarea></td>
										</tr>
										<tr>
										<td>Allub</td>
											<td><select name='ylemus'>
													<c:forEach var="ylemus" items="${ylemused}">
														<option value="<c:out value='${ylemus.riigi_admin_yksuse_lik_id}' />">
															<c:out value="${ylemus.nimetus}" />
														</option>
													</c:forEach>
											</select></td>
											</tr>
										<tr>
							<td><input type="hidden" name="id"
								value="<c:out value='${yksuseLiik.riigi_admin_yksuse_lik_id}' />"></td>
							<td></td>
							<td><c:if test="${empty yksuseLiik}">
									<input name="nimetus" type="text" value="Ühtegi alluvat ei saa hetkel lisada!">
								</c:if></td>
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
		</div>
	</div>
</body>
</html>