package com.quizapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String role = (String) session.getAttribute("role");

        if("ADMIN".equalsIgnoreCase(role)){request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request,response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/user/dashboard.jsp").forward(request,response);
        }
    }
}