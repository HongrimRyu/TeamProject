package tourpage.group.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class GmemoDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	private Connection getConnection() throws Exception {
		Context init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/jsptest");
		con = ds.getConnection();
		return con;
	}

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
	public boolean insertMemo(GmemoBean gb) {

		try {
			con=getConnection();
			sql="insert into gwmemo (groupid,gw_comment,w_time,gw_nick,gw_top,gw_left,gw_width,gw_height,passwd)"
					+ "values(?,?,now(),?,?,?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, gb.getGroupid());
			pstmt.setString(2, gb.getGw_comment());
			pstmt.setString(3, gb.getGw_nick());
			pstmt.setString(4, "0px");
			pstmt.setString(5, "0px");
			pstmt.setString(6, gb.getGw_width());
			pstmt.setString(7, gb.getGw_height());
			pstmt.setString(8, "dd");
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB();
		}
		return false;
	}

	public int getMaxidx() {
		try {
			con=getConnection();
			sql="select max(idx) from gwmemo";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB();
		}
		return 0;
	}

	public boolean updateMemo(GmemoBean gb,String mode) {
		try {
			con=getConnection();
			if(mode.equals("drag")){
			    sql="update gwmemo set gw_top=?, gw_left=? where idx=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, gb.getGw_top());
				pstmt.setString(2, gb.getGw_left());
				pstmt.setInt(3, gb.getIdx());
			}else if(mode.equals("size")){
				sql="update gwmemo set gw_width=?, gw_height=? where idx=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, gb.getGw_width());
				pstmt.setString(2, gb.getGw_height());
				pstmt.setInt(3, gb.getIdx());
			}else if(mode.equals("commodi")){
				sql="update gwmemo set gw_comment=? where idx=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, gb.getGw_comment());
				pstmt.setInt(2, gb.getIdx());
			}
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB();
		}
		return false;
	}

	public boolean deleteMemo(int idx) {
		
		try {
			con=getConnection();
			sql="delete from gwmemo where idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		
		return false;
	}
	
	public List<GmemoBean> getMemoList(String groupid){
		List<GmemoBean> memolist= new ArrayList<GmemoBean>();
		try {
			con=getConnection();
			sql="select * from gwmemo where groupid= ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, groupid);
			rs=pstmt.executeQuery();
			while(rs.next()){
				GmemoBean gb= new GmemoBean();
				gb.setIdx(rs.getInt("idx"));
				gb.setGroupid(rs.getString("groupid"));
				gb.setGw_comment(rs.getString("gw_comment"));
				gb.setW_time(rs.getTimestamp("w_time"));
				gb.setGw_nick(rs.getString("gw_nick"));
				gb.setGw_top(rs.getString("gw_top"));
				gb.setGw_left(rs.getString("gw_left"));
				gb.setGw_width(rs.getString("gw_width"));
				gb.setGw_height(rs.getString("gw_height"));
				memolist.add(gb);				
			}
			return memolist;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		
		
		return null;
	}
	
	
	
}
