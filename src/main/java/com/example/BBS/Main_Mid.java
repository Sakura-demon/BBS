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
        //接收前端发来的留言号数据
        int Mid = Integer.parseInt(req.getParameter("Mid"));
        //保存到Session对象中
        HttpSession session = req.getSession();
        session.setAttribute("Mid",Mid);
    }
}
