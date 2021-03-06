<!DOCTYPE html>
<html lang="en">
<head>
<!-- Theme Made By www.w3schools.com - No Copyright -->
<title>Bootstrap Theme Simply Me</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css?family=Montserrat"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
body {
	font: 20px Montserrat, sans-serif;
	line-height: 1.8;
	color: #f5f6f7;
}

p {
	font-size: 16px;
}

.margin {
	margin-bottom: 45px;
}

.bg-1 {
	background-color: #1abc9c; /* Green */
	color: #ffffff;
}

.bg-2 {
	background-color: #474e5d; /* Dark Blue */
	color: #ffffff;
}

.bg-3 {
	background-color: #ffffff; /* White */
	color: #555555;
}

.bg-4 {
	background-color: #2f2f2f; /* Black Gray */
	color: #fff;
}

.container-fluid {
	padding-top: 70px;
	padding-bottom: 70px;
}

.navbar {
	padding-top: 15px;
	padding-bottom: 15px;
	border: 0;
	border-radius: 0;
	margin-bottom: 0;
	font-size: 12px;
	letter-spacing: 5px;
}

.navbar-nav  li a:hover {
	color: #1abc9c !important;
}
</style>
</head>
<body>

	<!-- First Container -->
	<%
		int contractIterator = 1;
		int userIterator = 1;
		while (request.getParameter("contractId" + contractIterator) != null) {
			String contractId = request.getParameter("contractId" + contractIterator);
			String subject = request.getParameter("subject" + contractIterator);
	%>
	
	<div class="container-fluid bg-1 text-center">
User proposals for contract
			<%=subject%>
			(No.
			<%=contractId%>)
		<h3 class="margin">
			
		</h3>
		<%
			while (request.getParameter("userId" + contractIterator + userIterator) != null) {
					String userId = request.getParameter("userId" + contractIterator + userIterator);
		%>
		<div class="container-fluid">
		<div class="col-sm-4 col-xs-12">
			<div class="panel panel-default text-center">
				<div class="panel-heading">
					<h1>
						User no.
						<%=userId%></h1>
				</div>
				<div class="panel-body">
					rong>Endless</strong> Amet
					</p>
				</div>
				<div class="panel-footer">
					<a
						href="https://localhost:8443/IdentityManagement/rest/user/user/<%=userId%>"
						class="btn btn-default btn-lg"> <span
						class="glyphicon glyphicon-group"></span> Go!
					</a>
				</div>
			</div>
		</div>
		<% userIterator++;
			}%>
		</div>
		<%
				contractIterator++;
				userIterator = 1;
			}
		%>
	</div>


	<!-- Second Container -->
	<div class="container-fluid bg-2 text-center">
		<h3 class="margin">What Am I?</h3>
		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
			eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim
			ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
			aliquip ex ea commodo consequat.</p>
		<a href="#" class="btn btn-default btn-lg"> <span
			class="glyphicon glyphicon-search"></span> Search
		</a>
	</div>

	<!-- Third Container (Grid) -->
	<div class="container-fluid bg-3 text-center">
		<h3 class="margin">Where To Find Me?</h3>
		<br>
		<div class="row">
			<div class="col-sm-4">
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
					do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
				<img src="birds1.jpg" class="img-responsive margin"
					style="width: 100%" alt="Image">
			</div>
			<div class="col-sm-4">
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
					do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
				<img src="birds2.jpg" class="img-responsive margin"
					style="width: 100%" alt="Image">
			</div>
			<div class="col-sm-4">
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
					do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
				<img src="birds3.jpg" class="img-responsive margin"
					style="width: 100%" alt="Image">
			</div>
		</div>
	</div>

	<!-- Footer -->
	<footer class="container-fluid bg-4 text-center">
		<p>
			Bootstrap Theme Made By <a href="https://www.w3schools.com">www.w3schools.com</a>
		</p>
	</footer>

</body>
</html>
