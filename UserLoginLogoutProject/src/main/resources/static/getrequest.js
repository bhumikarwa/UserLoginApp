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
				url: "http://localhost:8085/getState",
				data: "countryId=" + countryId,
				success: function(result) {
					if (result.status == "success") {
					alert("result");
					}
					if (result != null) {
						var s = '';
						s += '<option th:each="state:${' + result.object + '}" th:value="${' + state.id + '}" th:text="${' + state.name + '}"></option>'
						$('#stateList').html(s);
					}
				}
			});
		});

	})
