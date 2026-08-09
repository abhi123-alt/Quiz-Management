<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StudyTrack — Track Your Study. Achieve Your Goals.</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> <style>
        :root{
          --indigo-700:#4338ca;
          --indigo-600:#4f46e5;
          --indigo-500:#6366f1;
          --violet-500:#7c6cf6;
          --ink-900:#1a1a2e;
          --ink-600:#5b5b6e;
          --ink-400:#8b8ba0;
          --bg-lav:#f3f1ff;
          --bg-lav-2:#eceafd;
          --white:#ffffff;
          --green-500:#22c55e;
          --border:#e6e3fb;
          --focus-ring:#f59e0b;
        }
    </style>
</head>
<body>
<a class="skip-link" href="#main-content">Skip to main content</a>
<header>
    <a class="logo" href="#top" aria-label="StudyTrack home">
        <span class="logo-badge" aria-hidden="true">🎓</span>
        StudyTrack
    </a>

    <nav class="primary-nav" aria-label="Primary">
        <ul>
            <li><a href="#features" id="feature">Features</a></li>
            <li><a href="#how-it-works" id="working">How It Works</a></li>
            <li><a href="#pricing" id="pricing">Pricing</a></li>
            <li><a href="#about" id="about">About</a></li>
        </ul>
    </nav>

    <div class="header-actions">
        <a class="link-btn" href="${pageContext.request.contextPath}/login">Login</a>
        <a href="${pageContext.request.contextPath}/register" class="btn btn-primary btn-lg">Get Started Free</a>
    </div>
</header>

<main id="main-content">
    <section class="hero" id="top" aria-labelledby="hero-heading">
        <div class="hero-copy">
            <h1 id="hero-heading">
                Track Your Study.<br>
                <span class="accent">Achieve</span> Your Goals.
            </h1>
            <p>Plan your tasks, track study time, build good habits, and achieve your dreams with StudyTrack.</p>
            <div class="hero-ctas">
               <a href="${pageContext.request.contextPath}/register" class="btn btn-primary btn-lg">Get Started Free</a>
                <button type="button" class="watch-demo" aria-label="Watch a demo video of StudyTrack">
                    <span class="play-circle" aria-hidden="true">▶</span>
                    Watch Demo
                </button>
            </div>
            <div class="trusted">
                <div class="avatars" aria-hidden="true">
                    <span style="background:#f59e0b;">A</span>
                    <span style="background:#4f46e5;">B</span>
                    <span style="background:#22c55e;">C</span>
                    <span style="background:#ec4899;">D</span>
                </div>
                <p>Trusted by <strong>10,000+</strong> students</p>
            </div>
        </div>

        <div class="hero-art" aria-hidden="true">
            <div class="art-blob"></div>
            <figure>
                <svg class="person" viewBox="0 0 200 260" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Illustration of a student sitting cross-legged using a laptop">
                    <ellipse cx="100" cy="245" rx="70" ry="10" fill="#e6e3fb"/>
                    <rect x="55" y="60" width="90" height="110" rx="30" fill="#4f46e5"/>
                    <circle cx="100" cy="45" r="30" fill="#2b2b40"/>
                    <circle cx="100" cy="50" r="24" fill="#f2c9a0"/>
                    <rect x="70" y="150" width="60" height="55" rx="18" fill="#2b2b40"/>
                    <rect x="72" y="175" width="56" height="34" rx="8" fill="#dcdcec" transform="rotate(-3 100 190)"/>
                    <rect x="80" y="182" width="40" height="22" rx="3" fill="#8b8ba0"/>
                </svg>
            </figure>
            <div class="floating-card fc-1">
                <span class="check-dot">✓</span>
                Task Completed
            </div>
            <div class="floating-card fc-2">
                📈 Progress +24%
            </div>
        </div>
    </section>

    <section onclick class="feature-strip" id="features" aria-label="Key features">
        <article class="feature-card">
            <div class="feature-icon" aria-hidden="true">✅</div>
            <h3>Task Management</h3>
            <p>Organize and prioritize your study tasks with ease.</p>
        </article>
        <article class="feature-card">
            <div class="feature-icon" aria-hidden="true">⏱️</div>
            <h3>Focus Timer</h3>
            <p>Stay focused with Pomodoro-style study sessions.</p>
        </article>
        <article class="feature-card">
            <div class="feature-icon" aria-hidden="true">📊</div>
            <h3>Progress Analytics</h3>
            <p>Track your growth with visual insights and charts.</p>
        </article>
        <article class="feature-card">
            <div class="feature-icon" aria-hidden="true">🎯</div>
            <h3>Habits &amp; Breaks</h3>
            <p>Build good habits and take mindful breaks.</p>
        </article>
    </section>

</main>

<footer class="site-footer">
    <a href="#">© 2026 StudyTrack. All rights reserved.</a>
</footer>

<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>