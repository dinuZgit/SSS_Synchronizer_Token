package synchro_token;

import synchro_token.Database;
import synchro_token.LamdasClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet ("/LoginController")
public class LoginController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Map <String, String> csrfTokenStore = new HashMap<String, String>();
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at : ").append(request.getContextPath());
	}
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession(true);
		
		if (Database.isValidUser(username, password)) {
			
			String csrfToken = generateCSRFToken (session.getId());
			csrfTokenStore.put(session.getId(), csrfToken);
			
			response.addCookie(LamdasClass.COOKIE_SESSION_ID.apply(session));
			session.removeAttribute("invalidCredentials");
			response.sendRedirect("./HomePage.jsp");
			
		}
		
		else {
			
			session.setAttribute("invalidCredentials", "Not_ok");
			response.sendRedirect("./LoginPage.jsp");
		}
		
	}
	
	private String generateCSRFToken (String strClearText) {
		
		return strClearText + "." + getRandomString();
	}
	
	private String getRandomString () {
		
		UUID randomUuid = UUID.randomUUID();
		return randomUuid.toString();
	}

}
