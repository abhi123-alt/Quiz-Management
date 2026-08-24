<%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <!DOCTYPE html>
 <html lang="en">
 <head>
     <meta charset="UTF-8">
     <title>Login | StudyTrack</title>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
 </head>

 <body class="auth-page">
 <div class="auth-container">
     <div class="auth-card">
         <div class="auth-logo">🎓 <span>StudyTrack</span></div>
         <h2>Welcome Back 👋</h2>
         <p class="auth-subtitle">Continue your study journey</p>
                  <% String error = (String) request.getAttribute("error");
                      if(error != null){
                  %>
                  <div class="error-message"><%= error %></div>
                  <% } %>
         <form action="${pageContext.request.contextPath}/login" method="post">

             <div class="input-group">
                 <label>Email Address</label>
                 <input type="email"name="email"placeholder="Enter your email"required>
             </div>

             <div class="input-group">
                 <label>Password</label>
                 <div class="password-box">
                     <input type="password"id="password"name="password"placeholder="Enter your password"required>
                     <span class="toggle-password"onclick="togglePassword('password')">👁</span>
                 </div>
             </div>


             <div class="input-group">
                 <label>Role</label>
                 <input type="text" name="role" placeholder="Enter the role(Admin/User)" required>
             </div>


             <button type="submit" class="auth-btn">Login</button>
         </form>
         <div class="auth-bottom-text">Don't have an account?
             <a href="${pageContext.request.contextPath}/register">Register</a>
         </div>
     </div>
 </div>
 <script src="${pageContext.request.contextPath}/js/script.js"></script>
 </body>
 </html>
