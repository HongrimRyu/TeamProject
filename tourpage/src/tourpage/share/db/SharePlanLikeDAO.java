package tourpage.share.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SharePlanLikeDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	//커넥션 풀
	private Connection getConnection() throws Exception{
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/jsptest");
		con = ds.getConnection();
		return con;
	}//getConnection()
	//자원해제 메소드 
	public void DBClose(){
		if(rs != null){
			try{
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(pstmt != null){
			try{
			pstmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(con != null){
			try{	
			con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}//DBClose
	
	//좋아요를 저장하는 메소드
	public boolean check_insert_delete(int plan_idx, String id){
		boolean check = false;
		int max = 0;
		try {

			con = getConnection();
			sql = "select * from shareplanlike where plan_idx = ? and id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, plan_idx);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			if(rs.next()){ // 해당 값이 있으면 삭제해준다.
				sql = "delete from shareplanlike where plan_idx = ? and id = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, plan_idx);
				pstmt.setString(2, id);
				pstmt.executeUpdate();
				System.out.println("shareplanlike에 해당데이터 삭제");
				check = true;
				System.out.println("check : " + check);
				return check;
			}
			
			// 해당값이 없으면 넣어준다.
			sql = "select max(idx) from shareplanlike";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())
				max = rs.getInt(1) + 1;
			
			sql = "insert into shareplanlike values(?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, max);
			pstmt.setInt(2, plan_idx);
			pstmt.setString(3, id);
			pstmt.executeUpdate();
			System.out.println("shareplanlike에 해당데이터 삽입");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBClose();
		}
		System.out.println("check : " + check);
		return check;
	}//check_insert
	
	
} // SharePlanLikeDAO
