package com.example.BBS;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/User_messageOrback")
public class User_messageOrback extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int status = Integer.parseInt(req.getParameter("status"));
        HttpSession session = req.getSession();
        if (status == 0){
            int flag = Integer.parseInt(req.getParameter("flag"));
            session.setAttribute("flag",flag);
        }else {
            JSONObject jsonObject = new JSONObject();
            int flag = (int) session.getAttribute("flag");
            jsonObject.put("flag",flag);
            PrintWriter writer = resp.getWriter();
            writer.println(jsonObject);
            writer.flush();
            writer.close();
        }
    }
}
