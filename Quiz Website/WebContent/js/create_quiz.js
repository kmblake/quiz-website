$(document).ready(function() {
	var multiple_pages = $( "input[name='multiple_pages']" );
	var practice_mode = $( "input[name='practice_mode']" );
	var immediate_feedback = $( "input[name='immediate_feedback']" );
	immediate_feedback.prop("disabled", true);
	practice_mode.prop("disabled", true);
	immediate_feedback.change(function() {
		if (practice_mode.prop("disabled") && !immediate_feedback.prop("disabled")) {
			practice_mode.prop("disabled", false);
		} else {
			practice_mode.prop("disabled", true);
			practice_mode.prop("checked", false);
		}
		practice_mode.parent().toggleClass("disabled");
	});
	
	multiple_pages.change(function() {
		if (immediate_feedback.prop("disabled")) {
			immediate_feedback.prop("disabled", false);
		} else {
			if (!practice_mode.prop("disabled")) {
				practice_mode.prop("disabled", true);
				practice_mode.parent().addClass("disabled");
				practice_mode.prop("checked", false);
			}
			immediate_feedback.prop("disabled", true);
			immediate_feedback.prop("checked", false);
		}
		immediate_feedback.parent().toggleClass("disabled");
	});
});

function toggleCheckbox(checkbox) {
	if (checkbox.prop("disabled")) {
		checkbox.prop("disabled", false)
	} else {
		checkbox.prop("disabled", true)
	}
	checkbox.parent().toggleClass("disabled");
}