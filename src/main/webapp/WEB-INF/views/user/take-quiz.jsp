<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Start Quiz</title>

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
            margin: 50px auto;
            padding: 20px;
        }

        .card {
            background: white;
            border-radius: 14px;
            padding: 35px;
            box-shadow: 0 5px 25px rgba(0,0,0,0.08);
        }

        .header {
            margin-bottom: 25px;
        }

        .header h1 {
            margin: 0 0 10px;
            font-size: 30px;
        }

        .header p {
            margin: 0;
            color: #6b7280;
            line-height: 1.6;
        }

        .quiz-info {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 15px;
            margin: 25px 0;
        }

        .info-box {
            background: #f8fafc;
            border: 1px solid #e5e7eb;
            border-radius: 10px;
            padding: 18px;
        }

        .info-box span {
            display: block;
            font-size: 13px;
            color: #6b7280;
            margin-bottom: 6px;
        }

        .info-box strong {
            font-size: 17px;
        }

        .description {
            background: #f9fafb;
            border-left: 4px solid #4f46e5;
            padding: 18px;
            margin: 25px 0;
            line-height: 1.6;
        }

        .warning {
            background: #fff7ed;
            border: 1px solid #fed7aa;
            color: #9a3412;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
        }

        .actions {
            display: flex;
            gap: 12px;
            margin-top: 30px;
        }

        .btn {
            border: none;
            border-radius: 8px;
            padding: 13px 22px;
            font-size: 15px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }

        .btn-primary {
            background: #4f46e5;
            color: white;
        }

        .btn-primary:hover {
            background: #4338ca;
        }

        .btn-secondary {
            background: #6b7280;
            color: white;
        }

        .btn-secondary:hover {
            background: #4b5563;
        }

        @media (max-width: 650px) {

            .quiz-info {
                grid-template-columns: 1fr;
            }

            .card {
                padding: 22px;
            }

            .actions {
                flex-direction: column;
            }

            .btn {
                text-align: center;
            }
        }

    </style>
</head>
<body>
<div class="container">
    <div class="card">
        <div class="header">
            <h1>${quiz.title}</h1>
            <p> Review the quiz information before starting. </p>
        </div>

        <div class="quiz-info">
            <div class="info-box">
                <span>Category</span>
                <strong> ${quiz.category} </strong>
            </div>

            <div class="info-box">
                <span>Difficulty</span>
                <strong> ${quiz.difficulty} </strong>
            </div>

            <div class="info-box">
                <span>Time Limit</span>
                <strong> ${quiz.time_limit} minutes </strong>
            </div>
        </div>


        <div class="description">
            <strong>Description</strong>
            <p> ${quiz.description} </p>
        </div>

        <div class="warning">
            <strong>Before you start:</strong>
            <ul>
                <li>Make sure you are ready to complete the quiz.</li>
                <li>The quiz attempt will be recorded.</li>
                <li>Your score will be saved after submission.</li>
                <li>The time limit is ${quiz.time_limit} minutes.</li>
            </ul>
        </div>

        <!--
            This form sends the quiz_id to AttemptServlet.
            AttemptServlet expects the parameter name "quizId".
        -->

        <form method="post" action="${pageContext.request.contextPath}/create-attempt">
            <input type="hidden" name="quiz_id" value="${quiz.quiz_id}">
            <div class="actions">
                <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">
                    Cancel
                </a>

                <button type="submit" class="btn btn-primary">
                    Start Quiz →
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>