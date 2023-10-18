$(document).ready(
	function() {
		// Unique email veification
		$("#email").on('blur', function(event) {
			event.preventDefault();
			var enteredEmail = $("#email").val();
			$.ajax({
				url: "/validateEmail",
				data: "email=" + enteredEmail,
				success: function(result) {
					if (result == 'Duplicate') {
						$("#emailMsg").html("Email already registered.!!");
						$("#email").focus();
						$("#createAccBtn").prop("disabled", true);
					} else {
						$("#emailMsg").html("");
						$("#createAccBtn").prop("disabled", false)
					}
				}
			})
		})



		// GET REQUEST
		$("#countryList").on('change', function(event) {
			event.preventDefault();
			var countryId = $('#countryList option:selected').val();
			$.ajax({
				type: 'GET',
				url: 'http://localhost:8085/loadStatesByCountry/' + countryId,
				success: function(result) {
					var result = JSON.parse(result);
					if (result.length > 0) {
						var s = '';
						s += '<option value="">--state--</option>';
						for (var i = 0; i < result.length; i++) {
							s += '<option value="' + result[i].id + '">' + result[i].name + '</option>';
						} $('#stateList').html(s);
					}
				}
			});
		});
		
		$("#stateList").on('change', function(event) {
			event.preventDefault();
			var stateId = $('#stateList option:selected').val();
			$.ajax({
				type: 'GET',
				url: 'http://localhost:8085/loadCitiesByState/' + stateId,
				success: function(result) {
					var result = JSON.parse(result);
					if (result.length > 0) {
						var s = '';
						s += '<option value="">--city--</option>';
						for (var i = 0; i < result.length; i++) {
							s += '<option value="' + result[i].id + '">' + result[i].name + '</option>';
						} $('#cityList').html(s);
					}
				}
			});
		});

	})
