<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
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

        /* =====================================================
           SIDEBAR
           ===================================================== */

        .sidebar {
            position: fixed;
            left: 0;
            top: 0;
            width: 240px;
            height: 100vh;
            background: #111827;
            padding: 25px 15px;
            color: white;
        }

        .logo {
            font-size: 22px;
            font-weight: bold;
            padding: 0 12px;
            margin-bottom: 35px;
        }

        .logo span {
            color: #818cf8;
        }

        .nav-title {
            font-size: 11px;
            text-transform: uppercase;
            color: #9ca3af;
            padding: 0 12px;
            margin-bottom: 10px;
        }

        .nav-link {
            display: block;
            text-decoration: none;
            color: #d1d5db;
            padding: 12px;
            border-radius: 7px;
            margin-bottom: 5px;
            font-size: 14px;
        }

        .nav-link:hover {
            background: #1f2937;
            color: white;
        }

        .nav-link.active {
            background: #4f46e5;
            color: white;
        }

        .logout {
            position: absolute;
            bottom: 25px;
            left: 15px;
            right: 15px;
        }

        /* =====================================================
           MAIN
           ===================================================== */

        .main {
            margin-left: 240px;
            padding: 35px;
        }

        .topbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .topbar h1 {
            margin: 0 0 7px;
            font-size: 30px;
        }

        .topbar p {
            margin: 0;
            color: #6b7280;
        }

        .admin-name {
            background: white;
            padding: 10px 15px;
            border-radius: 8px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.05);
            font-size: 14px;
        }

        /* =====================================================
           STAT CARDS
           ===================================================== */

        .stats {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: white;
            padding: 22px;
            border-radius: 12px;
            box-shadow: 0 4px 18px rgba(0,0,0,0.05);
        }

        .stat-title {
            color: #6b7280;
            font-size: 13px;
            margin-bottom: 10px;
        }

        .stat-value {
            font-size: 28px;
            font-weight: bold;
        }

        .stat-description {
            margin-top: 7px;
            color: #9ca3af;
            font-size: 12px;
        }

        /* =====================================================
           MANAGEMENT
           ===================================================== */

        .section {
            margin-bottom: 30px;
        }

        .section-title {
            margin-bottom: 15px;
        }

        .section-title h2 {
            margin: 0 0 5px;
            font-size: 21px;
        }

        .section-title p {
            margin: 0;
            color: #6b7280;
            font-size: 14px;
        }

        .management-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }

        .management-card {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 18px rgba(0,0,0,0.05);
        }

        .management-card h3 {
            margin: 0 0 8px;
        }

        .management-card p {
            color: #6b7280;
            font-size: 14px;
            line-height: 1.5;
            min-height: 45px;
        }

        .card-button {
            display: inline-block;
            margin-top: 10px;
            padding: 10px 14px;
            background: #4f46e5;
            color: white;
            text-decoration: none;
            border-radius: 7px;
            font-size: 13px;
        }

        .card-button:hover {
            background: #4338ca;
        }

        /* =====================================================
           QUICK ACTIONS
           ===================================================== */

        .quick-actions {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }

        .quick-action {
            background: white;
            padding: 14px 18px;
            border-radius: 8px;
            text-decoration: none;
            color: #374151;
            box-shadow: 0 3px 12px rgba(0,0,0,0.05);
            font-size: 14px;
        }

        .quick-action:hover {
            color: #4f46e5;
        }

        /* =====================================================
           RESPONSIVE
           ===================================================== */

        @media (max-width: 1000px) {

            .stats {
                grid-template-columns: repeat(2, 1fr);
            }

            .management-grid {
                grid-template-columns: 1fr;
            }

        }

        @media (max-width: 700px) {

            .sidebar {
                position: relative;
                width: 100%;
                height: auto;
            }

            .logout {
                position: static;
                margin-top: 20px;
            }

            .main {
                margin-left: 0;
                padding: 20px;
            }

            .stats {
                grid-template-columns: 1fr;
            }

            .topbar {
                align-items: flex-start;
                flex-direction: column;
                gap: 15px;
            }

        }

    </style>
</head>
<body>

<!-- =========================================================
     SIDEBAR
     ========================================================= -->

<div class="sidebar">
    <div class="logo">
        Online<span>Quiz</span>
    </div>

    <div class="nav-title">
        Administration
    </div>

    <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link active">
        Dashboard
    </a>

    <a href="${pageContext.request.contextPath}/admin/quizzes?action=list" class="nav-link">
        Manage Quizzes
    </a>

    <a href="${pageContext.request.contextPath}/admin/quizzes?action=add" class="nav-link">
        Create Quiz
    </a>

    <div class="nav-title" style="margin-top:25px;">
        Account
    </div>

    <a href="${pageContext.request.contextPath}/logout" class="nav-link"> Logout </a>
</div>

<!-- =========================================================
     MAIN CONTENT
     ========================================================= -->

<div class="main">
    <!-- =====================================================
         TOP BAR
         ===================================================== -->
    <div class="topbar">
        <div>
            <h1>Admin Dashboard</h1>
            <p>
                Manage your online quiz system from here.
            </p>
        </div>

        <div class="admin-name">
            ${user.name}
        </div>
    </div>

    <!-- =====================================================
         STATISTICS
         ===================================================== -->
    <div class="stats">
        <div class="stat-card">
            <div class="stat-title">
                Total Quizzes
            </div>

            <div class="stat-value">
                ${totalQuizzes != null ? totalQuizzes : 0}
            </div>

            <div class="stat-description">
                Quizzes available in the system
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-title">
                Total Questions
            </div>

            <div class="stat-value">
                ${totalQuestions != null ? totalQuestions : 0}
            </div>

            <div class="stat-description">
                Questions across all quizzes
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-title"> Total Attempts </div>
            <div class="stat-value"> ${totalAttempts != null ? totalAttempts : 0} </div>
            <div class="stat-description"> Quiz attempts submitted </div>
        </div>

        <div class="stat-card">
            <div class="stat-title"> Total Users</div>
            <div class="stat-value"> ${totalUsers != null ? totalUsers : 0} </div>
            <div class="stat-description"> Registered users </div>
        </div>
    </div>

    <!-- =====================================================
         MANAGEMENT
         ===================================================== -->

    <div class="section">
        <div class="section-title">
            <h2>Quiz Management</h2>
            <p>
                Manage quizzes and their questions.
            </p>
        </div>

        <div class="management-grid">
            <!-- =================================================
                 MANAGE QUIZZES
                 ================================================= -->

            <div class="management-card">
                <h3>
                    Manage Quizzes
                </h3>
                <p>
                    View all quizzes, edit quiz information,
                    delete quizzes and manage their questions.
                </p>
                <a href="${pageContext.request.contextPath}/admin/quizzes?action=list" class="card-button">
                    Manage Quizzes
                </a>
            </div>

            <!-- =================================================
                 CREATE QUIZ
                 ================================================= -->

            <div class="management-card">
                <h3>
                    Create Quiz
                </h3>
                <p>
                    Create a new quiz by entering its title,
                    category, difficulty and time limit.
                </p>
                <a href="${pageContext.request.contextPath}/admin/quizzes?action=add" class="card-button">
                    Create Quiz
                </a>
            </div>

            <!-- =================================================
                 QUESTIONS
                 ================================================= -->

            <div class="management-card">
                <h3>
                    Manage Questions
                </h3>
                <p>
                    Select a quiz from the quiz management page
                    to add, edit or delete its questions.
                </p>
                <a href="${pageContext.request.contextPath}/admin/quizzes?action=list" class="card-button">
                    Open Quiz Manager
                </a>
            </div>
        </div>
    </div>

    <!-- =====================================================
         QUICK ACTIONS
         ===================================================== -->

    <div class="section">
        <div class="section-title">
            <h2>Quick Actions</h2>
            <p>
                Frequently used administration functions.
            </p>
        </div>

        <div class="quick-actions">
            <a href="${pageContext.request.contextPath}/admin/quizzes?action=add" class="quick-action">
                + Create Quiz
            </a>

            <a href="${pageContext.request.contextPath}/admin/quizzes?action=list" class="quick-action">
                View All Quizzes
            </a>

            <a href="${pageContext.request.contextPath}/logout" class="quick-action">
                Logout
            </a>
        </div>
    </div>
</div>
</body>
</html>