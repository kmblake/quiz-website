<%@ page import ="quiz.PictureResponse" %>
<form action="AddQuestionServlet" method="post">
	<input type="hidden" name="question-type" value="<%= PictureResponse.type_id %>">
	<input id="done-input" type="hidden" name="done" value="0">
	<ul class="stripped">
		<li class="form-item">Picture URL: 
			<span style="font-weight: normal">Paste the URL of the picture you want to include here </span>
		</li>
		<li class="form-item"><textarea id="textarea" style="font-weight: normal" rows="4" cols="79" name="question"></textarea>
		<li class="form-item center"><button class="btn btm-sm btn-default" type="button" onclick="previewImage();">Preview Image</button></li>
		<li class="form-item">Answer: <input type="text" class="answer-input" name="answer"></li>
		<li class="form-item">
			<button class="btn btn-success" type="submit">Add Another Question</button>
			<button class="btn btn-primary align-right" type="button" onclick="doneEntering();">Done: Review Quiz</button>
		</li>
	</ul>
</form>