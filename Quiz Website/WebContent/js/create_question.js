// When the page is fully loaded...
$(document).ready(function() {

	$( "#question-selector" ).change(function() {
		var questionType = $( "#question-selector" ).val();
		// alert( "Selection changed to " + questionType );
		// var questionNum = $("#form-container").data("question-num");

		$.get('QuestionFormServlet', {"question-type": questionType},
			function(resp) { // on success
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

function doneEntering() {
	$("#done-input").val("1");
	$("form").submit();
};

function addBlank() {
	$("#textarea").val($("#textarea").val() + "//blank//"); 
}

var last = 3;
var numOptions = 0;
function addOption() {
	this.numOptions++;
	$(".stripped li:nth-child(" + last + ")").after('<li class="form-item">Answer: <input type="text" class="answer-input name="answer-' + this.numOptions + '"></li>');
	this.last++;
	$("#answer-count").val(numOptions);
}
