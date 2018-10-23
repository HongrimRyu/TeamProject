package tourpage.shop.action;

public class ActionForward {
	// 이동정보 저장 객체( 이동할 페이지주소, 이동할 방식 )
	
	// 멤버변수  path : 이동할 페이지주소
	private String path;
	// 멤버변수 isRedirect : 이동할 방식
	// true - sendRedirect 페이지 이동
	// false - forward 페이지 이동 
	private boolean isRedirect;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	

}
