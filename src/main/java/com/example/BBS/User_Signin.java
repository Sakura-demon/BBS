package com.example.BBS;

import com.alibaba.fastjson.JSONObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/User_Signin")
public class User_Signin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String Uname = req.getParameter("Uname");
        String Upassword = req.getParameter("Upassword");
        int flag = 0;
        PrintWriter writer = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        if(Uname.length() != 12){
            flag = -1;
            jsonObject.put("flag",flag);
            writer.println(jsonObject);
            writer.flush();
            writer.close();
        }
        else {
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
                sql = con.prepareCall("{call User_Signin(?,?,?)}");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                sql.setString(1,Uname);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                sql.setString(2,Upassword);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                sql.registerOutParameter("3", Types.INTEGER);
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
                flag = sql.getInt(3);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (flag == 1){
                try {
                    rs.next();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                int Uaccount = 0;
                try {
                    Uaccount = rs.getInt("Uaccount");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                HttpSession session = req.getSession();
                session.setAttribute("account",Uaccount);
                session.setAttribute("Upassword",Upassword);
            }
            jsonObject.put("flag",flag);
            writer.println(jsonObject);
            writer.flush();
            writer.close();
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                sql.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
