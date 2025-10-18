package com.selfProject.sms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Course {
	private Connection connection;
	private Scanner scanner;
	
	public Course(Connection connection,Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}
	
	//add new course
	public void addCourse() {
		System.out.println("Course name:");
		String crs_name=scanner.nextLine();
		System.out.println("Course duration:");
		String duration=scanner.nextLine();
		System.out.println("Enter fees:");
		int fees=scanner.nextInt();
		
		String query="Insert into course(crs_name,crs_dur,crs_fee) values (?,?,?)";
		try (PreparedStatement psmt=connection.prepareStatement(query)){
			psmt.setString(1, crs_name);
			psmt.setString(2, duration);
			psmt.setInt(3, fees);
			int count=psmt.executeUpdate();
			if(count>0)
				System.out.println("Course added successfully...........");
			else
				System.out.println("Error at insertion .... check code !!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//List of courses
	public void courseList() {
		String query = "Select * from course";
		try (PreparedStatement psmt = connection.prepareStatement(query)) {

			try (ResultSet rs = psmt.executeQuery()) {
				System.out.println(".......Course details.........");
				System.out.println("+---------------------------------------------------------+");
				System.out.println("| Course Id   |  Course Name  | Course duration  |  Fees +");
				System.out.println("+---------------------------------------------------------+");
				while (rs.next()) {
					int id = rs.getInt("crs_id");
					String name = rs.getString("crs_name");
					String duration = rs.getString("crs_dur");
					int fees = rs.getInt("crs_fee");
					System.out.printf("|%-10s|%14s|%-8s|%-10s|\n", id, name, duration, fees);
					System.out.println("+-----------------------------------------------------+");
				}
			} catch (Exception e) {
				e.getMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//Get Course by id
	public void getCourseById(int id) {
		String query = "Select * from course where crs_id=?";
		try (PreparedStatement psmt = connection.prepareStatement(query)) {

			psmt.setInt(1, id);
			try (ResultSet rs = psmt.executeQuery()) {
				if(!rs.isBeforeFirst()) {
					System.out.println("Course details not found...!");
				}
				else {
					System.out.println(".......Course details.........");
					System.out.println("+--------------------------------------------------------+");
					System.out.println("| Course Id   |  Course Name  | Course duration  |  Fees +");
					System.out.println("+--------------------------------------------------------+");
					if (rs.next()) {
						String name = rs.getString("crs_name");
						String duration = rs.getString("crs_dur");
						int fees = rs.getInt("crs_fee");
						System.out.printf("|%-10s|%14s|%-8s|%-10s|\n", id, name, duration, fees);
						System.out.println("+-----------------------------------------------------+");
					} else
						System.out.println("Course details not found............");
				}
			} catch (Exception e) {
				e.getMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//get course by name
	public void getCourse(String crs_name) {
		String query = "Select * from course where crs_name=?";
		try {
			PreparedStatement psmt = connection.prepareStatement(query);
			psmt.setString(1, crs_name);
			ResultSet rs = psmt.executeQuery();
			System.out.println(".......Course details.........");
			System.out.println("+--------------------------------------------------------+");
			System.out.println("| Course Id   |  Course Name  | Course duration  |  Fees +");
			System.out.println("+--------------------------------------------------------+");

			if (rs.next()) {
				int id=rs.getInt("crs_id");
				String duration = rs.getString("crs_dur");
				int fees = rs.getInt("crs_fee");
				System.out.printf("|%-10s|%14s|%-8s|%-10s|\n", id, crs_name, duration, fees);
				System.out.println("+-----------------------------------------------------+");
			} else
				System.out.println("Course details not found............");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Delete course by id
	public void deleteCourse(int id) {
		String query="Delete from course where crs_id=?";
		try {
			PreparedStatement psmt=connection.prepareStatement(query);
			psmt.setInt(1, id);
			int count=psmt.executeUpdate();
			if(count>0)
				System.out.println("Course deleted successfully");
			else
				System.out.println("Course details not found.......");
		}
		catch(Exception e) {
			System.out.println("Can't delete the course because it is connected with enrollment table :,"+e.getMessage());
		}
	}
	
	//it will check weather the course is available or not
	public boolean checkCourse(int id) {
		String query="Select * from course where crs_id=?";
		
		try {
			PreparedStatement psmt=connection.prepareStatement(query);
			psmt.setInt(1, id);
			ResultSet rs=psmt.executeQuery();
			if(rs.next())
				return true;
			else
				return false;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//list all student details based on course
	public void getStuDetailByCourse() {
		String query="Select s.stu_id,s.stu_name,s.stu_email,s.stu_mob,c.crs_name "
				+ " from enrollment e "
				+ " join student s on s.stu_id=e.stu_id "
				+ "join course c on c.crs_id=e.crs_id "
				+ "where c.crs_name like ?";
		System.out.println("Enter course name:");
		String crs_name=scanner.nextLine();
		try(PreparedStatement psmt=connection.prepareStatement(query)){
			psmt.setString(1, crs_name);
			ResultSet rs=psmt.executeQuery();
			System.out.println(".......Student details.........");
			System.out.println("+----------------------------------------------------------------------+");
			System.out.println("| Stu Id   |  Stu Name   |   Stu Email |    Stu Mobile |  Crs name +");
			System.out.println("+-------------------------------------------------------------------------+");
			while(rs.next()) {
				int stu_id=rs.getInt("stu_id");
				String stu_name=rs.getString("stu_name");
				String stu_email=rs.getString("stu_email");
				String stu_mob=rs.getString("stu_mob");
				System.out.printf("%-10s|%18s|%-8s|%18s|%18s|\n",stu_id,stu_name,stu_email,stu_mob,crs_name);
				System.out.println();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
