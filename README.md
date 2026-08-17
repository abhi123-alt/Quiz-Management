# 🎓 Online Quiz App

An online quiz application built with **Java Servlets, JSP, JDBC, and MySQL**.  
This project demonstrates user authentication, role-based dashboards (Admin/User), and quiz management features.

---

## 🚀 Features
- User Registration & Login (with session management)
- Role-based dashboards:
  - **Admin**: Manage quizzes, view users
  - **User**: Attempt quizzes, view results
- Secure session handling
- MySQL database integration with DAO & Service layers
- MVC architecture (Controller → Service → DAO → Model → View)

---

## 🛠️ Tech Stack
- **Backend**: Java Servlets, JSP
- **Database**: MySQL
- **Server**: Apache Tomcat 10
- **Build Tool**: Maven
- **IDE**: IntelliJ IDEA / Eclipse

---

## 📂 Project Structure
online-quiz-app/
├── src/main/java/com/quizapp/controller   # Servlets (Login, Register, Dashboard)
├── src/main/java/com/quizapp/service      # Business logic
├── src/main/java/com/quizapp/dao          # Database access (UserDAO, QuizDAO)
├── src/main/java/com/quizapp/model        # POJOs (User, Quiz, etc.)
├── src/main/java/com/quizapp/util         # DBConnection utility
├── src/main/webapp/WEB-INF/views          # JSP pages (auth, user, admin)
└── pom.xml                                # Maven dependencies

---

🤝 Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

---

## 📸 Screenshots

### 📝Landing Page
<img width="1765" height="871" alt="image" src="https://github.com/user-attachments/assets/392cc9f3-efab-403d-b4de-88d36258c1e5" />

### 🔐 Login Page
<img width="950" height="742" alt="image" src="https://github.com/user-attachments/assets/5bb31b81-3c22-4eef-a730-c89182688322" />

### 📝 Registration Page
<img width="567" height="839" alt="image" src="https://github.com/user-attachments/assets/e8153159-9a41-4a26-a41b-34ee2a0de449" />

### 👤 User Dashboard
<img width="1852" height="856" alt="image" src="https://github.com/user-attachments/assets/3e8920bd-7abd-48ca-a93a-afbc32954154" />

### 🛠️ Admin Dashboard


