package synchro_token;

import java.util.function.Function;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;



public class LamdasClass {

	public static final Function<HttpSession, Cookie>
	COOKIE_SESSION_ID = session -> new Cookie ("SessionID", session.getId());

}
