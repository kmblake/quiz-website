<%@ page import ="quiz.QuestionResponse" %>
<script>
var lastLi = 3;
var numOptions = 1;
function addMCOption() {
	this.numOptions++;
	if (this.numOptions == 1) {
		var selector = this.lastLi + 1;
		$(".hidden").removeClass("hidden");
	}
	$(".stripped li:nth-child(" + this.lastLi + ")")
			.after(
					'<li class="form-item">Answer '
							+ this.numOptions
							+ ': <input type="text" class="multi-answer-input" name="option-' + this.numOptions + '"></li>');
	this.lastLi++;
	$("#answer-count").val(this.numOptions);
}
</script>
<form action="AddQuestionServlet" method="post">
	<input type="hidden" name="question-type" value="<%= QuestionResponse.type_id %>">
	<input id="done-input" type="hidden" name="done" value="0">
	<input id="answer-count" type="hidden" name="num-answers" value="1">
	<ul class="stripped">
		<li class="form-item">Question:</li>
		<li class="form-item"><textarea id="textarea" style="font-weight: normal" rows="4" cols="79" name="question"></textarea>
		<li class="form-item">Answer 1: <input type="text" class="multi-answer-input" name="option-1"></li>
		<li class="form-item center"><button class="btn btm-sm btn-default" type="button" onclick="addMCOption();">Add Answer</button></li>
		<li class="form-item">
			<button class="btn btn-success" type="submit">Add Another Question</button>
			<button class="btn btn-primary align-right" type="button" onclick="doneEntering();">Done: Review Quiz</button>
		</li>
	</ul>
</form>