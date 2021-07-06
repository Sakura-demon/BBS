package com.example.BBS;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/User_pastBackDelete")
public class User_pastBackDelete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int Bid = Integer.parseInt(req.getParameter("Bid"));
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
            sql = con.prepareCall("{call User_pastBackDelete(?,?)}");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            sql.setInt(1,Bid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            sql.registerOutParameter(2,Types.INTEGER);
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
            flag = sql.getInt("flag");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",flag);
        PrintWriter writer = resp.getWriter();
        writer.println(jsonObject);
        writer.flush();
        writer.close();
    }
}