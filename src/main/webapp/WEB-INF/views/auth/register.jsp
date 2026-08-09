<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Create Account | StudyTrack</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-page">
<div class="auth-container">
    <div class="auth-card register-card">
        <div class="auth-logo">🎓 <span>StudyTrack</span> </div>
        <h2>Create Your Account 🚀</h2>
        <p class="auth-subtitle">Start your study journey today</p>
        <% String error = (String) request.getAttribute("error");
           if(error != null){ %>
            <div class="error-message"><%= error %></div>
            <% } %>
        <% String success = (String) request.getAttribute("success");
           if(success != null){ %>
            <div class="success-message"><%= success %></div>
        <% } %>
        <form action="${pageContext.request.contextPath}/register" method="post" onsubmit="return validateForm()">
            <div class="input-group">
            <label>Full Name</label><input type="text" name="name" placeholder="Enter your full name" required>
            </div>
            <div class="input-group">
                <label>Email Address</label>
                <input type="email" name="email" id="email" placeholder="Enter your email"required>
            </div>

            <div class="input-group">
                <label>Password</label>
                <div class="password-box">
                    <input type="password" id="password" name="password" placeholder="Create password" required>
                    <span class="toggle-password" onclick="togglePassword('password')">👁</span>
                </div>
            </div>

            <div class="input-group">
                <label>Confirm Password</label>
                <div class="password-box">
                    <input type="password"id="confirmPassword"name="confirmPassword"placeholder="Confirm password"required>
                    <span class="toggle-password"onclick="togglePassword('confirmPassword')">👁</span>
                </div>
            </div>
            <button type="submit" class="auth-btn">Create Account</button>
        </form>
        <div class="auth-bottom-text">Already have an account?<a href="${pageContext.request.contextPath}/login">Login</a></div>
    </div>
</div>
</body>
</html>