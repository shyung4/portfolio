package com.naver.phone.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.naver.phone.dto.UserDto;

@Repository
public class UserDao {

	
	final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	
	public Connection open() {
		Connection con = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(JDBC_URL, "ora_user", "hong");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public void addMem(UserDto u) throws Exception { //가져와야하는거 userid
		Connection con = open();
		
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("	INSERT INTO usertb VALUES(?,?,?)	");
		
		
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		
		try(con;pstmt) {
			
			pstmt.setString(1, u.getUserid()); // userid 넣어야하는것
			pstmt.setString(2, u.getUserpw());
			pstmt.setString(3, u.getUserph());
			pstmt.executeUpdate();
			
		}

	}
	
	public ArrayList<UserDto> getUser() throws Exception {
		Connection con = open();
		
		ArrayList<UserDto> userList = new ArrayList<>();
		String sql = "	SELECT userid, userpw FROM usertb	";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try(con; pstmt; rs) {
			
			while(rs.next()) {
				UserDto u = new UserDto();
				u.setUserid(rs.getString("userid"));
				u.setUserpw(rs.getString("userpw"));
				userList.add(u);
			}

			
			return userList;
		}
	}
	
	public int checkId(String userid) throws Exception {
		Connection con = open();
		String sql = "	select * from usertb where userid = ?	";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userid);
		ResultSet rs = pstmt.executeQuery();
		
		int idCheck = 0;
		try(con; pstmt; rs) {
			while(rs.next()) { 
				if(userid.equals(rs.getString("userid"))) { // 중복이면 1로 됨
				idCheck++;
				}
			}
			
		
		}
	
		return idCheck;
	}
	
	
}
