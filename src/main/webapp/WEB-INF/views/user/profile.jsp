<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>My Profile - QuizApp</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f6fb;
            color: #1e293b;
        }

        .container {
            max-width: 900px;
            margin: 50px auto;
            padding: 20px;
        }

        .profile-card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.08);
            overflow: hidden;
        }

        /* HEADER */

        .profile-header {
            background: linear-gradient(135deg, #4f46e5, #6366f1);
            padding: 40px;
            color: white;
            text-align: center;
        }

        .avatar {
            width: 100px;
            height: 100px;
            margin: 0 auto 20px;

            border-radius: 50%;

            background: white;
            color: #4f46e5;

            display: flex;
            align-items: center;
            justify-content: center;

            font-size: 42px;
            font-weight: bold;
        }

        .profile-header h1 {
            margin: 0 0 8px;
            font-size: 30px;
        }

        .profile-header p {
            margin: 0;
            opacity: 0.9;
        }

        /* BODY */

        .profile-body {
            padding: 40px;
        }

        .section-title {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 25px;
            color: #172554;
        }

        .info-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }

        .info-box {
            background: #f8fafc;
            border: 1px solid #e2e8f0;
            border-radius: 12px;
            padding: 20px;
        }

        .info-label {
            display: block;
            color: #64748b;
            font-size: 13px;
            margin-bottom: 8px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .info-value {
            font-size: 17px;
            font-weight: 600;
            color: #1e293b;
            word-break: break-word;
        }

        .role-badge {
            display: inline-block;
            padding: 6px 14px;
            border-radius: 20px;
            background: #e0e7ff;
            color: #3730a3;
            font-size: 14px;
            font-weight: bold;
        }

        /* ACTIONS */

        .actions {
            margin-top: 35px;
            display: flex;
            gap: 12px;
        }

        .btn {
            padding: 13px 22px;
            border-radius: 9px;
            text-decoration: none;
            border: none;
            cursor: pointer;
            font-size: 15px;
            font-weight: 600;
        }

        .btn-primary {
            background: #4f46e5;
            color: white;
        }

        .btn-primary:hover {
            background: #4338ca;
        }

        .btn-secondary {
            background: #64748b;
            color: white;
        }

        .btn-secondary:hover {
            background: #475569;
        }

        /* RESPONSIVE */

        @media (max-width: 650px) {

            .container {
                margin: 20px auto;
            }

            .profile-header {
                padding: 30px 20px;
            }

            .profile-body {
                padding: 25px;
            }

            .info-grid {
                grid-template-columns: 1fr;
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

    <div class="profile-card">

        <!-- PROFILE HEADER -->

        <div class="profile-header">

            <div class="avatar">
                ${user.name.substring(0,1).toUpperCase()}
            </div>

            <h1>${user.name}</h1>

            <p>${user.email}</p>

        </div>


        <!-- PROFILE INFORMATION -->

        <div class="profile-body">

            <div class="section-title">
                Personal Information
            </div>

            <div class="info-grid">

                <!-- USER ID -->

                <div class="info-box">

                    <span class="info-label">
                        User ID
                    </span>

                    <div class="info-value">
                        ${user.userId}
                    </div>

                </div>


                <!-- NAME -->

                <div class="info-box">

                    <span class="info-label">
                        Full Name
                    </span>

                    <div class="info-value">
                        ${user.name}
                    </div>

                </div>


                <!-- EMAIL -->

                <div class="info-box">

                    <span class="info-label">
                        Email Address
                    </span>

                    <div class="info-value">
                        ${user.email}
                    </div>

                </div>


                <!-- ROLE -->

                <div class="info-box">

                    <span class="info-label">
                        Account Type
                    </span>

                    <div class="info-value">

                        <span class="role-badge">
                            ${user.role}
                        </span>

                    </div>

                </div>

            </div>


            <!-- ACTIONS -->

            <div class="actions">

                <a href="${pageContext.request.contextPath}/profile?action=edit"
                   class="btn btn-primary">

                    Edit Profile

                </a>

                <a href="${pageContext.request.contextPath}/dashboard"
                   class="btn btn-secondary">

                    Back to Dashboard

                </a>

            </div>

        </div>

    </div>

</div>

</body>

</html>