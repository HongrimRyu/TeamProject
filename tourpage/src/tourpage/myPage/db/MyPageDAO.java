package tourpage.myPage.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import tourpage.member.db.MemberBean;
import tourpage.member.encryption.BCrypt;

public class MyPageDAO {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	
	private Connection getCon() throws Exception{
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/jsptest");
		con=ds.getConnection();
		return con;
	}
	
	public void CloseDB(){
		if(rs!=null){
			try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		if(pstmt!=null){
			try { pstmt.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		if(con!=null){
			try { con.close(); } catch (SQLException e) {e.printStackTrace();}
		}
	}
	
	public int insertProfile(MyPageBean mpb){
		int check=0;
		try {
			con = getCon();
			sql="select * from myPage";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				sql = "insert into myPage values(?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, mpb.getFile());
				pstmt.executeUpdate();
				System.out.println("insert S");
				check=1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			CloseDB();
		}
		return check;
	}
	public int deleteMyInfo(String id, String pass){
		int check = 0;
		try {
			con = getCon();
			sql  = "select * from member where pass=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, pass);
			rs=pstmt.executeQuery();
			if(rs.next()){
				if(pass.equals(BCrypt.checkpw(pass, rs.getString("pass")))){
					sql = "delete form member where id=?";
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					System.out.println("delete completed");
					check=1;
				}else{
					check=0;
				}
			}else{
				check=-1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			CloseDB();
		}
		return check;
	}
	
//	// 회원정보 수정
//	public int UpdateMember(MemberBean mb) {
//		
//		int check = -1; // -1 아이디 없음, 0 아이디O 비밀번호 X, 1 아이디O 비밀번호 O
//
//		try {
//			// 1단계 드라이버로드(프로그램 설치)
//			// 2단계 디비연결
//			con = getCon();
//			
//			sql = "select id from member id=?";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, mb.getId());
//			rs = pstmt.executeQuery();
//			if (rs.next()) {
//				sql = "update member set pass=?, nick=?, birth=?, gender=?, phone=?, profile=? where id=?";
//				pstmt = con.prepareStatement(sql);
//				pstmt.setString(1, mb.getPass());
//				pstmt.setString(2, mb.getNick());
//				pstmt.setString(3, mb.getBirth());
//				pstmt.setString(4, mb.getGender());
//				pstmt.setString(5, mb.getPhone());
//				pstmt.setString(6, mb.getProfile());
//				pstmt.setString(7, mb.getId());
//				// 4단계 실행
//				pstmt.executeUpdate();
//				check = 1;
//			} else {
//				check = -1;
//			}
//		} catch (SQLException e) {
//			System.out.println("디비 연결 실패!");
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			// 예외발생 여부와 상관없이
//			// 반드시 실행해야하는 코드 작성
//			CloseDB();
//		}
//		return check;
//	}
	
	 // 회원정보 가져오기 (getMember)
	public MemberBean getMember(String id) {
		MemberBean m = null;
		try {
			// 1단계 드라이버로드(프로그램 설치)
			// 2단계 디비연결
			con = getCon();

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
				// m.setPhone(rs.getString(m.getPhone()));
				// m.setReg_date(rs.getTimestamp("reg_date"));
			}
		} catch (SQLException e) {
			System.out.println("디비 연결 실패!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 예외발생 여부와 상관없이
			// 반드시 실행해야하는 코드 작성
			CloseDB();
		}
		return m;
	}
	// 회원정보 가져오기 (getMember)
	
	// 프로필 수정
	public int Profile(MemberBean mb) {
		
		System.out.println(mb.getPass());
		System.out.println(mb.getNick());
		System.out.println(mb.getBirth());
		System.out.println(mb.getGender());
		System.out.println(mb.getPhone());
		System.out.println(mb.getProfile());
		System.out.println(mb.getId());
		
		try {
			con = getCon();
			sql = "update member set pass=?, nick=?, birth=?, gender=?, phone=?, profile=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mb.getPass());
			pstmt.setString(2, mb.getNick());
			pstmt.setString(3, mb.getBirth());
			pstmt.setString(4, mb.getGender());
			pstmt.setString(5, mb.getPhone());
			pstmt.setString(6, mb.getProfile());
			pstmt.setString(7, mb.getId());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}
		
		return -1;
	}
	// 프로필 출력
	public String getProfile(String id) {
		
		try {
			con = getCon();
			sql = "select profile from member where id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("profile").equals("default.png")) {
					return "./img/myPage/default.png";
				}
				return "http://localhost:8088/tourpage/upload/" + rs.getString("profile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}
		return "http://localhost:8088/tourpage/WebContent/img/myPage/default.png";
	}
		
}