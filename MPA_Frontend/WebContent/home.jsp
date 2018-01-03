<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
function getFormData(data) {
	   var unindexed_array = data;
	   var indexed_array = {};

	   $.map(unindexed_array, function(n, i) {
	    indexed_array[n['name']] = n['value'];
	   });

	   return indexed_array;
	}
function userLogin2(){	
	//var formData = JSON.stringify($("#userData").serializeArray());
	var formData = $('#userData').serializeArray();
	$.ajax({
		type: 'POST',
		dataType : 'json', 
		data: JSON.stringify(getFormData(formData)),
		url: 'https://localhost:8443/IdentityManagement/rest/user/registerPU',
	    contentType: 'application/json',
		success: function(){
				//window.location.href = "http://localhost:8080/MPA_Frontend/home.jsp";
				document.getElementById("test").innerHTML = "Successfully registered";
				}
		});
	
}
</script>
</head>
<body>

<p id="test">Hello World</p>
	<form name="userData" id="userData">
		<p>Mail: <input type="text" name="mailAddress" id="mailAddress" value="frankvogel2@web.de">
		<p>Password: <input type="password" name="password" id="password" value="testPW">
		<p>Phone: <input type="text" name="phoneNumber" id="phoneNumber" value="1234567">
		<p>Country: <input type="text" name="country" id="country" value="Germany">
		<p>State: <input type="text" name="state" id="state" value="Baden-Wuerttemberg">
		<p>ZipCode: <input type="text" name="zipCode" id="zipCode" value="74172">
		<p>City: <input type="text" name="city" id="city" value="Neckarsulm">
		<p>Street: <input type="text" name="street" id="street" value="BP 1">
		<p>House number: <input type="text" name="houseNumber" id="houseNumber" value="1">
		<p>First name: <input type="text" name="firstName" id="firstName" value="Frank">
		<p>Surname: <input type="text" name="surName" id="surName" value="Vogel">
		<p>Birthday: <input type="text" name="birthday" id="birthday" value="01.01.1992">	
		<button onclick="userLogin2(); return false;">Register</button>
	</form>
	
	
	
</body>
</html>