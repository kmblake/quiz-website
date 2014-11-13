package quiz;

public class QuestionResponse extends Question {

	String questionType = "question_response";
	
	public QuestionResponse(int questionID, int questionNumber) {
		
	}
	
	@Override
	public String getType() {
		return questionType;
	}

	@Override
	public String getQuestionText() {
		// TODO Auto-generated method stub
		return null;
	}

}
