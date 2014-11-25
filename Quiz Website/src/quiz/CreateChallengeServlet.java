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
 * Servlet implementation class CreateChallengeServlet
 */
@WebServlet("/CreateChallengeServlet")
public class CreateChallengeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateChallengeServlet() {
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
		int challenged_by = Integer.parseInt(request.getParameter("challenged_by"));
		int challenged_user = Integer.parseInt(request.getParameter("challenged_user"));
		int quiz_id = Integer.parseInt(request.getParameter("quiz"));
		try {
			Connection con = (Connection) ((DBConnection) getServletContext().getAttribute("connection")).getConnection();
			Challenge.createChallenge(con, challenged_user, challenged_by, quiz_id);
			request.setAttribute("notification", "Your challenge has been issued!");
			RequestDispatcher dispatch = request.getRequestDispatcher("show_user.jsp?id=" + challenged_user);
			dispatch.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

}
