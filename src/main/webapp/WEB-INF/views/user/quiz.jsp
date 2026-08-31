<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${quiz.title}</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f7fb;
            padding: 30px;
        }

        .container {
            max-width: 900px;
            margin: auto;
        }

        .header {
            background: white;
            padding: 25px;
            border-radius: 12px;
            margin-bottom: 25px;
        }

        .question-card {
            background: white;
            padding: 25px;
            margin-bottom: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
        }

        .question-number {
            color: #4f46e5;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .question-text {
            font-size: 20px;
            margin-bottom: 20px;
        }

        .option {
            display: block;
            padding: 14px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 8px;
            cursor: pointer;
        }

        .option:hover {
            background: #f3f4ff;
        }

        .option input {
            margin-right: 10px;
        }

        .submit-btn {
            padding: 13px 25px;
            background: #4f46e5;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
        }

    </style>

</head>
<body>
<div class="container">
    <div class="header">
        <h1>${quiz.title}</h1>
        <p>
            ${quiz.description}
        </p>
    </div>

   <form method="post" action="${pageContext.request.contextPath}/quizzes">
       <input type="hidden" name="action" value="submit">
       <input type="hidden" name="quiz_id" value="${quiz.quiz_id}">
       <c:forEach var="question" items="${questions}" varStatus="status">
           <div class="question-card">

               <div class="question-number">
                   Question ${status.index + 1}
               </div>

               <div class="question-text">
                   ${question.question_text}
               </div>

               <c:forEach var="option" items="${question.options}">
                   <label class="option">
                       <input type="radio"
                              name="question_${question.question_id}"
                              value="${option.option_id}"
                              required>
                       ${option.option_text}
                   </label>
               </c:forEach>
           </div>
       </c:forEach>
       <button type="submit" class="submit-btn"> Submit Quiz </button>
   </form>
</div>
</body>
</html>