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

@WebServlet("/User_nameUpdate")
public class User_nameUpdate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String Uname = req.getParameter("Uname");
        int flag = 0;
        if (Uname.length() != 12){
            flag = 0;
            resp.setContentType("text/html;charset=utf-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag",flag);
            PrintWriter writer = resp.getWriter();
            writer.println(jsonObject);
            writer.flush();
            writer.close();
        }else {
            HttpSession session = req.getSession();
            int Uaccount = (int) session.getAttribute("account");
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
                sql = con.prepareCall("{call User_nameUpdate(?,?,?)}");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                sql.setInt(1,Uaccount);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                sql.setString(2,Uname);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
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
            flag = 0;
            try {
                flag = sql.getInt("flag");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            resp.setContentType("text/html;charset=utf-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag",flag);
            PrintWriter writer = resp.getWriter();
            writer.println(jsonObject);
            writer.flush();
            writer.close();
        }
    }
}
