

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quiz.FillInTheBlank;
import quiz.MultipleAnswer;
import quiz.MultipleChoice;
import quiz.PictureResponse;
import quiz.Question;
import quiz.QuestionResponse;
import quiz.Quiz;

/**
 * Servlet implementation class TakeQuizServlet
 */
@WebServlet("/TakeQuizServlet")
public class TakeQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TakeQuizServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	// Get method called when the quiz is one page
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	// Post method called when the quiz is multiple pages
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Quiz quiz = (Quiz) request.getAttribute("quiz");
		try {
			runPostMethod(quiz, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void runPostMethod(Quiz quiz, HttpServletResponse response) throws SQLException, IOException {
		boolean multiplePages = quiz.getIfHasMultiplePages();
		boolean practiceMode = quiz.getIfPracticeMode();
		
		printHeaderInfo(quiz, response);
		
		ArrayList<Question> questions = quiz.getQuestions();
		
		printAQuestion(questions.get(0), response);
		
		if(multiplePages) {
			boolean immediateFeedback = quiz.getIfImmediateFeedback();
		} else {
			for(int i=1;i<questions.size();i++) {
				printAQuestion(questions.get(i), response);
			}
		}
	}

	private void printHeaderInfo(Quiz quiz, HttpServletResponse response) throws IOException, SQLException {
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");

		out.println("<title>" + quiz.getTitle() + "</title>");
		out.println("</head>");
		out.println("<body>");
	}
	
	private void printFooterInfo(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("</body>");
		out.println("</html>");
		
	}

	private void printAQuestion(Question question, HttpServletResponse response) throws IOException, SQLException {

		PrintWriter out = response.getWriter();
		
		out.println("<h1>Question" + question.getQuestionNumber() + "</h1>");

		String questionType = question.getType();
		
		if(questionType.equals("question_response")) {
			printQuestionResponse(question);
		} else if(questionType.equals("fill_in_the_blank")) {
			printQuestionFillInTheBlank(question);
		} else if(questionType.equals("multiple_choice")) {
			printQuestionMultipleChoice(question);
		} else if(questionType.equals("picture_response")) {
			printQuestionPictureResponse(question);
		} else if(questionType.equals("multiple_answer")) {
			printQuestionMultipleAnswer(question);
		}
	}
	
	private void printQuestionResponse(Question question) {
		
	}
	
	private void printQuestionFillInTheBlank(Question question) {
		
	}
	
	private void printQuestionMultipleChoice(Question question) {
		
	}
	
	private void printQuestionPictureResponse(Question question) {
		
	}
	
	private void printQuestionMultipleAnswer(Question question) {
		
	}

}
