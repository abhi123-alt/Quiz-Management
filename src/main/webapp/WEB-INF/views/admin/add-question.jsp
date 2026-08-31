<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Question</title>

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
            margin-bottom: 25px;
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

        .field {
            margin-bottom: 20px;
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

        button,finish,
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

        .finish {
               background: #4f46e5;
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
        <h1>Add Question</h1>
        <p class="subtitle">
            Add a question to Quiz ID:
            <strong>${quiz_id}</strong>
        </p>
        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <div class="error">
                <%= error %>
            </div>
        <% } %>
        <form method="post" action="${pageContext.request.contextPath}/admin/questions">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="quiz_id" value="${quiz_id}">
            <div class="field">
                <label for="question_text"> Question </label>
                <textarea id="question_text" name="question_text" placeholder="Enter your question..." required></textarea>
            </div>

            <div class="field">
                <label>Options</label>
                <div class="option">
                    <input type="radio"
                           name="correct_option"
                           value="1"
                           required>

                    <input type="text"
                           name="option_1"
                           placeholder="Option 1"
                           required>
                </div>

                <div class="option">
                    <input type="radio"
                           name="correct_option"
                           value="2">

                    <input type="text"
                           name="option_2"
                           placeholder="Option 2"
                           required>
                </div>

                <div class="option">
                    <input type="radio"
                           name="correct_option"
                           value="3">

                    <input type="text"
                           name="option_3"
                           placeholder="Option 3"
                           required>
                </div>

                <div class="option">
                    <input type="radio"
                           name="correct_option"
                           value="4">

                    <input type="text"
                           name="option_4"
                           placeholder="Option 4"
                           required>
                </div>
            </div>

            <div class="actions">
                <a href="${pageContext.request.contextPath}/admin/questions?action=list&quiz_id=${quiz_id}"
                   class="back">
                    Cancel
                </a>

                <button type="submit">
                    Save Question
                </button>

                <a href="${pageContext.request.contextPath}/admin/questions?action=list&quiz_id=${quiz_id}" class="finish">
                    Finish Quiz
                </a>
            </div>
        </form>
    </div>
</div>
</body>
</html>