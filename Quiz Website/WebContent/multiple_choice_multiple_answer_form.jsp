<%@ page import ="quiz.MultipleChoiceMultipleAnswer" %>
<script>
var lastLi = 2;
var numOptions = 0;
function addMCOption() {
	this.numOptions++;
	$(".stripped li:nth-child(" + this.lastLi + ")")
			.after(
					'<li class="form-item">Option '
							+ this.numOptions + ': <span class="nobold">Correct <input type="checkbox" name="correct" value="'
							+ this.numOptions
							+ '"></span></li><li class="form-item nobold"><textarea id="textarea" rows="4" cols="79" name="option-'
							+ this.numOptions + '"></textarea>');
	this.lastLi+= 2;
	$("#answer-count").val(this.numOptions);
}
</script>
<form action="AddQuestionServlet" method="post" onsubmit="return checkMultipleAnswerForm()">
	<input type="hidden" name="question-type" value="<%= MultipleChoiceMultipleAnswer.type_id %>">
	<input id="done-input" type="hidden" name="done" value="0">
	<input id="answer-count" type="hidden" name="num-answers" value="0">
	<ul class="stripped">
		<li class="form-item">Question:</li>
		<li class="form-item"><textarea id="textarea" style="font-weight: normal" rows="4" cols="79" name="question"></textarea>
		<li class="form-item center"><button class="btn btm-sm btn-default" type="button" onclick="addMCOption();">Add Option</button></li>
		<li class="form-item">
			<button class="btn btn-success" type="submit">Add Another Question</button>
			<button class="btn btn-primary align-right" type="button" onclick="doneEntering();">Done: Review Quiz</button>
		</li>
	</ul>
</form>