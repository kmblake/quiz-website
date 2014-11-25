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
 * Servlet implementation class FriendRequestServlet
 */
@WebServlet("/FriendRequestServlet")
public class FriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendRequestServlet() {
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
		int src = Integer.parseInt(request.getParameter("source"));
		if (src == Friend.REQUEST) {
			int requested_by = Integer.parseInt(request.getParameter("requested_by"));
			int requested_for = Integer.parseInt(request.getParameter("requested_for"));
			try {
				Connection con = (Connection) ((DBConnection) getServletContext().getAttribute("connection")).getConnection();
				Friend.createRequest(con, requested_by, requested_for);
				RequestDispatcher dispatch = request.getRequestDispatcher("show_user.jsp?id=" + requested_for);
				dispatch.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (src == Friend.APPROVE) {
			int friendship_id = Integer.parseInt(request.getParameter("id"));
			Statement stmt = (Statement) getServletContext().getAttribute("statement");
			Friend.approveFriendship(stmt, friendship_id);
			RequestDispatcher dispatch = request.getRequestDispatcher("show_messages.jsp");
			dispatch.forward(request, response);
		}
	}

}
