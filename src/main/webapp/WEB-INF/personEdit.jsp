<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit person</title>
</head>
<body>
	<form method="post">
		<s:actionerror/>
		Prénom: <s:textfield name="person.firstname" /> <s:fielderror fieldName="person.lastname" /><br />
		Nom:    <s:textfield name="person.lastname" /> <s:fielderror fieldName="person.firstname"/><br />
		<s:submit method="save"></s:submit>
	</form>
</body>
</html>