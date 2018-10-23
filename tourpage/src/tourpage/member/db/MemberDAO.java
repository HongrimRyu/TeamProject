package tourpage.member.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


import tourpage.member.encryption.BCrypt;

// executeQuery() - select (DB데이터 조작X)
// => 레코드셋 데이터 리턴(ResultSet 객체에 저장)
// executeUpdate() - insert,delete,update (DB데이터 조작O) + DDL(create table~,alter~,drop~)
// => int 값(DB에 영향을 준 문장수,DB에 적용된 라인수) 리턴

// +추가
// "0"출력 -> 1) 실행 오류(추가,수정,삭제 실패)
//           2) 실행된 SQL에 영향을 주지 않는 코드(쿼리)
//          3) 실행된 SQL 구문이 DDL이다.

public class MemberDAO {

	// MemberBean저장된 정보를 받아서
	// 데이터 처리(디비 저장)

	// 공통으로 사용되는 레퍼런스를 미리 생성
	// 인스턴스변수 (전역변수)
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

	// 회원 가입 기능(insertMember)
	public void insertMember(MemberBean m) {
		// String driver = "com.mysql.jdbc.Driver";
		// String url = "jdbc:mysql://localhost:3306/jspdb";
		// String id = "jspid";
		// String pass = "jsppass";

		try {
			// 1단계 드라이버로드(프로그램 설치)
			// 2단계 디비연결
			con = getConnection();
			// 3단계 sql 객체 생성
			// String sql = "insert into userjoin values(null,?,?,?,?,?,?,?)";
			sql = "insert into member (id,pass,nick,birth,gender,phone,reg_date,reg_ip,profile,platform)"
					+ " values(?,?,?,?,?,?,now(),?,?,?)";
			// PreparedStatement pstmt = con.prepareStatement(sql);  default.png
			pstmt = con.prepareStatement(sql);
			// id
			pstmt.setString(1, m.getId());
			// pass
			pstmt.setString(2, m.getPass());
			// nick
			pstmt.setString(3, m.getNick());
			// birth
			pstmt.setString(4, m.getBirth());
			// gender
			pstmt.setString(5, m.getGender());
			// phone
			pstmt.setString(6, m.getPhone());
			// reg_ip
			pstmt.setString(7, m.getReg_ip());
			// profile
			pstmt.setString(8, "");
			// platform
			pstmt.setString(9, m.getPlatform());
			// 4단계 sql 객체 실행
			pstmt.executeUpdate();
			System.out.println("회원가입 성공(insert)");

			// pstmt.close();
			// con.close();

		} catch (SQLException e) {
			System.out.println("회원가입 실패(insert)");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 예외발생 여부와 상관없이
			// 반드시 실행해야하는 코드 작성
			closeDB();
		}

	}
	// 회원 가입 기능(insertMember)

	// 아이디 비밀번호 체크하는 메서드(idCheck)
	public boolean idCheck(String id){
		boolean check = false;

		try {
			// 1단계 드라이버로드(프로그램 설치)
			// 2단계 디비연결
			con = getConnection();
			// 3단계 sql 객체 생성
			sql = "select * from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// 4단계 sql 객체 실행
			rs = pstmt.executeQuery();
			// 5단계 rs에 저장된 정보를 처리
			if (rs.next()) {
				// 아이디 있을때
				check = true;
			}
		} catch (SQLException e) {
			System.out.println("디비 연결 실패!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return check;
	}
	
	public boolean nickCheck(String nick){
		boolean check = false; 

		try {
			// 1단계 드라이버로드(프로그램 설치)
			// 2단계 디비연결
			con = getConnection();
			// 3단계 sql 객체 생성
			sql = "select * from member where nick=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nick);
			// 4단계 sql 객체 실행
			rs = pstmt.executeQuery();
			// 5단계 rs에 저장된 정보를 처리
			if (rs.next()) {
				// 닉네임 있을때
				check = true;
			}
		} catch (SQLException e) {
			System.out.println("디비 연결 실패!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return check;
	}
	
	public boolean phoneCheck(String phone){
		boolean check = false; 

		try {
			// 1단계 드라이버로드(프로그램 설치)
			// 2단계 디비연결
			con = getConnection();
			// 3단계 sql 객체 생성
			sql = "select * from member where phone=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, phone);
			// 4단계 sql 객체 실행
			rs = pstmt.executeQuery();
			// 5단계 rs에 저장된 정보를 처리
			if (rs.next()) {
				// 연락처 있을때
				check = true;
			}
		} catch (SQLException e) {
			System.out.println("디비 연결 실패!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return check;
	}
	//로그인 확인
	public boolean loginCheck(String id, String pass) {

		boolean check = false; // 로그인 성공, 실패

		try {
			// 1단계 드라이버로드(프로그램 설치)
			// 2단계 디비연결
			con = getConnection();
			// 3단계 sql 객체 생성
			// id에 해당하는 pass있는지 판단
			sql = "select pass from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// 4단계 sql 객체 실행
			rs = pstmt.executeQuery();
			// 5단계 rs에 저장된 정보를 처리
			// 아이디 있을때
			// -비밀번호 O
			if (rs.next()) {// 아이디 있을 때
				if (BCrypt.checkpw(pass,rs.getString("pass"))) {//비밀번호가 같을 때
					check = true;
				}
			}
		} catch (SQLException e) {
			System.out.println("디비 연결 실패!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 예외발생 여부와 상관없이
			// 반드시 실행해야하는 코드 작성
			closeDB();
		}
		return check;
	}
	
	public boolean updatePass(String id,String pass) {

		boolean check = false; // 로그인 성공, 실패

		try {
			// 1단계 드라이버로드(프로그램 설치)
			// 2단계 디비연결
			con = getConnection();
			// 3단계 sql 객체 생성
			// id에 해당하는 pass 업데이트
			sql = "update member set pass=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, BCrypt.hashpw(pass, BCrypt.gensalt()));
			pstmt.setString(2, id);
			// 4단계 sql 객체 실행
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("디비 연결 실패!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return check;
	}
	public boolean joinCheck(String email){
		try {
			// 1단계 드라이버로드(프로그램 설치)
			// 2단계 디비연결
			con = getConnection();
			// 3단계 sql 객체 생성
			// id에 해당하는 pass 업데이트
			sql = "select id from member where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, email);
			// 4단계 sql 객체 실행
			rs = pstmt.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			System.out.println("디비 연결 실패!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		
		
		return false;
	}
	public String SNSidCheck(String email){
		String check ="";
		try{
			con= getConnection();
			sql = "select platform from member where id= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next()){
				check = rs.getString("platform"); // 이게 널이 들어감
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 예외발생 여부와 상관없이
			// 반드시 실행해야하는 코드 작성
			closeDB();
		}
		return check;
	}
	 // 회원정보 가져오기 (getMember)
		public MemberBean getMember(String id) {
			MemberBean m = null;
			try {
				// 1단계 드라이버로드(프로그램 설치)
				// 2단계 디비연결
				con = getConnection();

				// 3단계 sql 객체 생성
				sql = "select * from member where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				// 4단계 sql 객체 실행
				rs = pstmt.executeQuery();
				// 5단계 rs에 저장된 정보를 처리
				// 회원정보를 저장(MemberBean)
				if (rs.next()) {
					// 회원 정보가 있음.
					// 회원정보 저장 객체생성 MemberBean -> 저장
					m = new MemberBean();
					m.setId(rs.getString("id"));
					m.setPass(rs.getString("pass"));
					m.setNick(rs.getString("nick"));
					m.setBirth(rs.getString("birth"));
					m.setGender(rs.getString("gender"));
					m.setPhone(rs.getString(m.getPhone()));
					m.setReg_date(rs.getTimestamp("reg_date"));
				}
			} catch (SQLException e) {
				System.out.println("디비 연결 실패!");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 예외발생 여부와 상관없이
				// 반드시 실행해야하는 코드 작성
				closeDB();
			}
			return m;
		}
		// 회원정보 가져오기 (getMember)
		
		public String getNick(String id){ // 닉네임 가져오기
			String nick = "";
			try {
				con = getConnection();
				sql = "select nick from member where id = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if(rs.next()){
					nick = rs.getString(1);
				}
				System.out.println("닉네임 얻기 성공");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("닉네임 얻기 실패");
			} finally {
				closeDB();
			}
			return nick;
		} // 닉네임 가져오기
		
		// id값에 해당하는 프로필 이미지 가져오는 메소드
		public String getProfile(String id){
			String profile = "";
			try {
				con = getConnection();
				sql = "select profile from member where id = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if(rs.next()){
					profile = rs.getString(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return profile;
		}
		// 아이디 
		public int FriendCheck(String id){
			int check = -1;

			try {
				// 1단계 드라이버로드(프로그램 설치)
				// 2단계 디비연결
				con = getConnection();
				// 3단계 sql 객체 생성
				sql = "select * from member where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				// 4단계 sql 객체 실행
				rs = pstmt.executeQuery();
				// 5단계 rs에 저장된 정보를 처리
				if (rs.next()) {
					// 아이디 있을때
					check = 0;
				}
			} catch (SQLException e) {
				System.out.println("디비 연결 실패!");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			return check;
		}
		
		
	
	
}
