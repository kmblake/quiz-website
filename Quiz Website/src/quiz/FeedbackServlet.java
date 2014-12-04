package quiz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FeedbackServlet
 */
@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FeedbackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Quiz quiz = (Quiz)session.getAttribute("quiz");

		int correct = 0;

		ArrayList<Question> questions = quiz.getQuestions();
		session.setAttribute("questions", questions);

		boolean multiple = (Boolean)session.getAttribute("multiple");
		boolean practice = (Boolean)session.getAttribute("practice");
		RequestDispatcher dispatch = null;
		if (!multiple) {
			for (Question q : questions) {
				String questionID = Integer.toString(q.getQuestionID());
				Object answer = null;
				if (!q.getType().equals("multiple_choice_multiple_answer"))
					answer = request.getParameter(questionID);
				else {
					answer = getMultipleAnswers(request, q);
				}
				session.setAttribute(questionID, answer);
				if (q.isCorrect(answer)) {
					correct++;
				}
			}
			int score = correct;
			session.setAttribute("score", score);
			session.setAttribute("time_elapsed", getTimeElapsed(session));
			dispatch = request.getRequestDispatcher("feedback.jsp");
			dispatch.forward(request, response);	
		}
		else {
			Integer curr_question = (Integer)session.getAttribute("current_question");
			Question q = questions.get(curr_question);
			String questionID = Integer.toString(q.getQuestionID());
			Object answer = null;
			if (!q.getType().equals("multiple_choice_multiple_answer"))
				answer = request.getParameter(questionID);
			else {
				answer = getMultipleAnswers(request, q);
			}
			session.setAttribute(questionID, answer);
			if (q.isCorrect(answer)) {
				correct++;
			}
			session.setAttribute("score", (Integer)session.getAttribute("score") + correct);

			boolean immediate = (Boolean)session.getAttribute("immediate");
			if(curr_question + 1 >= quiz.getLength() && !(immediate && practice)) {
				session.setAttribute("time_elapsed", getTimeElapsed(session));
				dispatch = request.getRequestDispatcher("feedback.jsp");
			}
			else {
				if (immediate) {
					dispatch = request.getRequestDispatcher("immediate_feedback.jsp");
				}
				else {
					session.setAttribute("current_question", curr_question + 1);
					dispatch = request.getRequestDispatcher("take_quiz.jsp");
				}
			}
			dispatch.forward(request, response);

		}
	}

	private ArrayList<String> getMultipleAnswers(HttpServletRequest request, Question q) {
		int numOptions = ((MultipleChoiceMultipleAnswer)q).numOptions();
		ArrayList<String> userAnswers = new ArrayList<String>();
		for (int i = 0; i < numOptions; i++) {
			String a = request.getParameter(q.getQuestionID() + "-" + i);
			if (a != null)
				userAnswers.add(a);		
		} 
		return userAnswers;
	}

	private long getTimeElapsed(HttpSession session) {
		long time_started = (Long)session.getAttribute("time_started");
		long time_elapsed = System.currentTimeMillis() - time_started;
		return time_elapsed;
	}

}
