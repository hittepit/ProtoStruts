<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Liste des personnes</title>
</head>
<body>
	<h1>Liste des personnes</h1>
	<table border="1">
		<thead>
			<tr><th>Nom</th><th>Prénom</th><th>Actions</th></tr>
		</thead>
		<tbody>
			<s:iterator value="persons">
				<s:url id="normalEdit" action="personEditNormal">
					<s:param name="person" value="id"></s:param>
				</s:url>
				<s:url id="jsonEdit" action="personEditJson">
					<s:param name="person" value="id"></s:param>
				</s:url>
				<tr>
					<td><s:property value="lastname"/></td>
					<td><s:property value="firstname"/></td>
					<td>Edition <s:a href="%{normalEdit}">normal</s:a> <s:a href="%{jsonEdit}">ajax</s:a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</body>
</html>