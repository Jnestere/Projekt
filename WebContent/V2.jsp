<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="stiil.css">
		<title>Vaata admin üksust!</title>
	</head>
	<body>	
 <div id="sisu">
    <div id="vorm">
	       Vaata riigi admin üksusi:<p>
   <c:forEach var="yksus" items="${yksused}">
	
   <table width="880px">
   <tr>	
   <td width="50%">
    <form method='POST' action='' accept-charset="UTF-8">
    	<table width='400px'>
			<tr>
				<td>
					Kood
				</td>
				<td>
					<input type="text" value="<c:out value='${yksus.kood}' />" name="kood">
				</td>
			</tr>
			<tr>
				<td>
					Nimetus
				</td>
				<td>
					<input name="nimetus" type="text" value="<c:out value='${yksus.nimetus}' />">
				</td>
			</tr>	
			<tr>
				<td>
					Kommentaar
				</td>
				<td>
					<textarea rows="4" cols="30" name="kommentaar"><c:out value="${yksus.kommentaar}" /></textarea>
				</td>
			</tr>
			<tr>
				<td>
					Liik
				</td>
				<td>
					<select name='liik'>
					<c:forEach var="liik" items="${yksused}">
					 	<option value="<c:out value='${yksus.riigi_admin_yksuse_liik_id}' />">
					  		<c:out value="${yksus.riigi_admin_yksuse_liik_nimetus}" />
					  		</option>
					  		</c:forEach>
					  	
					</select>
				</td>
			</tr>
			<tr>
				<td>
					Allub
					
				</td>
				<td>
					<select name='allub' title='Näitab ainult admin üksusi millele on võimalik alluda'>
					 	<option value="<c:out value='${alluvus.admin_alluvus_id}' />">
					  		<c:out value="${alluvus.riigi_admin_yksuse_liik}" />
					  	</option>
					</select>
				</td>
			</tr>
		</table>
	<input type="hidden" name="riigi_admin_yksus_id" value="<c:out value='${yksus.riigi_admin_yksus_id}' />">
	<div align="center">
		<input type='submit' value='salvesta vorm' />
	</div>
	</form>
	
	</td><td width="50%">
	<form method='POST' action='' accept-charset="UTF-8">
	
	<table width="300px">
	<tr class="pealkiri">
		<td colspan="2">
			Alluvad
		</td>
	</tr>
	<c:forEach var="alluvus" items="${alluvused}">
	<tr>
		<td>
		<a href="AdminYksuseRedaktor?ID=<c:out value='${alluvus.admin_alluvus_id}' />">
			<c:out value='${alluvus.riigi_admin_yksuse_nimi}' />
		</a>
		</td>
		<td align='right'>
		<input type="hidden" name="alluv" value="<c:out value='${alluvus.admin_alluvus_id}' />">
		<input type="hidden" name="ylev" value="<c:out value='${yksus.riigi_admin_yksus_id}' />">
		<input type='submit' value='eemalda'></td>
	</tr>
	</c:forEach>
	<tr>
		<td colspan="2" align='right'>
			<input type="button" value="lisa" onclick='window.location = "LisaAdminAlluv?id=<c:out value='${yksus.riigi_admin_yksus_id}' />"'>
		</td>
	</table>
</form>
	</td></tr></table></c:forEach>
	
	<input type="button"
					value="loobu" onclick='window.location = "IndexScreen.jsp"'>
	</div>
	</div>
	</body>
</html>