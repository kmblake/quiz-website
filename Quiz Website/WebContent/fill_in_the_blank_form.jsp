<!--Add class value-->
<form action="AddQuestionServlet" method="post">
	<input type="hidden" name="question-type" value="2">
	<input id="done-input" type="hidden" name="done" value="0">
	<ul class="stripped">
		<li class="form-item">Statement: 
			<span style="font-weight: normal">Click the blank button to enter the blank where you want it.  Only one blank per statement is supported! </span>
		</li>
		<li class="form-item"><button class="btn btm-sm btn-default" type="button" onclick="addBlank();">Add Blank</button></li>
		<li class="form-item"><textarea id="textarea" style="font-weight: normal" rows="4" cols="79" name="statement"></textarea>
		<li class="form-item">Answer: <input type="text" class="answer-input" name="answer"></li>
		<li class="form-item">
			<button class="btn btn-success" type="submit">Add Another Question</button>
			<button class="btn btn-primary align-right" type="button" onclick="doneEntering();">Done: Review Quiz</button>
		</li>
	</ul>
</form>