<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Update Profile</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f6fb;
        }

        .container {
            max-width: 650px;
            margin: 60px auto;
            padding: 20px;
        }

        .card {
            background: white;
            padding: 35px;
            border-radius: 15px;
            box-shadow: 0 5px 25px rgba(0,0,0,0.08);
        }

        h1 {
            margin-top: 0;
            color: #172554;
        }

        .subtitle {
            color: #64748b;
            margin-bottom: 30px;
        }

        .field {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #334155;
        }

        input {
            width: 100%;
            padding: 13px;
            border: 1px solid #cbd5e1;
            border-radius: 8px;
            font-size: 16px;
        }

        input:focus {
            outline: none;
            border-color: #4f46e5;
        }

        .error {
            background: #fee2e2;
            color: #991b1b;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .success {
            background: #dcfce7;
            color: #166534;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .actions {
            display: flex;
            gap: 12px;
            margin-top: 30px;
        }

        .btn {
            padding: 12px 22px;
            border-radius: 8px;
            text-decoration: none;
            border: none;
            cursor: pointer;
            font-size: 15px;
        }

        .btn-primary {
            background: #4f46e5;
            color: white;
        }

        .btn-secondary {
            background: #64748b;
            color: white;
        }
    </style>
</head>

<body>

<div class="container">
    <div class="card">
        <h1>Update Profile</h1>
        <p class="subtitle">
            Update your personal information.
        </p>
        <!-- ERROR -->
        <c:if test="${not empty error}">
            <div class="error">
                ${error}
            </div>
        </c:if>
        <!-- SUCCESS -->
        <c:if test="${not empty success}">
            <div class="success">
                ${success}
            </div>
        </c:if>
        <form method="post"
              action="${pageContext.request.contextPath}/profile">
            <input type="hidden" name="action" value="update">

            <!-- NAME -->
            <div class="field">
                <label for="name">
                    Name
                </label>
                <input
                        type="text"
                        id="name"
                        name="name"
                        value="${user.name}"
                        required
                        maxlength="256">
            </div>

            <!-- EMAIL -->
            <div class="field">
                <label for="email">
                    Email
                </label>
                <input
                        type="email"
                        id="email"
                        name="email"
                        value="${user.email}"
                        required
                        maxlength="150">
            </div>

                <div class="field">
                        <label for="password">
                            New Password
                        </label>

                        <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="Leave blank to keep current password"
                            maxlength="255">
                </div>

                <div class="field">
                            <label>Role</label>
                            <input
                                type="text"
                                value="${user.role}"
                                readonly>
                </div>

            <div class="actions">
                <a href="${pageContext.request.contextPath}/profile"
                   class="btn btn-secondary">
                    Cancel
                </a>

                <button type="submit"
                        class="btn btn-primary">
                    Update Profile
                </button>

            </div>

        </form>

    </div>

</div>

</body>

</html>