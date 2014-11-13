<%@ page import ="quiz.QuestionResponse" %>
<form action="AddQuestionServlet" method="post">
	<input type="hidden" name="question-type" value="<%= QuestionResponse.type_id %>">
	<input id="done-input" type="hidden" name="done" value="0">
	<script> 
	function done() {
		$("#done-input").val("1");
		$("form").submit();
	};
	</script>
	<ul class="stripped">
		<li class="form-item">Question:</li>
		<li class="form-item"><textarea style="font-weight: normal" rows="4" cols="79" name="question"></textarea>
		<li class="form-item">Answer: <input type="text" class="answer-input" name="answer"></li>
		<li class="form-item">
			<button class="btn btn-success" type="submit">Add Another Question</button>
			<button class="btn btn-primary align-right" type="button" onclick="doneEntering();">Done: Review Quiz</button>
		</li>
	</ul>
</form>