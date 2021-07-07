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
        //接收前端发来的status数据
        int status = Integer.parseInt(req.getParameter("status"));
        HttpSession session = req.getSession();
        //如果status = 0时表示用户在个人页面进行选择点击，将当前flag的的数据保存到Session对象中
        if (status == 0){
            int flag = Integer.parseInt(req.getParameter("flag"));
            session.setAttribute("flag",flag);
        }
        //否则表示用户进入用户过去留言页面，返回Session保存的flag数据
        else {
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
