<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
function createContract(){
	document.getElementById("authorized").innerHTML = "";
	var formData = $('#contract').serializeArray();
	$.ajax({
		type: 'POST',
		data: formData,
		url: 'https://localhost:8443/ContractManagement/rest/contract/createContract',
		contentType: 'application/x-www-form-urlencoded',
		success: function(){
				//window.location.href = "https://localhost:8080/MPA_Frontend/home.jsp";
				document.getElementById("authorized").innerHTML = "Successfully created in";
				}
		});
	
}
function createTask(){
	document.getElementById("authorized").innerHTML = "";
	var formData = $('#task').serializeArray();
	$.ajax({
		type: 'POST',
		data: formData,
		url: 'https://localhost:8443/ContractManagement/rest/contract/createTask',
		contentType: 'application/x-www-form-urlencoded',
		success: function(){
				//window.location.href = "https://localhost:8080/MPA_Frontend/home.jsp";
				document.getElementById("authorized").innerHTML = "Successfully created in";
				}
		});
	
}
function createBC(){
	document.getElementById("authorized").innerHTML = "";
	var formData = $('#basicCondition').serializeArray();
	$.ajax({
		type: 'POST',
		data: formData,
		url: 'https://localhost:8443/ContractManagement/rest/contract/createBasicCondition',
		contentType: 'application/x-www-form-urlencoded',
		success: function(){
				//window.location.href = "https://localhost:8080/MPA_Frontend/home.jsp";
				document.getElementById("authorized").innerHTML = "Successfully created BC";
				}
		});
	
}

function createRequirement(){
	document.getElementById("authorized").innerHTML = "";
	var formData = $('#requirement').serializeArray();
	$.ajax({
		type: 'POST',
		data: formData,
		url: 'https://localhost:8443/ContractManagement/rest/contract/createRequirement',
		contentType: 'application/x-www-form-urlencoded',
		success: function(){
				//window.location.href = "https://localhost:8080/MPA_Frontend/home.jsp";
				document.getElementById("authorized").innerHTML = "Successfully created requirement";
				}
		});
	
}

function createSC(){
	document.getElementById("authorized").innerHTML = "";
	var formData = $('#special').serializeArray();
	$.ajax({
		type: 'POST',
		data: formData,
		url: 'https://localhost:8443/ContractManagement/rest/contract/createSpecialCondition',
		contentType: 'application/x-www-form-urlencoded',
		success: function(){
				//window.location.href = "https://localhost:8080/MPA_Frontend/home.jsp";
				document.getElementById("authorized").innerHTML = "Successfully created requirement";
				}
		});
	
}
function deleteContract(){
	document.getElementById("authorized").innerHTML = "";
	var formData = $('#contractId').serializeArray();
	$.ajax({
		type: 'POST',
		data: formData,
		url: 'https://localhost:8443/ContractManagement/rest/contract/deleteContract',
		contentType: 'application/x-www-form-urlencoded',
		success: function(){
				//window.location.href = "https://localhost:8080/MPA_Frontend/home.jsp";
				document.getElementById("authorized").innerHTML = "Successfully deleted contract";
				}
		});
	
}
function deleteTask(){
	document.getElementById("authorized").innerHTML = "";
	var formData = $('#task').serializeArray();
	$.ajax({
		type: 'POST',
		data: formData,
		url: 'https://localhost:8443/ContractManagement/rest/contract/deleteTask',
		contentType: 'application/x-www-form-urlencoded',
		success: function(){
				//window.location.href = "https://localhost:8080/MPA_Frontend/home.jsp";
				document.getElementById("authorized").innerHTML = "Successfully deleted task";
				}
		});
	
}
function deleteBC(){
	document.getElementById("authorized").innerHTML = "";
	var formData = $('#task').serializeArray();
	$.ajax({
		type: 'POST',
		data: formData,
		url: 'https://localhost:8443/ContractManagement/rest/contract/deleteBasicCondition',
		contentType: 'application/x-www-form-urlencoded',
		success: function(){
				//window.location.href = "https://localhost:8080/MPA_Frontend/home.jsp";
				document.getElementById("authorized").innerHTML = "Successfully deleted task";
				}
		});
	
}
function deleteRequirement(){
	document.getElementById("authorized").innerHTML = "";
	var formData = $('#requirement').serializeArray();
	$.ajax({
		type: 'POST',
		data: formData,
		url: 'https://localhost:8443/ContractManagement/rest/contract/deleteRequirement',
		contentType: 'application/x-www-form-urlencoded',
		success: function(){
				//window.location.href = "https://localhost:8080/MPA_Frontend/home.jsp";
				document.getElementById("authorized").innerHTML = "Successfully deleted requirement";
				}
		});
	
}
function deleteSC(){
	document.getElementById("authorized").innerHTML = "";
	var formData = $('#special').serializeArray();
	$.ajax({
		type: 'POST',
		data: formData,
		url: 'https://localhost:8443/ContractManagement/rest/contract/deleteSpecialCondition',
		contentType: 'application/x-www-form-urlencoded',
		success: function(){
				//window.location.href = "https://localhost:8080/MPA_Frontend/home.jsp";
				document.getElementById("authorized").innerHTML = "Successfully deleted condition";
				}
		});
	
}
function getContracts(){
	
}
</script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<button onclick="getContracts(); return false;">Get all contracts</button>"
	<form name="contractId">
		<input type="hidden" name="contractId" id="contractId" value="1"/>
	</form>
	<form name="contract" id="contract">
		<p>Type <input type="text" name="contractType" id="contractType" list="contractTypeList">
			<datalist id="contractTypeList">
   		 		<option value="Consulting">
    			<option value="Development">
			</datalist>
		<p>Designation: <input type="text" name="designation" id="designation"/>
		<p>Subject: <input type="text" name="contractSubject" id="contractSubject"/>
		<button onclick="createContract(); return false;">Create contract</button>
		<button onclick="deleteContract(); return false;">Delete contract</button>
	</form>
	<p id="authorized"></p>
	<form name="task" id="task">
		<input type="hidden" name="taskId" id="taskId" value="1"/>
		<input type="hidden" name="contractId" id="contractId" value="1"/>
		<p>Task description: <input type="text" name="description" id="description"/>
		<p>Type <input type="text" name="taskType" id="taskType" list="typeList">
			<datalist id="typeList">
   		 		<option value="Functional">
    			<option value="Non_Functional">
			</datalist>
		<p>Type <input type="text" name="taskSubType" id="taskSubType" list="subTypeList">
			<datalist id="subTypeList">
   		 		<option value="Usability">
    			<option value="Reliability">
    			<option value="Performance">
    			<option value="Supportability">
    			<option value="Design constraint">
    			<option value="Implementation constraint">
    			<option value="Interface constraint">
    			<option value="Physical constraint">
			</datalist>
		<button onclick="createTask(); return false;">Add task</button>
		<button onclick="deleteTask(); return false;">Delete task</button>
	</form>
	<p id="task"></p>
	
	<p>Add your basic conditions</p>
	<form name="basicCondition" id="basicCondition">
		<input type="hidden" name="contractId" id="contractId" value="1"/>
		<p>startDate <input type="date" name="startDate" id="startDate"/>
 		<p>endDate <input type="date" name="endDate" id="endDate"/>
		<p>location <input type="text" name="location" id="location"/>
		<p>radius <input type="text" name="radius" id="radius"/>
		<p>Workload <input type="text" name="workload" id="workload"/>
		<button onclick="createBC(); return false;">Add basic conditions</button>
		<button onclick="deleteBC(); return false;">Delete basic conditions</button>
	</form>
	
	<p>Add your requirement</p>
	<form name="requirement" id="requirement">
		<input type="hidden" name="requirementId" id="requirementId" value="1"/>
		<input type="hidden" name="contractId" id="contractId" value="1"/>
		<p>Description: <input type="text" name="description" id="description"/>
		<button onclick="createRequirement(); return false;">Add requirement</button>
		<button onclick="deleteRequirement(); return false;">Delete requirement</button>
	</form>
	
	<p>Add your special condition</p>
	<form name="special" id="special">
		<input type="hidden" name="contractId" id="contractId" value="1"/>
		<input type="hidden" name="specialConId" id="specialConId" value="1"/>
		<p>Description: <input type="text" name="description" id="description"/>
		<button onclick="createSC(); return false;">Add condition</button>
		<button onclick="deleteSC(); return false;">Delete condition</button>
	</form>
</body>


</html>