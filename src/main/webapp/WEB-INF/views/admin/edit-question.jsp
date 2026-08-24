<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Question</title>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f5f7fb;
        }

        .container {
            max-width: 800px;
            margin: 40px auto;
            padding: 20px;
        }

        .card {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.06);
        }

        h1 {
            margin-top: 0;
        }

        .subtitle {
            color: #6b7280;
        }

        .field {
            margin-bottom: 22px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 8px;
        }

        textarea,
        input[type="text"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #d1d5db;
            border-radius: 7px;
            font-size: 15px;
        }

        textarea {
            min-height: 120px;
            resize: vertical;
        }

        .option {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 12px;
        }

        .option input[type="text"] {
            flex: 1;
        }

        .actions {
            display: flex;
            gap: 10px;
            margin-top: 25px;
        }

        button,
        .back {
            border: none;
            padding: 11px 18px;
            border-radius: 7px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
        }

        button {
            background: #4f46e5;
            color: white;
        }

        .back {
            background: #6b7280;
            color: white;
        }

        .error {
            background: #fee2e2;
            color: #991b1b;
            padding: 12px;
            border-radius: 7px;
            margin-bottom: 20px;
        }

    </style>

</head>
<body>
<div class="container">
    <div class="card">
        <h1>Edit Question</h1>
        <p class="subtitle">
            Question ID:
            <strong>${question.question_id}</strong>
            |
            Quiz ID:
            <strong>${question.quiz_id}</strong>
        </p>

        <c:if test="${not empty error}">
            <div class="error">
                ${error}
            </div>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/admin/questions">
            <input type="hidden"  name="action" value="update">
            <input type="hidden" name="question_id" value="${question.question_id}">
            <div class="field">
                <label for="question_text"> Question</label>
                <textarea id="question_text" name="question_text" required>${question.question_text}</textarea>
            </div>
            <div class="field">
                <label> Options </label>
                <c:forEach var="option" items="${options}" varStatus="status">
                    <div class="option">
                        <input type="hidden" name="option_id" value="${option.option_id}">
                        <input type="radio" name="correct_option" value="${status.index}" ${option.isIs_correct() ? 'checked' : ''}>
                        <input type="text" name="option_text" value="${option.option_text}" required>
                    </div>
                </c:forEach>
            </div>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/admin/questions?action=list&quiz_id=${question.quiz_id}" class="back">
                    Cancel
                </a>
                <button type="submit"> Update Question </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>