package com.example.BBS;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AdminOrUser")
public class AdminOrUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String account = req.getSession().getAttribute("account").toString();
        int flag = 0;
        PrintWriter writer = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        if (account.contains("Admin")){
            jsonObject.put("flag",flag);
            writer.println(jsonObject);
        }
        else {
            flag = 1;
            jsonObject.put("flag",flag);
            writer.println(jsonObject);
        }
        writer.flush();
        writer.close();
    }
}
