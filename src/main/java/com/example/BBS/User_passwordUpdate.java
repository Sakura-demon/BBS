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

@WebServlet("/User_passwordUpdate")
public class User_passwordUpdate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收用户传来的用户密码和Session对象的账号信息
        String Upassword = req.getParameter("Upassword");
        HttpSession session = req.getSession();
        int Uaccount = (int) session.getAttribute("account");
        //调用调用用户密码修改功能存储过程User_passwordUpdate，传入用户密码和账号信息
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
            //调用调用用户密码修改功能存储过程User_passwordUpdate
            sql = con.prepareCall("{call User_passwordUpdate(?,?,?)}");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //传入用户密码
            sql.setInt(1,Uaccount);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //传入账号
            sql.setString(2,Upassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //注册输出参数
            sql.registerOutParameter(3, Types.INTEGER);
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
            //获得输出参数返回结果
            flag = sql.getInt("flag");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //将flag放入JSONObject里以JSON格式返回flag
        resp.setContentType("text/html;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",flag);
        PrintWriter writer = resp.getWriter();
        writer.println(jsonObject);
        writer.flush();
        writer.close();
    }
}
