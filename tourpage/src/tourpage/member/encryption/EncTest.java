package tourpage.member.encryption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class EncTest {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	// 1단계 드라이버 로드, 2단계 디비연결 작업 메서드 생성
	// getConnection() 메서드 - Connection 리턴
	private Connection getConnection() throws Exception {
		/*String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/jspdb";
		String id = "jspid";
		String pass = "jsppass";

		try {
			Class.forName(driver);
			System.out.println("드라이버 로드 성공");

			con = DriverManager.getConnection(url, id, pass);
			System.out.println("디비 접속 성공");
		} catch (ClassNotFoundException ce) {
			System.out.println("드라이버 로드 실패");
			ce.printStackTrace();
		} catch (SQLException e) {
			System.out.println("디비 접속 실패");
			e.printStackTrace();
		}
		return con;*/
		
		// Connection con = null;
		Context init = new InitialContext();
		DataSource ds =(DataSource) init.lookup("java:comp/env/jdbc/jsptest");
		con = ds.getConnection();
		return con; 	
		
	}

	// DB에 데이터 자원을 해제 시키는 메서드
	// closeDB() 메서드
	public void closeDB() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void passCheck(){
		boolean check = false;

		try {
			// 1단계 드라이버로드(프로그램 설치)
			// 2단계 디비연결
			con = getConnection();
			// 3단계 sql 객체 생성
			sql = "select pass from member where id='sksmscjsaos1@naver.com'";
			pstmt = con.prepareStatement(sql);
			// 4단계 sql 객체 실행
			rs = pstmt.executeQuery();
			// 5단계 rs에 저장된 정보를 처리
			rs.next();
			check = BCrypt.checkpw("xor493463", rs.getString("pass"));
			System.out.println(check);
		} catch (SQLException e) {
			System.out.println("디비 연결 실패!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
}
