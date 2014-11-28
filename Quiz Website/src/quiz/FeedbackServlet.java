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
		RequestDispatcher dispatch = null;
		if (!multiple) {
			for (Question q : questions) {
				String questionID = Integer.toString(q.getQuestionID());
				String answer = (String)request.getParameter(questionID);
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
			String answer = (String)request.getParameter(questionID);
			session.setAttribute(questionID, answer);
			if (q.isCorrect(answer)) {
				correct++;
			}
			session.setAttribute("score", (Integer)session.getAttribute("score") + correct);

			boolean immediate = (Boolean)session.getAttribute("immediate");
			if(curr_question + 1 >= quiz.getLength()) {
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

	private int getTimeElapsed(HttpSession session) {
		long time_started = (Long)session.getAttribute("time_started");
		long time_elapsed = System.currentTimeMillis() - time_started;
		return (int)(time_elapsed / 1000);
	}
	
	private int computeScore(HttpServletRequest request, Quiz quiz) {

		/*int correct = 0;
		HttpSession session = request.getSession();

		for (Question q : questions) {
			String questionID = Integer.toString(q.getQuestionID());
			String answer = (String)request.getParameter(questionID);
			session.setAttribute(questionID, answer);
			if (q.isCorrect(answer)) {
				correct++;
			}
		}
		return correct;*/
		return 0;
	}

}
