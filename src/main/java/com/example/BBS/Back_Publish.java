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

@WebServlet("/Back_Publish")
public class Back_Publish extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收前端传来的回复信息和Session对象留言号和账号信息
        String Bmessage = req.getParameter("Bmessage");
        HttpSession session = req.getSession();
        int Mid = (int) session.getAttribute("Mid");
        int Uaccount = (int) session.getAttribute("account");
        //调用删除留言功能存储过程Back_Publish，传入留言号、账号和回复信息
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
            //调用删除留言功能存储过程Back_Publish
            sql = con.prepareCall("{call Back_Publish(?,?,?,?)}");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //传入留言号
            sql.setInt(1,Mid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //传入账号
            sql.setInt(2,Uaccount);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //传入回复信息
            sql.setString(3,Bmessage);
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
