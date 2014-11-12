// When the page is fully loaded...
$(document).ready(function() {

	$( "#question-selector" ).change(function() {
		var questionType = $( "#question-selector" ).val();
		// alert( "Selection changed to " + questionType );
		var quizId = $(".container").data("quiz-id");

		$.get('QuestionFormServlet', {"question-type": questionType, "quiz-id": quizId},
			function(resp) { // on sucess
				updateForm(resp);
			})
			.fail(function() { // on failure
				alert("Request failed.");
			});
	});  
});


function updateForm(data) {
	$("#form-container").html(data);
};