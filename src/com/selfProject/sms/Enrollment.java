package com.selfProject.sms;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Scanner;

public class Enrollment {
	private Connection connection;
	private Scanner scanner;
	
	public Enrollment(Connection connection,Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}
	
	//Enroll course
	public void enrollCourse(Student s,Course c) {
		System.out.println("Enter student id:");
		int stu_id=scanner.nextInt();
		System.out.println("Enter course id:");
		int crs_id=scanner.nextInt();
		boolean condition=s.checkStudent(stu_id) && c.checkCourse(crs_id);

		String query="Insert into enrollment(stu_id,crs_id,enroll_date) values (?,?,?)";
		try {
			if(condition)
			{
				PreparedStatement psmt=connection.prepareStatement(query);
				psmt.setInt(1, stu_id);
				psmt.setInt(2, crs_id);
				psmt.setDate(3, Date.valueOf(LocalDate.now()));
				//Local date.now() will return the current date
				//Date.valueOf() will convert date to sql date format
				int count = psmt.executeUpdate();
				if(count>0)
					System.out.println("Course enrolled successfull");
				else
					System.out.println(".......Enrollment failed......  ");
			}
			else
				System.out.println("Student or Course has not enrolled");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Count of enrolled student
	public void enrolledStuCount() {
		String query="Select count(*) from enrollment";
		try {
			PreparedStatement psmt=connection.prepareStatement(query);
			ResultSet rs=psmt.executeQuery();
			if(rs.next()) {//rs.next()=>cursor will move to first row
				int count=rs.getInt(1);//getInt(1)=>get value from first column
				System.out.println("Total number of enrolled students:"+count);
			}
			else
				System.out.println("No students have enrolled yet..!!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//enrolled list of students
	public void enrolledStu() {
		String query = "Select e.enr_id,s.stu_name,c.crs_name,e.enroll_date " + "from enrollment e "
				+ "join student s on s.stu_id=e.stu_id " + "join course c on c.crs_id=e.crs_id";
		// this is called try-with-resource=>it will do auto statement closing
		// eg:psmt.close() rs.close() we don't want to close manually
		// try(){...}
		try (PreparedStatement psmt = connection.prepareStatement(query); 
				ResultSet rs = psmt.executeQuery()) {
			System.out.println(".......Enrollment details.........");
			System.out.println("+-------------------------------------------------+");
			System.out.println("| Enr Id   |  Stu name  | Crs name  |  enr date +");
			System.out.println("+-------------------------------------------------+");
			while (rs.next()) {
				int id = rs.getInt("enr_id");
				String stu_name = rs.getString("stu_name");
				String crs_name = rs.getString("crs_name");
				String enr_dte = rs.getString("enroll_date");
				System.out.printf("%-10s|%18s|%-8s|%-10s|\n", id, stu_name, crs_name, enr_dte);
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
