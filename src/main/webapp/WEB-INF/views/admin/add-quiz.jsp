<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Quiz</title>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f5f7fb;
            color: #1f2937;
        }

        .container {
            max-width: 850px;
            margin: 40px auto;
            padding: 20px;
        }

        .card {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.06);
        }

        .header {
            margin-bottom: 25px;
        }

        .header h1 {
            margin: 0 0 8px;
        }

        .header p {
            margin: 0;
            color: #6b7280;
        }

        .field {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }

        input,
        textarea,
        select {
            width: 100%;
            padding: 12px;
            border: 1px solid #d1d5db;
            border-radius: 7px;
            font-size: 15px;
            font-family: inherit;
        }

        textarea {
            min-height: 130px;
            resize: vertical;
        }

        input:focus,
        textarea:focus,
        select:focus {
            outline: none;
            border-color: #4f46e5;
        }

        .row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }

        .actions {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }

        .btn {
            border: none;
            padding: 12px 20px;
            border-radius: 7px;
            text-decoration: none;
            cursor: pointer;
            font-size: 14px;
        }

        .btn-primary {
            background: #4f46e5;
            color: white;
        }

        .btn-secondary {
            background: #6b7280;
            color: white;
        }

        .error {
            background: #fee2e2;
            color: #991b1b;
            padding: 13px;
            border-radius: 7px;
            margin-bottom: 20px;
        }

        .required {
            color: #dc2626;
        }

        @media (max-width: 650px) {

            .row {
                grid-template-columns: 1fr;
                gap: 0;
            }

            .card {
                padding: 20px;
            }

        }

    </style>
</head>
<body>
<div class="container">
    <div class="card">
        <!-- =================================================
             HEADER
             ================================================= -->

        <div class="header">
            <h1>Create New Quiz</h1>
            <p> Enter the quiz information below. </p>
        </div>

        <%
            String error = (String) request.getAttribute("error");
        %>

        <% if (error != null) { %>
            <div class="error">
                <%= error %>
            </div>
        <% } %>

        <!-- =================================================
             FORM
             ================================================= -->

        <form method="post" action="${pageContext.request.contextPath}/admin/quizzes">
            <!-- ACTION -->
            <input type="hidden" name="action" value="add">

            <div class="field">
                <label for="title">
                    Quiz Title
                    <span class="required">*</span>
                </label>
                <input type="text"  id="title" name="title" value="${param.title}" placeholder="Enter quiz title" maxlength="255" required>
            </div>

            <div class="field">
                <label for="description">
                    Description
                </label>
                <textarea id="description" name="description" placeholder="Enter quiz description">${param.description}</textarea>
            </div>

            <div class="row">
                <div class="field">
                    <label for="category">
                        Category
                        <span class="required">*</span>
                    </label>
                    <input type="text" id="category" name="category" value="${param.category}" placeholder="e.g. Java" maxlength="100" required>
                </div>

                <div class="field">
                    <label for="difficulty">
                        Difficulty
                        <span class="required">*</span>
                    </label>
                    <select id="difficulty" name="difficulty" required>
                        <option value=""> Select difficulty </option>
                        <option value="Easy" ${param.difficulty == 'Easy' ? 'selected' : ''}> Easy </option>
                        <option value="Medium" ${param.difficulty == 'Medium' ? 'selected' : ''}> Medium</option>
                        <option value="Hard" ${param.difficulty == 'Hard' ? 'selected' : ''}> Hard </option>
                    </select>
                </div>
            </div>

            <div class="row">
                <div class="field">
                    <label for="time_limit">
                        Time Limit (minutes)
                        <span class="required">*</span>
                    </label>
                    <input type="number" id="time_limit" name="time_limit" value="${param.time_limit}" placeholder="e.g. 30" min="1" max="300" required>
                </div>

                <div class="field">
                    <label for="created_by">
                        Created By
                        <span class="required">*</span>
                    </label>
                    <input type="number" id="created_by" name="created_by" value="${param.created_by}" placeholder="User ID" min="1" required>
                </div>
            </div>

            <div class="actions">
                <a href="${pageContext.request.contextPath}/admin/quizzes?action=list" class="btn btn-secondary"> Cancel</a>
                <button type="submit" class="btn btn-primary"> Create Quiz </button>
            </div>

        </form>
    </div>
</div>
</body>
</html>