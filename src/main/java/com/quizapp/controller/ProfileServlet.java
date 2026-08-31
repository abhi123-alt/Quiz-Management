package com.quizapp.controller;

import com.quizapp.model.User;
import com.quizapp.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {

        try {

            userService = new UserService();

        } catch (Exception e) {

            throw new ServletException(
                    "Failed to initialize UserService",
                    e
            );
        }
    }


    // =========================
    // GET
    // =========================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check login
        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );

            return;
        }

        int user_id = (Integer) session.getAttribute("userId");

        String action = request.getParameter("action");


        // EDIT PROFILE

        if ("edit".equals(action)) {

            showEditProfile(
                    request,
                    response,
                    user_id
            );

            return;
        }


        // DEFAULT → PROFILE

        showProfile(
                request,
                response,
                user_id
        );
    }


    // =========================
    // SHOW PROFILE
    // =========================

    private void showProfile(
            HttpServletRequest request,
            HttpServletResponse response,
            int user_id)
            throws ServletException, IOException {

        User user = userService.getUserById(user_id);

        if (user == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "User not found"
            );

            return;
        }

        request.setAttribute("user", user);

        request.getRequestDispatcher(
                "/WEB-INF/views/user/profile.jsp"
        ).forward(request, response);
    }


    // =========================
    // SHOW EDIT PROFILE
    // =========================

    private void showEditProfile(
            HttpServletRequest request,
            HttpServletResponse response,
            int user_id)
            throws ServletException, IOException {

        User user = userService.getUserById(user_id);

        if (user == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "User not found"
            );

            return;
        }

        request.setAttribute("user", user);

        request.getRequestDispatcher(
                "/WEB-INF/views/user/edit-profile.jsp"
        ).forward(request, response);
    }


    // =========================
    // POST
    // =========================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );

            return;
        }

        int user_id =
                (Integer) session.getAttribute("userId");

        String action = request.getParameter("action");


        if ("update".equals(action)) {

            updateProfile(
                    request,
                    response,
                    user_id
            );

            return;
        }


        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid profile action"
        );
    }


    // =========================
    // UPDATE PROFILE
    // =========================

    private void updateProfile(
            HttpServletRequest request,
            HttpServletResponse response,
            int user_id)
            throws ServletException, IOException {

        String name =
                request.getParameter("name");

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");

        // Validate name

        if (name == null ||
                name.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Name is required."
            );

            showEditProfile(
                    request,
                    response,
                    user_id
            );

            return;
        }


        // Validate email

        if (email == null ||
                email.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Email is required."
            );

            showEditProfile(
                    request,
                    response,
                    user_id
            );

            return;
        }


        // Get existing user

        User existingUser =
                userService.getUserById(user_id);

        if (existingUser == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "User not found"
            );

            return;
        }


        /*
         * If password is empty,
         * keep the old password.
         */

        if (password == null ||
                password.trim().isEmpty()) {

            password =
                    existingUser.getPassword();
        }


        /*
         * Do NOT allow normal users
         * to change their role.
         *
         * Keep the role from database.
         */

        boolean updated =
                userService.updateProfile(
                        user_id,
                        name.trim(),
                        email.trim(),
                        password
                );


        if (updated) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/profile"
            );

            return;
        }

        request.setAttribute(
                "error",
                "Unable to update profile."
        );

        showEditProfile(
                request,
                response,
                user_id
        );
    }
}