package com.selfProject.sms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Student {
	private Connection connection;
	private Scanner scanner;
	
	
	public  Student(Connection connection,Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}
	public void addStudent() {
		System.out.println("Enter Student name:");
		String name=scanner.nextLine();
		System.out.println("Enter Student email:");
		String email=scanner.nextLine();
		System.out.println("Enter Student mobile num:");
		String mob_num=scanner.next();
		scanner.nextLine();
		System.out.println("Enter Student address:");
		String address=scanner.nextLine();
		String query="Insert into student(Stu_name,Stu_email,Stu_mob,Stu_add) values (?,?,?,?)";
		//Add Student details in db
		try (PreparedStatement psmt=connection.prepareStatement(query)){
			psmt.setString(1, name);
			psmt.setString(2, email);
			psmt.setString(3, mob_num);
			psmt.setString(4, address);
			
			int count=psmt.executeUpdate();
			
			if(count>0) {
				System.out.println("Student Data Updated Successfully!.....");
			}
			else {
				System.out.println("Insertion not successful !......");
			}
		}
		catch(Exception e) {
			e.getMessage();		
			}
	}
	
		//Get student details by their id
		public void getStudentById(int id) {
			//try-with-resource =>we don't want to close statement manualy it will do automatically
			//psmt.close()  rs.close()
			String query="Select * from student where Stu_id=?";
			try (PreparedStatement psmt=connection.prepareStatement(query)){
				
				psmt.setInt(1, id);
				try(ResultSet rs=psmt.executeQuery()){
				if(rs.next()) {
					System.out.println(".......Student details.........");
					System.out.println("+-------------------------------------------------+");
					System.out.println("| Stu Id   |  Stu Name  | Stu Email  |  Stu mob +");
					System.out.println("+-------------------------------------------------+");
					String name=rs.getString("Stu_name");
					String mob=rs.getString("Stu_mob");
					String email=rs.getString("Stu_email");
					System.out.printf("%-10s|%-15s|%-25s|%-12s|\n", id, name, email, mob);
					System.out.println("+-------------------------------------------------+");
				}
				else
					System.out.println("Student details not found....");
				}
				catch (Exception e) {
				    System.out.println("Error while reading student details: " + e.getMessage());
				}

			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		//get student by their name
		public void getStuByName() {
			System.out.println("Enter student name:");
			String stu_name = scanner.nextLine();
			String query = "Select * from student where stu_name = ?";

			try (PreparedStatement psmt = connection.prepareStatement(query)) {
				psmt.setString(1, stu_name);
				try (ResultSet rs = psmt.executeQuery()) {
					if (!rs.isBeforeFirst()) {// check resultset has any content if not return this content
						System.out.println("Student detail not found....!");
						return;
					}
					System.out.println(".......Student details.........");
					System.out.println("+-------------------------------------------------+");
					System.out.println("| Stu Id   |  Stu Name  | Stu Email  |  Stu mob +");
					System.out.println("+-------------------------------------------------+");
					while (rs.next()) {
						int id = rs.getInt("Stu_id");
						String mob = rs.getString("Stu_mob");
						String email = rs.getString("Stu_email");
						System.out.printf("%-10s|%-15s|%-25s|%-12s|\n", id, stu_name, email, mob);
						System.out.println("+-------------------------------------------------+");
					}

				}
				catch (Exception e) {
				    System.out.println("Error while reading student details: " + e.getMessage());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		//Display all Student Details
		public void studentList() {
			String query="Select * from student";
			//try-with-resource =>we don't want to close statement manualy it will do automatically
			try (PreparedStatement psmt=connection.prepareStatement(query)){
				
				System.out.println(".......Student details.........");
				System.out.println("+-------------------------------------------------+");
				System.out.println("| Stu Id   |  Stu Name  | Stu Email  |  Stu mob +");
				System.out.println("+-------------------------------------------------+");
				try(ResultSet rs=psmt.executeQuery()){
					while(rs.next()) {
						int id=rs.getInt("Stu_id");
						String name=rs.getString("Stu_name");
						String mob=rs.getString("Stu_mob");
						String email=rs.getString("Stu_email");
						String add=rs.getString("stu_add");
						System.out.printf("%-10s|%15s|%-25s|%-12s|%-20s|\n",id,name,email,mob,add);
						System.out.println();
					}
				}
				catch (Exception e) {
				    System.out.println("Error while reading student details: " + e.getMessage());
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		//update student details by their id
		public void updateStu(int id) {
			if(checkStudent(id))
			{
				System.out.println("Update Student name:");
				String name=scanner.nextLine();
				System.out.println("Update Student email:");
				String email=scanner.nextLine();
				System.out.println("Update Student mobile num:");
				String mob_num=scanner.next();
				scanner.nextLine();
				System.out.println("Update Student address:");
				String address=scanner.nextLine();
				String query="Update student set stu_name=?,stu_email=?,stu_mob=?,stu_add=? where stu_id=? ";
				try (PreparedStatement psmt=connection.prepareStatement(query)){
					psmt.setString(1, name);
					psmt.setString(2, email);
					psmt.setString(3, mob_num);
					psmt.setString(4, address);
					psmt.setInt(5, id);
					int count=psmt.executeUpdate();
					if(count>0)
						System.out.println("Updation done successfully for:"+name);
					else
						System.out.println("Updation ......failed......");
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			else{
				System.out.println(".......Student details not found......");
			}
		}
		
		//Delete the student data by their id
		public void deleteStu(int id) {
			String query="Delete from student where Stu_id=?";
			String query1="Delete from enrollment where stu_id=?";
			//here for deleting student record why we deleting enrollment details of student
			//here student table has primary key and enrollment table has forein key have connection with them
			//if we delete one detail it will affect the other  table
			//because if we delete student but he enrolled the course it will affect the enrollment table
			//if we don't delete in enrollment table,there is no student how it can be enrolled  question will arise
			//that's why if we delete a student,delete the relavent enrollment details at same time
			try(PreparedStatement psmt=connection.prepareStatement(query);
					PreparedStatement psmt1=connection.prepareStatement(query1)){
				psmt1.setInt(1, id);
				psmt1.executeUpdate();
				psmt.setInt(1, id);
				int count=psmt.executeUpdate();
				if(count>0) {
					System.out.println("Student data deleted successfully....!");
					System.out.println();
				}
				else {
					System.out.println("Student data not found on that id:"+id);
					System.out.println();
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		//check for the student present in list or not
		public boolean checkStudent(int id) {
			String query = "Select * from student where stu_id=?";

			try (PreparedStatement psmt = connection.prepareStatement(query)) {

				psmt.setInt(1, id);
				try (ResultSet rs = psmt.executeQuery()) {
					if (rs.next())
						return true;
					else
						return false;
				} catch (Exception e) {
					e.getMessage();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	
}
