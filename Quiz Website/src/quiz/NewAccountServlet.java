package quiz;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class NewAccountServlet
 */
@WebServlet("/NewAccountServlet")
public class NewAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewAccountServlet() {
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
		response.setContentType("text/html; charset=UTF-8");

		AccountManager manager = (AccountManager)this.getServletContext().getAttribute("manager");
		String first = request.getParameter("first");
		String last = request.getParameter("last");
		String username = request.getParameter("user");
		String password = request.getParameter("password");
		if (manager.accountExists(username)) {
			String error = "The user name <span class='bold'>" + username + "</span> is already in use.<br>Please choose another user name.";
			request.setAttribute("errorText", error);
			RequestDispatcher dispatch = request.getRequestDispatcher("account_save_error.jsp");
			dispatch.forward(request, response);
		}
		else {
			manager.createAccount(first, last, username, password);
			User currentUser;
			try {
				currentUser = new User(username, (Statement) getServletContext().getAttribute("statement"));
				HttpSession session = request.getSession();
				session.setAttribute("user", currentUser);
				RequestDispatcher dispatch = request.getRequestDispatcher("home.jsp");
				dispatch.forward(request, response);	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				String error = "Sorry, there was an error creating your account.<br>Please try again.";
				request.setAttribute("errorText", error);
				RequestDispatcher dispatch = request.getRequestDispatcher("account_save_error.jsp");
				dispatch.forward(request, response);
			}
			
		}
	}

}
