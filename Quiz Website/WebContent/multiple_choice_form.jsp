<!--Add class value-->
<form action="AddQuestionServlet" method="post">
	<input type="hidden" name="question-type" value="3">
	<input id="done-input" type="hidden" name="done" value="0">
	<input id="answer-count" type="hidden" name="num-answers" value="0">
	<ul class="stripped">
		<li class="form-item">Question:</li>
		<li class="form-item"><textarea id="textarea" style="font-weight: normal" rows="4" cols="79" name="statement"></textarea>
		<li class="form-item"><button class="btn btm-sm btn-default" type="button" onclick="addMCOption();">Add Option</button></li>
		<li class="form-item">
			<button class="btn btn-success" type="submit">Add Another Question</button>
			<button class="btn btn-primary align-right" type="button" onclick="doneEntering();">Done: Review Quiz</button>
		</li>
	</ul>
</form>