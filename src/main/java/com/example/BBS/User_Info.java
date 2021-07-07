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
import java.sql.*;

@WebServlet("/User_Info")
public class User_Info extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收用户的Session对象账号信息
        HttpSession session = req.getSession();
        int Uaccount = (int) session.getAttribute("account");
        //调用个人页面功能存储过程User_Info，传入账号
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
            //调用个人页面功能存储过程User_Info
            sql = con.prepareCall("{call User_Info(?)}");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //传入账号
            sql.setInt(1,Uaccount);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = sql.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        //将结果集中的用户名称，用户图像路径放入JSONObject里以JSON格式返回
        try {
            jsonObject.put("Uname",rs.getString("Uname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            jsonObject.put("Uimgurl",rs.getString("Uimgurl"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        jsonObject.put("Uaccount",Uaccount);
        PrintWriter writer = resp.getWriter();
        writer.println(jsonObject);
        writer.flush();
        writer.close();
    }
}
