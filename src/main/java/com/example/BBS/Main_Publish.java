package com.example.BBS;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.*;

@WebServlet("/Main_Publish")
public class Main_Publish extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收主页传来的留言标题和留言内容和Session对象里的账号
        String Mtitle = req.getParameter("Mtitle");
        String Mmessage = req.getParameter("Mmessage");
        HttpSession session = req.getSession();
        int Uaccount = (int) session.getAttribute("account");
        //调用主页留言发表功能存储过程Main_Publish，传入留言标题和留言内容和账号
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BBS?serverTimezone=GMT&characterEncoding=utf-8","root","12345678");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        CallableStatement sql = null;
        try {
            //调用主页留言发表功能存储过程Main_Publish
            sql = con.prepareCall("{call Main_Publish(?,?,?,?)}");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //传入留言标题
            sql.setString(1,Mtitle);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //传入留言内容
            sql.setString(2,Mmessage);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //传入账号
            sql.setInt(3,Uaccount);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //注册输出参数
            sql.registerOutParameter(4,Types.INTEGER);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = sql.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int flag = 0;
        try {
            //获取输出参数返回结果
            flag = sql.getInt("flag");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //将flag放入JSONObject里以JSON格式返回flag
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",flag);
        PrintWriter writer = resp.getWriter();
        writer.println(jsonObject);
        writer.flush();
        writer.close();
    }
}
