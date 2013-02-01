<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="stiil.css">
<title>Admin Yksuse Lisaja</title>
</head>

<body>
	<div id="sisu">
		<div id="vorm">
		
			<p>
				<c:forEach var="yksuseLiik" items="${yksused}">

					<table width="880px">
						<tr>
							<td width="50%">
								<form method='POST' action='' accept-charset="UTF-8">
									<table width='400px'>
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
														<option value="<c:out value='${ylemus.riigi_admin_yksus_id}' />">
															<c:out value="${ylemus.nimetus}" />
														</option>
													</c:forEach>
											</select></td>
										</tr>
									</table>
									<input type="hidden" name="riigi_admin_yksus_id"
										value="<c:out value='${yksuseLiik.riigi_admin_yksus_id}' />">
							      <input type='submit' value='lisa' />
								</form>

							</td>
							<td width="50%">
								<form method='POST' action='' accept-charset="UTF-8">

									<table width="292">
										<tr class="pealkiri">
										
											<td colspan="2">alluvused</td>
										</tr>
										<c:forEach var="alluvus" items="${alluvused}">
											<tr>
				
												<td width="2"><a href="AdminLiigiRedaktor?ID=<c:out value='${alluvus.riigi_admin_yksus_id}' />">
														<c:out value='${alluvus.nimetus}' />
												</a></td>
											
											</tr>
										</c:forEach>
										
																			<td width="138" align="center">&nbsp;</td>
									</table>
								</form>
							</td>
						</tr>
					</table>

				</c:forEach>
			<p>
              <input type="button"
					value="loobu" onClick='window.location = "Index.jsp"'>
			
			
	
        </div>
	</div>

</body>

</html>