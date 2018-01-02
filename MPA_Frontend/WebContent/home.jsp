<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
function userLogin2(){
	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/IdentityManagement/rest/user/login/frankvogel2@web.de/test',
		datatype: 'text',
		success: function(){
				window.location.href = "http://localhost:8080/MPA_Frontend/home.jsp";
				document.getElementById("test").innerHTML = "Successfully logged in";
				}
		});
	
}
</script>
</head>
<body>

<p id="test">Hello World</p>
	<form>
		<p>Mail: <input type="text" name="mail">
		<p>Password: <input type="password" name="pw">
		<p>Phone: <input type="text" name="phoneNumber">
		<p>Country: <input type="text" name="country">
		<p>State: <input type="text" name="state">
		<p>ZipCode: <input type="text" name="zip">
		<p>City: <input type="text" name="city">
		<p>Street: <input type="text" name="street">
		<p>House number: <input type="text" name="housenumber">
		<p>First name: <input type="text" name="firstName">
		<p>Surname: <input type="text" name="surName">
		<p>Birthday: <input type="text" name="birthday">	
		<button onclick="userLogin2(); return false;">Login</button>
	</form>
	
	
	
</body>
</html>