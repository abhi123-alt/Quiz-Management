<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuizApp | Result</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: Arial, sans-serif;
            background: #f5f7fb;
            color: #222;
        }

        .result-container {
            width: 90%;
            max-width: 900px;
            margin: 50px auto;
        }

        .result-card {
            background: #ffffff;
            border-radius: 18px;
            padding: 40px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.08);
        }

        .result-header {
            text-align: center;
            margin-bottom: 35px;
        }

        .result-icon {
            width: 80px;
            height: 80px;
            margin: 0 auto 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            background: #eef2ff;
            font-size: 38px;
        }

        .result-header h1 {
            font-size: 32px;
            margin-bottom: 10px;
        }

        .result-header p {
            color: #666;
        }

        .quiz-title {
            text-align: center;
            margin-bottom: 30px;
        }

        .quiz-title h2 {
            font-size: 24px;
            margin-bottom: 6px;
        }

        .quiz-title p {
            color: #777;
        }

        .score-box {
            text-align: center;
            padding: 30px;
            margin-bottom: 30px;
            background: #f8f9ff;
            border-radius: 15px;
        }

        .score {
            font-size: 55px;
            font-weight: bold;
            margin-bottom: 8px;
        }

        .score-label {
            color: #666;
            font-size: 15px;
        }

        .statistics {
            display: grid;
            grid-template-columns:
            repeat(3, 1fr);
            gap: 18px;
            margin-bottom: 35px;
        }

        .stat {
            text-align: center;
            padding: 22px;
            background: #fafafa;
            border-radius: 12px;
        }

        .stat h3 {
            font-size: 26px;
            margin-bottom: 5px;
        }

        .stat p {
            color: #777;
            font-size: 14px;
        }

        .actions {
            display: flex;
            justify-content: center;
            gap: 15px;
            flex-wrap: wrap;
        }

        .btn {
            display: inline-block;
            padding: 12px 22px;
            border-radius: 9px;
            text-decoration: none;
            font-size: 15px;
            cursor: pointer;
        }

        .primary-btn {
            background: #4f46e5;
            color: white;
        }

        .secondary-btn {
            background: #eeeeee;
            color: #333;
        }

        .empty-result {
            text-align: center;
            padding: 50px;
        }

        @media (max-width: 650px) {
            .statistics {
                grid-template-columns: 1fr;
            }

            .result-card {
                padding: 25px;
            }

        }

    </style>
</head>
<body>
<div class="result-container">
    <div class="result-card">
        <!-- =====================================================
             RESULT HEADER
        ====================================================== -->

        <div class="result-header">
            <div class="result-icon">🏆</div>
            <h1> Quiz Completed! </h1>
            <p> Here is your quiz performance. </p>
        </div>

        <!-- =====================================================
             QUIZ INFORMATION
        ====================================================== -->
        <div class="quiz-title">
            <h2> ${quiz.title} </h2>
            <p> ${quiz.category}
                <c:if test="${not empty quiz.difficulty}">
                    • ${quiz.difficulty}
                </c:if>
            </p>
        </div>



        <!-- =====================================================
             SCORE
        ====================================================== -->

        <div class="score-box">
            <div class="score">
                ${percentage}%
            </div>
            <div class="score-label">
                Your Final Score
            </div>
        </div>



        <!-- =====================================================
             STATISTICS
        ====================================================== -->

        <div class="statistics">
            <!-- CORRECT -->
            <div class="stat">
                <h3> ${correctAnswers} </h3>
                <p> Correct Answers </p>
            </div>

            <!-- INCORRECT -->
            <div class="stat">
                <h3> ${wrongAnswers} </h3>
                <p> Wrong Answers </p>
            </div>

            <!-- TOTAL -->
            <div class="stat">
                <h3> ${totalQuestions} </h3>
                <p> Total Questions </p>
            </div>
        </div>

        <!-- =====================================================
             ACTIONS
        ====================================================== -->

        <div class="actions">
            <a href="${pageContext.request.contextPath}/quizzes?action=list" class="btn primary-btn">
                Take Another Quiz
            </a>

            <a href="${pageContext.request.contextPath}/history" class="btn secondary-btn">
                View History
            </a>

            <a href="${pageContext.request.contextPath}/dashboard" class="btn secondary-btn">
                Dashboard
            </a>

        </div>
    </div>
</div>
</body>
</html>