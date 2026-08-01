(function () {
  "use strict";
  wireSocialButtons("register-google", "register-github");

  document.getElementById("register-form").addEventListener("submit", function (e) {
    e.preventDefault();

    var name = document.getElementById("register-name");
    var email = document.getElementById("register-email");
    var password = document.getElementById("register-password");
    var valid = true;

    setFieldError("register-name", "");
    setFieldError("register-email", "");
    setFieldError("register-password", "");
    clearStatus("register-status");

    if (!name.value.trim()) {
      setFieldError("register-name", "Enter your name.");
      valid = false;
    }
    if (!isValidEmail(email)) {
      setFieldError("register-email", "Enter a valid email address.");
      valid = false;
    }
    if (!password.value || password.value.length < 8) {
      setFieldError("register-password", "Password must be at least 8 characters.");
      valid = false;
    }
    if (!valid) return;

    var btn = document.getElementById("register-submit");
    btn.disabled = true;
    btn.textContent = "Creating account\u2026";

    // ---- Real backend call ----
    // Your API creates the user and, typically, either logs them straight
    // in (setting a session cookie) or expects them to verify their email
    // before logging in. Adjust the redirect below to match your flow.
    fetch("/api/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({
        name: name.value.trim(),
        email: email.value,
        password: password.value
      })
    })
      .then(function (res) {
        if (!res.ok) {
          return res.json().catch(function () { return {}; }).then(function (data) {
            throw new Error(data.message || "Could not create your account.");
          });
        }
        return res.json().catch(function () { return {}; });
      })
      .then(function () {
        showStatus("register-status", "Account created. Redirecting to login\u2026", "success");
        setTimeout(function () {
          window.location.href = "login.html";
        }, 900);
      })
      .catch(function (err) {
        showStatus("register-status", err.message || "Something went wrong. Please try again.", "error");
      })
      .finally(function () {
        btn.disabled = false;
        btn.textContent = "Register";
      });
  });
})();
"use strict";

/* ---------- Small shared helpers used on both login.html and register.html ---------- */

function setFieldError(inputId, message) {
  var errorEl = document.getElementById(inputId + "-error");
  if (errorEl) errorEl.textContent = message || "";
}

function showStatus(id, message, type) {
  var el = document.getElementById(id);
  if (!el) return;
  el.textContent = message;
  el.className = "status-msg show " + type;
}

function clearStatus(id) {
  var el = document.getElementById(id);
  if (!el) return;
  el.textContent = "";
  el.className = "status-msg";
}

/**
 * Real project note:
 * These two functions send the browser straight to your identity provider's
 * consent screen. Swap in your own OAuth client IDs and redirect URIs
 * (set these up in Google Cloud Console / GitHub Developer Settings).
 * Your backend then handles the callback, creates/looks up the user,
 * and sets a secure, httpOnly session cookie — the frontend never
 * touches the access token directly.
 */
function startGoogleOAuth() {
  var clientId = "YOUR_GOOGLE_CLIENT_ID";
  var redirectUri = encodeURIComponent(window.location.origin + "/auth/google/callback");
  var url =
    "https://accounts.google.com/o/oauth2/v2/auth" +
    "?client_id=" + clientId +
    "&redirect_uri=" + redirectUri +
    "&response_type=code" +
    "&scope=" + encodeURIComponent("openid email profile");
  window.location.href = url;
}

function startGitHubOAuth() {
  var clientId = "YOUR_GITHUB_CLIENT_ID";
  var redirectUri = encodeURIComponent(window.location.origin + "/auth/github/callback");
  var url =
    "https://github.com/login/oauth/authorize" +
    "?client_id=" + clientId +
    "&redirect_uri=" + redirectUri +
    "&scope=" + encodeURIComponent("read:user user:email");
  window.location.href = url;
}

function wireSocialButtons(googleBtnId, githubBtnId) {
  var googleBtn = document.getElementById(googleBtnId);
  var githubBtn = document.getElementById(githubBtnId);
  if (googleBtn) googleBtn.addEventListener("click", startGoogleOAuth);
  if (githubBtn) githubBtn.addEventListener("click", startGitHubOAuth);
}

function isValidEmail(input) {
  return !!input.value && input.validity.valid;
}
