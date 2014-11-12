package quiz;

import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizServlet() {
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
		Statement stmt = (Statement) getServletContext().getAttribute("statement");
		DBConnection c = (DBConnection) getServletContext().getAttribute("connection");
		Connection con = c.getConnection();
		String username = (String) request.getSession().getAttribute("user"); //TODO: Ask Bryant about this
		if (username == null) username = "jdoe"; //TODO: DELETE THIS LINE
//		request.setAttribute("SQLerror", "Oops, it looks like there was an error and your quiz didn't save.  Please try again.");
//		RequestDispatcher dispatch = request.getRequestDispatcher("create_quiz.jsp");
//		dispatch.forward(request, response);
		try {
			
			Quiz q = new Quiz(con, request.getParameter("title"), request.getParameter("description"), username,
					request.getParameter("randomized") != null, request.getParameter("multiple_pages") != null,
					request.getParameter("immediate_feedback") != null, request.getParameter("practice_mode") != null);
//			QuestionType[] questionTypes = QuestionType.getQuestionTypes(stmt);
//			request.setAttribute("question_types", questionTypes);
			RequestDispatcher dispatch = request.getRequestDispatcher("add_quiz_question.jsp");
			dispatch.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("SQLerror", "Oops, it looks like there was an error and your quiz didn't save.  Please try again.");
			RequestDispatcher dispatch = request.getRequestDispatcher("create_quiz.jsp");
			dispatch.forward(request, response);
		}
		
	}

}
