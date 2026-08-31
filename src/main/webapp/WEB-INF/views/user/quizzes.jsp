<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuizApp - Quizzes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <style>
        .quiz-container {
         padding: 30px;
        }

        .quiz-grid {
            display: grid;
            grid-template-columns:
            repeat(auto-fit, minmax(280px, 1fr));
            gap: 20px;
            margin-top: 25px;
        }

        .quiz-card {
            background: white;
            border-radius: 15px;
            padding: 25px;
            box-shadow:0 5px 20px rgba(0,0,0,0.08);
            transition: 0.2s;
        }

        .quiz-card:hover {
            transform: translateY(-4px);
        }

        .quiz-card h2 {
            margin-bottom: 10px;
        }

        .quiz-description {
            color: #666;
            min-height: 50px;
        }

        .quiz-info {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
            margin: 15px 0;
        }

        .badge {
            padding: 6px 12px;
            border-radius: 20px;
            background: #f1f3f5;
            font-size: 13px;
        }

        .start-btn {
            display: inline-block;
            padding: 10px 18px;
            background: #4f46e5;
            color: white;
            text-decoration: none;
            border-radius: 8px;
        }

        .empty {
            padding: 50px;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="quiz-container">
    <h1>Available Quizzes</h1>
    <p>Choose a quiz and test your knowledge.</p>
    <div class="quiz-grid">
        <c:choose>
            <c:when test="${not empty quizzes}">
                <c:forEach var="quiz" items="${quizzes}">
                    <div class="quiz-card">
                        <h2>${quiz.title}</h2>
                        <p class="quiz-description">${quiz.description}</p>
                        <div class="quiz-info">
                            <span class="badge">Category: ${quiz.category}</span>
                            <span class="badge">Difficulty: ${quiz.difficulty}</span>
                            <span class="badge">${quiz.time_limit} minutes</span>
                        </div>

                        <a href="${pageContext.request.contextPath}/quizzes?action=take&amp;quiz_id=${quiz.quiz_id}" class="start-btn">
                            Start Quiz →
                        </a>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="empty">
                    <h2>No quizzes available</h2>
                    <p>Please check again later.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>