// When the page is fully loaded...
$(document).ready(function() {

	$("#question-selector").change(function() {
		var questionType = $("#question-selector").val();
		// alert( "Selection changed to " + questionType );
		// var questionNum = $("#form-container").data("question-num");

		$.get('QuestionFormServlet', {
			"question-type" : questionType
		}, function(resp) { // on success
			updateForm(resp);
		}).fail(function() { // on failure
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

var lastLi = 2;
var lastOption = 1;
var numOptions = 0;
function addMCOption() {
	this.numOptions++;
	if (this.numOptions == 1) {
		var selector = this.lastLi + 1;
		$(".hidden").removeClass("hidden");
	}
	$(".stripped li:nth-child(" + this.lastLi + ")")
			.after(
					'<li class="form-item">Option '
							+ this.numOptions
							+ ':</li><li class="form-item nobold"><textarea id="textarea" rows="4" cols="79" name="option-'
							+ this.numOptions + '"></textarea>');
	$("#answer-select option:nth-child(" + this.lastOption + ")").after(
			'<option value="' + this.numOptions + '">Option ' + this.numOptions
					+ ' </option>');
	this.lastLi+= 2;
	this.lastOption++;
	$("#answer-count").val(this.numOptions);
}
