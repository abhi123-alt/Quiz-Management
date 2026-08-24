<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Questions</title>
    <style>
        * {
            box-sizing: border-box;
        }
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f5f7fb;
            color: #222;
        }

        .container {
            max-width: 1100px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .header h1 {
            margin: 0 0 6px;
        }

        .header p {
            margin: 0;
            color: #6b7280;
        }

        .btn {
            text-decoration: none;
            padding: 11px 17px;
            border-radius: 7px;
            background: #4f46e5;
            color: white;
            display: inline-block;
        }

        .back {
            background: #6b7280;
        }

        .card {
            background: white;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 4px 18px rgba(0,0,0,0.06);
        }

        .question {
            border: 1px solid #e5e7eb;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 15px;
        }

        .question-number {
            font-size: 13px;
            color: #6b7280;
            margin-bottom: 8px;
        }

        .question-text {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 15px;
        }

        .actions {
            display: flex;
            gap: 8px;
        }

        .action {
            padding: 7px 11px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 13px;
        }

        .edit {
            background: #eef2ff;
            color: #4338ca;
        }

        .delete {
            background: #fee2e2;
            color: #dc2626;
        }

        .empty {
            text-align: center;
            padding: 50px;
            color: #6b7280;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <div>
            <h1>Manage Questions</h1>
            <p>
                Manage questions for Quiz ID:
                <strong>${quiz_id}</strong>
            </p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/admin/quizzes?action=list" class="btn back">
                Back to Quizzes
            </a>

            <a href="${pageContext.request.contextPath}/admin/question?action=add&quiz_id=${quiz_id}" class="btn">
                + Add Question
            </a>
        </div>
    </div>
    <div class="card">
        <c:choose>
            <c:when test="${not empty questions}">
                <c:forEach var="question"
                           items="${questions}"
                           varStatus="status">
                    <div class="question">
                        <div class="question-number">
                            Question ${status.index + 1}
                        </div>
                        <div class="question-text">
                            ${question.question_text}
                        </div>
                        <div class="actions">
                            <a href="${pageContext.request.contextPath}/admin/question?action=edit&question_id=${question.question_id}" class="action edit">
                                Edit
                            </a>

                            <a href="${pageContext.request.contextPath}/admin/question?action=delete&question_id=${question.question_id}" class="action delete"
                             onclick="return confirm('Delete this question and its options?');">
                                Delete
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="empty">
                    <h3>No questions found</h3>
                    <p> This quiz does not have any questions yet. </p> <br>
                    <a href="${pageContext.request.contextPath}/admin/question?action=add&quiz_id=${quiz_id}" class="btn">
                        Add First Question
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>