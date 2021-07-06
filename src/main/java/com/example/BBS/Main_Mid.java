package com.example.BBS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/Main_Mid")
public class Main_Mid extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int Mid = Integer.parseInt(req.getParameter("Mid"));
        HttpSession session = req.getSession();
        session.setAttribute("Mid",Mid);
    }
}
