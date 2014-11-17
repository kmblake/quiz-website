<%@ page import ="quiz.MultipleChoice" %>
<script>
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
</script>
<form action="AddQuestionServlet" method="post">
	<input type="hidden" name="question-type" value="<%= MultipleChoice.type_id %>">
	<input id="done-input" type="hidden" name="done" value="0">
	<input id="answer-count" type="hidden" name="num-answers" value="0">
	<ul class="stripped">
		<li class="form-item">Question:</li>
		<li class="form-item"><textarea id="textarea" style="font-weight: normal" rows="4" cols="79" name="question"></textarea>
		<li class="form-item center"><button class="btn btm-sm btn-default" type="button" onclick="addMCOption();">Add Option</button></li>
		<li class="form-item hidden">
			Correct Answer: 
			<select id="answer-select" name="correct-answer" class="form-control mc-options nobold">
			  <option value="" disabled selected>Select Option</option>
			</select>
		</li>
		<li class="form-item">
			<button class="btn btn-success" type="submit">Add Another Question</button>
			<button class="btn btn-primary align-right" type="button" onclick="doneEntering();">Done: Review Quiz</button>
		</li>
	</ul>
</form>