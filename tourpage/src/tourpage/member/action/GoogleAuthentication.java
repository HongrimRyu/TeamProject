package tourpage.member.action;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class GoogleAuthentication extends Authenticator{
	PasswordAuthentication passAuth;
	
	public GoogleAuthentication() {
		passAuth = new PasswordAuthentication("itwilltest1", "itwill1!");
	}
	
	protected PasswordAuthentication getPasswordAuthentication() {
		return passAuth;
	}
}
