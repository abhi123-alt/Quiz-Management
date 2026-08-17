
// SIDEBAR
function toggleSidebar() {
    const sidebar = document.getElementById("sidebar");
    sidebar.classList.toggle("show");
}

// SEARCH QUIZZES
function searchQuizzes() {
    const input =document.getElementById("quizSearch");
    const searchText =input.value.toLowerCase();
    const quizzes =document.querySelectorAll(".quiz-row");
    quizzes.forEach(function (quiz) {
        const name = quiz.getAttribute("data-name").toLowerCase();
        if (name.includes(searchText)) {
            quiz.style.display = "flex";
        } else {
            quiz.style.display = "none";
        }
    });
}
// CLOSE SIDEBAR WHEN CLICKING OUTSIDE
document.addEventListener("click", function (event) {
    const sidebar = document.getElementById("sidebar");
    const menuButton =document.querySelector(".menu-btn");
    if (
        window.innerWidth <= 700 &&
        sidebar.classList.contains("show") &&
        !sidebar.contains(event.target) &&
        !menuButton.contains(event.target)
    ) {
        sidebar.classList.remove("show");
    }
});