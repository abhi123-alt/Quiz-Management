<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuizApp | Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
<div class="app">
    <!-- ==============================SIDEBAR=============================== -->
    <aside class="sidebar" id="sidebar">
        <div class="logo">
            <div class="logo-icon">Q</div>
            <span>QuizApp</span>
        </div>
        <nav class="navigation">
            <a href="${pageContext.request.contextPath}/dashboard"class="nav-link active">
                <span>⌂</span>
                <span>Dashboard</span>
            </a>
            <a href="${pageContext.request.contextPath}/quizzes"class="nav-link">
                <span>📝</span>
                <span>Take Quiz</span>
            </a>

            <a href="${pageContext.request.contextPath}/history"
               class="nav-link">
                <span>📊</span>
                <span>My Results</span>
            </a>

            <a href="${pageContext.request.contextPath}/profile"
               class="nav-link">
                <span>👤</span>
                <span>Profile</span>
            </a>
        </nav>

        <div class="sidebar-bottom">
            <a href="${pageContext.request.contextPath}/logout"class="nav-link logout">
                <span>↪</span>
                <span>Logout</span>
            </a>
        </div>
    </aside>

    <!-- ==============================MAIN=============================== -->
    <main class="main">
        <!-- TOP BAR -->
        <header class="topbar">
            <button class="menu-btn"onclick="toggleSidebar()">☰</button>
            <div class="page-title">
                <h1>Dashboard</h1>
                <p>Track your learning and test your knowledge.</p>
            </div>
            <div class="profile-mini">
                <div class="avatar">
                    <c:choose>
                        <c:when test="${not empty sessionScope.user.name}">${sessionScope.user.name.substring(0,1)}</c:when>
                        <c:otherwise>U</c:otherwise>
                    </c:choose>
                </div>
                <div class="profile-name">
                    <strong>${sessionScope.user.name}</strong>
                    <small>Student</small>
                </div>
            </div>
        </header>

<!-- ==============================WELCOME SECTION=============================== -->
        <section class="welcome">
            <div class="welcome-text">
                <span class="welcome-label">Welcome back 👋</span>
                <h2>${sessionScope.user.name}</h2>
                <p>Ready to challenge yourself today?
                    Choose a quiz and test your knowledge.
                </p>
                <a href="${pageContext.request.contextPath}/quizzes"class="primary-btn">
                    Take a Quiz
                    <span>→</span>
                </a>
            </div>

            <div class="welcome-illustration">
                <div class="trophy">🏆</div>
            </div>
        </section>

<!-- ==============================STATISTICS=============================== -->
        <section class="stats">
            <div class="stat-card">
                <div class="stat-icon purple">📝</div>
                <div class="stat-content">
                    <span>Quizzes Taken</span>
                    <h3>${totalAttempts}</h3>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon green">%</div>
                <div class="stat-content">
                    <span>Average Score</span>
                    <h3>${averageScore}%</h3>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon orange">⭐</div>
                <div class="stat-content">
                    <span>Best Score</span>
                    <h3>${bestScore}%</h3>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon blue">📚</div>
                <div class="stat-content">
                    <span>Available Quizzes</span>
                    <h3>${quizzes.size()}</h3>
                </div>
            </div>
        </section>


<!-- ==============================MAIN DASHBOARD GRID =============================== -->
        <section class="dashboard-grid">

            <!-- AVAILABLE QUIZZES -->

            <div class="card quizzes-card">
                <div class="card-header">
                    <div>
                        <h2>Available Quizzes</h2>
                        <p>Choose a quiz and start learning.</p>
                    </div>
                    <a href="${pageContext.request.contextPath}/quizzes"class="view-link">View all</a>
                </div>

                <!-- SEARCH -->

                <div class="quiz-search">
                    <input type="text" id="quizSearch" placeholder="Search quizzes..."onkeyup="searchQuizzes()">
                </div>

                <!-- QUIZ LIST -->

                <div id="quizList">
                    <c:choose>
                        <c:when test="${not empty quizzes}">
                            <c:forEach var="quiz" items="${quizzes}" begin="0" end="4">
                                <div class="quiz-row"
                                     data-name="${quiz.title}">
                                    <div class="quiz-left">
                                        <div class="quiz-icon">📝</div>
                                        <div>
                                            <h3>${quiz.title}</h3>
                                            <p>${quiz.description}</p>
                                        </div>
                                    </div>

                                    <a href="${pageContext.request.contextPath}/quiz?id=${quiz.id}"class="start-btn">
                                        Start
                                        <span>→</span>
                                    </a>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="empty">
                                <div>📚</div>
                                <p>No quizzes available right now.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- RECENT RESULTS -->

            <div class="card results-card">
                <div class="card-header">
                    <div>
                        <h2>Recent Results</h2>
                        <p>Your latest attempts.</p>
                    </div>
                    <a href="${pageContext.request.contextPath}/history" class="view-link"> View all</a>
                </div>

                <div class="results-list">
                    <c:choose>
                        <c:when test="${not empty recentAttempts}">
                            <c:forEach var="attempt" items="${recentAttempts}">
                                <div class="result-row">
                                    <div class="result-icon">✓</div>
                                    <div class="result-info">
                                        <h3>${attempt.quizTitle}</h3>
                                        <p>${attempt.attemptDate}</p>
                                    </div>
                                    <div class="result-score">${attempt.score}%</div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="empty">
                                <div>📊</div>
                                <a href="${pageContext.request.contextPath}/quizzes" class="small-btn">
                                    Take your first quiz
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </section>

<!-- ============================== QUICK ACTIONS=============================== -->

        <section class="quick-section">
            <h2>
                Quick Actions
            </h2>
            <div class="quick-grid">
                <a href="${pageContext.request.contextPath}/quizzes" class="quick-card">
                    <div class="quick-icon">📝</div>
                    <div>
                        <h3>Take Quiz</h3>
                        <p>Test your knowledge</p>
                    </div>
                    <span class="arrow">→</span>
                </a>
                <a href="${pageContext.request.contextPath}/history"class="quick-card">
                    <div class="quick-icon">📊</div>
                    <div>
                        <h3>View Results</h3>
                        <p>Check your performance</p>
                    </div>
                    <span class="arrow">→</span>
                </a>

                <a href="${pageContext.request.contextPath}/profile"class="quick-card">
                    <div class="quick-icon"> 👤</div>
                    <div>
                        <h3>My Profile</h3>
                        <p>Manage your account</p>
                    </div>
                    <span class="arrow"> →</span>
                </a>
            </div>
        </section>
    </main>
</div>
<script src="${pageContext.request.contextPath}/js/user-dashboard.js"></script>
</body>
</html>