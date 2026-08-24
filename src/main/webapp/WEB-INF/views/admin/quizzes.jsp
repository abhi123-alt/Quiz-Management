<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Quizzes</title>

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
            max-width: 1250px;
            margin: 40px auto;
            padding: 0 20px;
        }

        /* ================= HEADER ================= */

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 20px;
            margin-bottom: 30px;
        }

        .header h1 {
            margin: 0 0 7px;
            font-size: 30px;
        }

        .header p {
            margin: 0;
            color: #6b7280;
        }

        .header-actions {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }

        /* ================= BUTTONS ================= */

        .btn {
            display: inline-block;
            text-decoration: none;
            padding: 11px 17px;
            border-radius: 7px;
            font-size: 14px;
            border: none;
            cursor: pointer;
        }

        .btn-primary {
            background: #4f46e5;
            color: white;
        }

        .btn-secondary {
            background: #6b7280;
            color: white;
        }

        .btn-question {
            background: #eef2ff;
            color: #4338ca;
        }

        .btn-edit {
            background: #ecfdf5;
            color: #047857;
        }

        .btn-delete {
            background: #fee2e2;
            color: #dc2626;
        }

        /* ================= CARD ================= */

        .card {
            background: white;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
        }

        /* ================= TABLE ================= */

        .table-wrapper {
            width: 100%;
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            text-align: left;
            padding: 14px 12px;
            background: #f9fafb;
            color: #6b7280;
            font-size: 13px;
            text-transform: uppercase;
            border-bottom: 1px solid #e5e7eb;
        }

        td {
            padding: 16px 12px;
            border-bottom: 1px solid #e5e7eb;
            vertical-align: middle;
        }

        tr:last-child td {
            border-bottom: none;
        }

        .quiz-title {
            font-weight: bold;
            font-size: 16px;
        }

        .description {
            max-width: 280px;
            color: #6b7280;
            font-size: 14px;
            line-height: 1.4;
        }

        /* ================= BADGES ================= */

        .badge {
            display: inline-block;
            padding: 5px 9px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
        }

        .category {
            background: #f3f4f6;
            color: #374151;
        }

        .difficulty {
            background: #fff7ed;
            color: #c2410c;
        }

        /* ================= ACTIONS ================= */

        .actions {
            display: flex;
            gap: 7px;
            flex-wrap: wrap;
        }

        .action {
            display: inline-block;
            text-decoration: none;
            padding: 7px 10px;
            border-radius: 6px;
            font-size: 12px;
            font-weight: 500;
        }

        /* ================= EMPTY ================= */

        .empty {
            text-align: center;
            padding: 70px 20px;
        }

        .empty h2 {
            margin-bottom: 8px;
        }

        .empty p {
            color: #6b7280;
            margin-bottom: 25px;
        }

        /* ================= MOBILE ================= */

        @media (max-width: 768px) {

            .header {
                flex-direction: column;
                align-items: flex-start;
            }

            .header-actions {
                width: 100%;
            }

            .header-actions .btn {
                flex: 1;
                text-align: center;
            }

            .card {
                padding: 15px;
            }

        }

    </style>

</head>


<body>


<div class="container">
    <!-- =====================================================
         HEADER
         ===================================================== -->
    <div class="header">
        <div>
            <h1>Manage Quizzes</h1>
            <p>
                Create, edit, delete and manage questions for quizzes.
            </p>
        </div>
        <div class="header-actions">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary"> Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/quizzes?action=add" class="btn btn-primary">
                + Create Quiz
            </a>
        </div>
    </div>

    <!-- =====================================================
         QUIZ CARD
         ===================================================== -->

    <div class="card">
    <!-- =================================================
                     QUIZZES AVAILABLE
                     ================================================= -->
        <c:choose>
            <c:when test="${not empty quizzes}">
                <div class="table-wrapper">
                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Quiz</th>
                            <th>Category</th>
                            <th>Difficulty</th>
                            <th>Time Limit</th>
                            <th>Created By</th>
                            <th>Created At</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="quiz" items="${quizzes}">
                            <tr>
                                <!-- ================= ID ================= -->
                                <td> ${quiz.quiz_id}</td>
                                <!-- ================= TITLE ================= -->
                                <td> <div class="quiz-title"> ${quiz.title}</div>
                                    <c:if test="${not empty quiz.description}">
                                        <div class="description">
                                            ${quiz.description}
                                        </div>
                                    </c:if>
                                </td>
                                <!--================= CATEGORY ================= -->
                                <td>
                                    <span class="badge category">
                                        ${quiz.category}
                                    </span>
                                </td>
                                <!-- ================= DIFFICULTY ================= -->
                                <td>
                                    <span class="badge difficulty">
                                        ${quiz.difficulty}
                                    </span>
                                </td>
                                <!-- ================= TIME ================= -->
                                <td> ${quiz.time_limit} min </td>

                                <!-- ================= CREATED BY ================= -->
                                <td> ${quiz.created_by}</td>

                                <!-- ================= CREATED AT ================= -->
                                <td> ${quiz.created_at} </td>

                                <!-- ================= ACTIONS ================= -->
                                <td>
                                    <div class="actions">
                                        <!-- QUESTIONS -->
                                        <a href="${pageContext.request.contextPath}/admin/question?action=list&quiz_id=${quiz.quiz_id}" class="action btn-question">
                                            Questions
                                        </a>
                                        <!-- EDIT -->
                                        <a href="${pageContext.request.contextPath}/admin/quizzes?action=edit&quiz_id=${quiz.quiz_id}" class="action btn-edit">
                                            Edit
                                        </a>
                                        <!-- DELETE -->
                                        <a href="${pageContext.request.contextPath}/admin/quizzes?action=delete&quiz_id=${quiz.quiz_id}" class="action btn-delete"
                                                onclick="return confirm('Are you sure you want to delete this quiz?');">
                                            Delete
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>

            <c:otherwise>
        <!-- =================================================
                             NO QUIZZES
        ================================================= -->

                <div class="empty">
                    <h2>No Quizzes Found</h2>
                    <p> There are currently no quizzes in the system. </p>

                    <a href="${pageContext.request.contextPath}/admin/quizzes?action=add" class="btn btn-primary">
                        Create Your First Quiz
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>