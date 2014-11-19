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
		int score = computeScore(request);
		request.setAttribute("score", score);
		RequestDispatcher dispatch = request.getRequestDispatcher("feedback.jsp");
		dispatch.forward(request, response);	
	}

	private int computeScore(HttpServletRequest request) {
		int id = (Integer)request.getAttribute("id");
		int correct = 0;
		DBConnection con = (DBConnection)request.getServletContext().getAttribute("connection");
		Quiz quiz = null;
		try {
			quiz = new Quiz(id, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<Question> questions = quiz.getQuestions();
		request.setAttribute("questions", questions);
		for (Question q : questions) {
			String questionID = Integer.toString(q.getQuestionID());
			String answer = (String)request.getAttribute(questionID);
			if (q.isCorrect(answer)) {
				correct++;
			}
		}
		//return correct;
		return 0;
	}

}
