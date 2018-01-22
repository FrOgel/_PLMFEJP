
//JSON ERRAY
function getFormData(data) {
	   var unindexed_array = data;
	   var indexed_array = {};

	   $.map(unindexed_array, function(n, i) {
	    indexed_array[n['name']] = n['value'];
	   });

	   return indexed_array;
	}

  function userLogin(){
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
