package tourpage.group.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	// 추상메서드 구현
	 public ActionForward execute(HttpServletRequest request,
			 HttpServletResponse response) throws Exception ;

}
