package quiz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuestionResponseServlet
 */
@WebServlet("/AddQuestionServlet")
public class AddQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddQuestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBConnection c = (DBConnection) getServletContext().getAttribute("connection");
		Connection con = c.getConnection();
		int questionType = Integer.parseInt(request.getParameter("question-type"));
		int questionNumber = (Integer) request.getSession().getAttribute("question-num");
		int quizId = (Integer) request.getSession().getAttribute("quiz-id");
		try {
			switch (questionType) {
			case QuestionResponse.type_id: 
				QuestionResponse.storeQuestion(con, questionNumber, quizId, request.getParameter("question"), request.getParameter("answer"));
				break;
			}
			questionNumber++;
			request.getSession().setAttribute("question-num", questionNumber);
			int done = Integer.parseInt(request.getParameter("done"));
			RequestDispatcher dispatch;
			if (done == 0) {
				dispatch = request.getRequestDispatcher("add_quiz_question.jsp");
			} else {
				request.getSession().removeAttribute("question-num");
				dispatch = request.getRequestDispatcher("show_quiz.jsp");
			}
			dispatch.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
