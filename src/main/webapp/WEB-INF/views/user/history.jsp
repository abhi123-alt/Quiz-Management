<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/history.css">
</head>
<body>
<div class="page-container">
    <!-- ================= SIDEBAR ================= -->
    <aside class="sidebar">
        <div class="logo"> QuizApp </div>
        <nav>
            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/quizzes">Take Quiz</a>
            <a class="active" href="${pageContext.request.contextPath}/history"> History </a>
            <a href="${pageContext.request.contextPath}/logout"> Logout </a>
        </nav>
    </aside>
    <!-- ================= MAIN ================= -->
    <main class="main-content">
        <div class="top-section">
            <div>
                <h1>Quiz History</h1>
                <p> View all the quizzes you have attempted. </p>
            </div>
            <a class="take-btn" href="${pageContext.request.contextPath}/quizzes"> Take New Quiz </a>
        </div>
        <!-- ================= HISTORY ================= -->
        <div class="history-card">
            <c:choose>
                <c:when test="${empty history}">
                    <div class="empty-history">
                        <h2>No Quiz Attempts Yet</h2>
                        <p> You haven't attempted any quiz yet.</p>
                        <a href="${pageContext.request.contextPath}/quizzes" class="start-btn"> Start Your First </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-container">
                        <table>
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Quiz</th>
                                <th>Category</th>
                                <th>Difficulty</th>
                                <th>Score</th>
                                <th>Percentage</th>
                                <th>Date</th>
                                <th>Result</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach
                                    var="item"
                                    items="${history}"
                                    varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td class="quiz-title"> ${item.title}</td>
                                    <td> <span class="category">${item.category}</span></td>
                                    <td><span class="difficulty">${item.difficulty}</span></td>
                                    <td class="score">${item.score}/${item.total_questions}</td>
                                    <td><strong>${String.format("%.1f", item.percentage)}%</strong></td>
                                    <td>${item.completed_at}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${item.percentage >= 80}">
                                                <span class="result excellent">Excellent</span>
                                            </c:when>
                                            <c:when test="${item.percentage >= 60}">
                                                <span class="result good">Good</span>
                                            </c:when>
                                            <c:when test="${item.percentage >= 40}">
                                                <span class="result average">Average</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="result poor">Needs Improvement</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
</div>
</body>
</html>