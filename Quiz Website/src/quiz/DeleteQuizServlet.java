package quiz;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteQuizServlet
 */
@WebServlet("/DeleteQuizServlet")
public class DeleteQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteQuizServlet() {
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
		String[] idStrings = request.getParameterValues("quiz");
		DBConnection con = (DBConnection) getServletContext().getAttribute("connection");
		try {
			for (String id : idStrings) {
				Quiz q = new Quiz(Integer.parseInt(id), con);
				q.deleteQuiz();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("error", "Number Format Error");
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "SQL Error");
		} finally {
			RequestDispatcher dispatch = request.getRequestDispatcher("delete_quiz.jsp");
			dispatch.forward(request, response);
		}
	}

}
