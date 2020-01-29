package roll_call_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Teacher {
	private int Id=-1;
	private String UserName="";
	private String Name="";
	private String Password="";
	private String Email="";
	private String PhoneNumber="";
	private String Major="";
	
	public Teacher(String username , String name , String password , String email , String phone , String major) {
		UserName = username;
		Name = name;
		Password = password;
		Email = email;
		PhoneNumber = phone;
		Major=major;
		return;
	}
	
	public void addTeacher(Connection con) {
		try {
			//detect duplicate
			String sqltest="select TeacherId from teacher where TeacherUserName = ?";
			PreparedStatement stmt2=con.prepareStatement(sqltest);
			stmt2.setString(1, UserName);
			ResultSet rs=stmt2.executeQuery();
			if(rs.next()) { 
				function.BugDetector(2);
				return;
			}
			
			
			
			String sql="insert into teacher (TeacherName , TeacherMajor , TeacherUserName , TeacherPassword , TeacherEmail , TeacherPhoneNumber) "
				+ "values (?,?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,Name);
			stmt.setString(2, Major);
			stmt.setString(3,UserName);
			stmt.setString(4,Password);
			stmt.setString(5,Email);
			stmt.setString(6,PhoneNumber);
			stmt.executeUpdate();
			stmt.close();
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getId(Connection con) {
		
		if(Id!=-1) {
			return Id;
		}
		
		try {
			//do select
			String sql="select TeacherId from teacher where TeacherUserName = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, UserName);
			ResultSet rs=stmt.executeQuery();
			
			//check if the number of selected data = 1
			int size=0;
			if(rs != null) {
				rs.last();
				size = rs.getRow();
			}
			if(size != 1) {
				return -1;
			}
			Id=rs.getInt(1);
			return Id;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void joinClass(int classid,Connection con) {
		
		if(Id==-1) getId(con);
		
		if(!Class.isValidId(classid, con)) {
			function.BugDetector(6);
		}
		
		function.addTeacherinClass(Id, classid, con);
		return;
	}
	
	public static int getIdbyUserName(String username , Connection con) {
		try {
			//do select
			String sql="select TeacherId from teacher where TeacherUserName = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs=stmt.executeQuery();
			
			//check if the number of selected data = 1
			int size=0;
			if(rs != null) {
				rs.last();
				size = rs.getRow();
			}
			if(size != 1) {
				return -1;
			}
			return rs.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static boolean isValidId(int teacherid , Connection con) {
		try {
			String sql = "select TeacherId from teacher where TeacherId = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, teacherid);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
}
