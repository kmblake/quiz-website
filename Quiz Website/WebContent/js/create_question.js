// When the page is fully loaded...
$(document).ready(function() {

	$("#question-selector").change(function() {
		var questionType = $("#question-selector").val();

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

function previewImage() {
	var url = $("#textarea").val();
	$("#image-container").html('<img id="question-image" src="' + url + '" alt="Uploaded Image" onerror="loadError()">');
}

function loadError() {
	$("#image-container").html('<div class="alert alert-danger">Cannot load image.  Please check the image URL.</div>');
}

function checkMultipleAnswerForm(event) {
	var searchIDs = $("input:checkbox:checked").map(function(){
	      return $(this).val();
	    }).get();
	if (searchIDs.length > 1) {
		return true;
	} else {
		$("#image-container").html('<div class="alert alert-danger">You must select at least two correct answers.</div>');
		return false;
	}
	console.log(searchIDs.length);
	    
}
