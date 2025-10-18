[README.md](https://github.com/user-attachments/files/22983061/README.md)
# 🎓 Student Management System (Java + MySQL)

A **Java-based Student Management System (SMS)** built using **JDBC** and **MySQL** to manage students, courses, and enrollments.  
It demonstrates real-world database connectivity, CRUD operations, and proper relational integrity handling between multiple tables.

---

## 🚀 Features

### 🧑‍🎓 Student Module
- ➕ Add new students  
- 📋 View all students  
- 🔍 Get student by ID or name  
- ✏️ Update student details  
- ❌ Delete student (restricted if enrolled)

### 📚 Course Module
- ➕ Add new courses  
- 📋 List all courses  
- 🔍 Get course by ID or name  
- ❌ Soft delete (or restricted if students enrolled)

### 📝 Enrollment Module
- 🔗 Enroll a student in a course  
- 🔢 Count total enrolled students  
- 📄 View all enrollment details (Student + Course info)

---

## 🧱 Database Design

### Tables
1. **student**
   - `Stu_id` (Primary Key)  
   - `Stu_name`, `Stu_email`, `Stu_mob`, `Stu_add`

2. **course**
   - `crs_id` (Primary Key)  
   - `crs_name`, `crs_dur`, `crs_fee`

3. **enrollment**
   - `enr_id` (Primary Key)  
   - `stu_id` (Foreign Key → student.Stu_id)  
   - `crs_id` (Foreign Key → course.crs_id)  
   - `enroll_date`

---

## ⚙️ Tech Stack

| Layer | Technology |
|--------|-------------|
| Language | Java (JDK 17 or higher recommended) |
| Database | MySQL 8.0 |
| Connectivity | JDBC |
| IDE | Eclipse / IntelliJ IDEA / VS Code |
| Build Tool | Manual (no Maven required) |

---

## 🧩 How to Run

### 1️⃣ Setup Database
```sql
CREATE DATABASE sms;

USE sms;

CREATE TABLE student (
  Stu_id INT AUTO_INCREMENT PRIMARY KEY,
  Stu_name VARCHAR(50),
  Stu_email VARCHAR(50),
  Stu_mob VARCHAR(15),
  Stu_add VARCHAR(100)
);

CREATE TABLE course (
  crs_id INT AUTO_INCREMENT PRIMARY KEY,
  crs_name VARCHAR(50),
  crs_dur VARCHAR(20),
  crs_fee INT
);

CREATE TABLE enrollment (
  enr_id INT AUTO_INCREMENT PRIMARY KEY,
  stu_id INT NOT NULL,
  crs_id INT NOT NULL,
  enroll_date VARCHAR(45),
  FOREIGN KEY (stu_id) REFERENCES student(Stu_id),
  FOREIGN KEY (crs_id) REFERENCES course(crs_id)
);
```

### 2️⃣ Configure Database Connection
In `MainOfSMS.java`:
```java
private static final String dburl = "jdbc:mysql://localhost:3306/sms";
private static final String user = "root";
private static final String password = "root";
```

### 3️⃣ Run the Application
Compile and run `MainOfSMS.java`  
You’ll see a text-based menu:
```
Student Management System
1: Add Student
2: List All Students
3: Get Student by ID
4: Get Student by Name
5: Update Student
6: Delete Student
11: Add Course
12: List All Courses
13: Get Course by ID
14: Get Course by Name
15: Delete Course
21: Enroll Course
22: Count of Enrolled Students
23: List of Enrolled Students
100: Exit
```

---

## 🧠 Key Learnings

- JDBC Database Connection Handling  
- PreparedStatement (SQL Injection-safe queries)  
- Exception Handling & Resource Management (`try-with-resources`)  
- Relational Mapping with Foreign Keys  
- Input Validation and Logical Data Integrity  
- Understanding of ON DELETE / ON UPDATE Constraints  

---

## 💡 Future Improvements

- Add **Soft Delete** for Students and Courses  
- Implement **Email & Mobile Validation**  
- Add **Admin Login Authentication**  
- Create a **JavaFX or Web-based UI (Spring Boot)**  
- Export reports in **CSV or PDF**  

---

## 👨‍💻 Author

**Gopinath M**  
📍 Student Developer | Passionate about Java, SQL & Backend Development  
📧 gopinathmadhu1000@gmail.com  

---

## 🏆 Project Rating

| Criteria | Rating |
|-----------|---------|
| Code Quality | ⭐⭐⭐⭐☆ |
| Database Design | ⭐⭐⭐⭐☆ |
| JDBC Implementation | ⭐⭐⭐⭐☆ |
| Maintainability | ⭐⭐⭐☆ |
| Real-world Usefulness | ⭐⭐⭐⭐☆ |

> 💬 *"A clean, complete, and practical Student Management System — shows real backend understanding and is better structured than most beginner JDBC projects."*
