package com.selfProject.sms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainOfSMS {
	
	private static final String dburl="jdbc:mysql://localhost:3306/sms";
	private static final String user="root";
	private static final String password="root";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		Scanner s=new Scanner(System.in);
		try {
			Connection connection=DriverManager.getConnection(dburl,user,password);
			
			Student stu=new Student(connection,s);
			Course crs=new Course(connection,s);
			Enrollment e=new Enrollment(connection,s);
			
			while(true) {
				System.out.println("Student Management System");
				System.out.println("1:Add Student \n 2:All Student list \n 3:Get Student By Id \n 4:Get Student By name \n 5:Update student by Id \n 6:Delete student ");
				System.out.println("11:Add course \n 12:List of course \n 13:Get course by id \n 14:Get course by name \n 15:Get stu details by CRS name \n 16:Delete course");
				System.out.println("21:Enroll Course \n 22:Count of stu enrolled \n 23:Enrolled stu");
				System.out.println("100:Exit \n Enter your choice::");
				int choice=0;
				try {//it will get input from user 
					//if user enter any alphabet or symbol instead of numbers
					//it jump to catch block
					 choice=s.nextInt();
					 s.nextLine();
				}
				catch(InputMismatchException ex) {
					System.out.println("Invalid input.... !! Enter valid number...!!!");
					s.next();//if we don't use this the loop will never get terminated
				}
				switch(choice) {
				case 1:
					stu.addStudent();
					System.out.println();
					break;
				case 2:
					//list of students
					stu.studentList();
					System.out.println();
					break;
				case 3:
					//get student details by id
					System.out.println("Enter Student id: ");
					int id1=s.nextInt();
					stu.getStudentById(id1);
					break;
				case 4:
					//get student details by name
					stu.getStuByName();
					break;
				case 5:
					//update student by id
					System.out.println("Enter student id:");
					int id=s.nextInt();
					s.nextLine();
					stu.updateStu(id);
					break;
				case 6:
					//Delete student details by id
					System.out.println("Enter student id:");
					int id2=s.nextInt();
					stu.deleteStu(id2);
					break;
				
				case 11:
					//Add course
					crs.addCourse();
					break;
				case 12:
					//list of course
					crs.courseList();
					break;
				case 13:
					//Get course by id
					System.out.println("Enter course id:");
					int id3=s.nextInt();
					crs.getCourseById(id3);
					break;
				case 14:
					//get course by name
					System.out.println("Enter course name:");
					String crs_name=s.next();
					crs.getCourse(crs_name);
					break;
				case 15:
					//get stu details by crs name
					crs.getStuDetailByCourse();
					break;
				case 16:
					//delete course
					System.out.println("Enter course id:");
					int id4=s.nextInt();
					crs.deleteCourse(id4);
					break;
				case 21:
					//enroll course
					e.enrollCourse(stu, crs);
					break;
				case 22:
					//Total num of students enrolled
					e.enrolledStuCount();
					break;
				case 23:
					//enrolled stu list
					e.enrolledStu();
					break;
				case 100:
					//exit from while loop
					System.out.println("Thank you accessing Student DB");
					return;
					
				default:System.out.println("Enter valid choice......");
				}
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
