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
        //从Session对象中获取当前账户
        String account = req.getSession().getAttribute("account").toString();
        int flag = 0;
        PrintWriter writer = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        //如果该字符串中包含Admin则为管理员，返回0
        if (account.contains("Admin")){
            jsonObject.put("flag",flag);
            writer.println(jsonObject);
        }
        //否则为用户.返回1
        else {
            flag = 1;
            jsonObject.put("flag",flag);
            writer.println(jsonObject);
        }
        writer.flush();
        writer.close();
    }
}
