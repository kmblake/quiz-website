package quiz;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class DBListener
 *
 */
@WebListener
public class ContextInitializer implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ContextInitializer() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	DBConnection connection = new DBConnection();
    	AccountManager manager = new AccountManager(connection);
        ServletContext context = arg0.getServletContext();
        context.setAttribute("manager", manager);
        context.setAttribute("connection", connection);
        context.setAttribute("statement", connection.getStatement());
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
        ServletContext context = arg0.getServletContext();
        DBConnection connection = (DBConnection)context.getAttribute("connection");
        connection.close();
    }
	
}
