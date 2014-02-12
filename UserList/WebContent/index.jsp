<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User List</title>
<style type="text/css">
    .container {
        width: 200px;
        clear: both;
    }
    .container input {
        width: 100%;
        clear: both;
    }

    </style>
</head>
<body>
	
	<% out.println("<h1>User List Application</h1>"); %>
	<hr>
		<form id = fr_add name = fr_add method = get action = "ServletController">
			<div class = "container">
				First name: <input type = "text" value = "" name = f_name><br>
				
				 Last name: <input type = "text" value = "" name = l_name><br>
				 
				    E-mail: <input type = "text" value = "" name = email><br>
			  </div>  
			<input type = "submit" name = action value = "Add">
			<input type = "submit" name = action value = "Show all">
		</form>
	<hr>
	
	<table>
		<tr>
			
			<th>First Name</th>
			<th>Last Name</th>
			<th>E-mail</th>

		</tr>
		
		<tr>
		
			<c:forEach var = "user" items = "${userlist}">
				<tr>
					<td> <c:out value = "${user.firstName}"/> </td>
					<td> <c:out value = "${user.lastName}"/> </td>
					<td> <c:out value = "${user.email}"/> </td>
					<td>
						<a href = "ServletController?action=delete&userMail=
						<c:out value = "${user.email}"/>">Delete</a>
					</td>
				</tr>
			</c:forEach>
		
		</tr>
	</table>
	
</body>
</html>