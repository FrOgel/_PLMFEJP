<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <!-- Theme Made By www.w3schools.com - No Copyright -->
  <title>Bootstrap Theme Company Page</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>
  .jumbotron {
      background-color: #f4511e;
      color: #fff;
  }
  </style>
</head>
<body>
<div class="jumbotron text-center">
  <h1>MPA - Thank you for your registration!</h1> 
  <p>In order to complete your registration please click the link below:</p> 
  <a href="https://localhost:8443/IdentityManagement/rest/user/verify/<%= request.getParameter("id") %>/<%= request.getParameter("hash") %>">Verify!</a>
</div>
</body>
</html>
