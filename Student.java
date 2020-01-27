package roll_call_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student{
	private int Id=-1;
	private String UserName="";
	private String Name="";
	private String Password="";
	private String Email="";
	private String PhoneNumber="";
	
	
	public void addStudent(Connection con) {
		try {
			String sql="insert into student (StudentName , StudentUserName , StudentPassword , StudentEmail , StudentPhoneNumber) "
				+ "values ( ? , ? , ? , ? , ? )";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,Name);
			stmt.setString(2,UserName);
			stmt.setString(3,Password);
			stmt.setString(4,Email);
			stmt.setString(5,PhoneNumber);
			
			stmt.executeUpdate();
			
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getStudentId(Connection con) {
		try {
			//do select
			String sql="select StudentId from student where StudentUserName = ?";
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
	
	public void joinClass(int classid , Connection con) {
		try {
				
			if(!(function.isValidStudentId(Id,con)&&function.isValidClassId(classid,con))) {
				System.out.println("invalid studentid or classid in joinClass.");
				return;
			}
			
			//insert into studentinclass
			function.addStudentinClass(Id , classid , con);
			
			//add Attendance
			Date dnow = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql="select ClassTimeId from classtime where ClassTimeRefClassId = ? AND Time > ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, classid);
			stmt.setString(2, formatter.format(dnow));
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				function.addAttendance(Id,rs.getInt(rs.getRow()),con);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
