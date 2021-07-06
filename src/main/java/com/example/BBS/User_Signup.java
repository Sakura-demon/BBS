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
import java.util.Random;

@WebServlet("/User_Signup")
public class User_Signup extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String Upname = req.getParameter("Upname");
        String Uppassword = req.getParameter("Uppassword");
        int flag = 0;
        PrintWriter writer = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        if(Upname.length() != 12){
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
                sql = con.prepareCall("{call User_Signup(?,?,?,?)}");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                sql.setString(1,Upname);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                sql.setString(2,Uppassword);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String accountImg1 = "Img/account.png";
            String accountImg2 = "Img/樱花招福御守.png";
            String accountImg3 = "Img/狗.png";
            Random random = new Random();
            int s = random.nextInt(4);
            if (s == 1){
                try {
                    sql.setString(3,accountImg1);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else if (s == 2){
                try {
                    sql.setString(3,accountImg2);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else if (s == 3){
                try {
                    sql.setString(3,accountImg3);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            try {
                sql.registerOutParameter(4, Types.INTEGER);
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
                flag = sql.getInt(4);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if(flag == 1){
                try {
                    rs.next();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                int Upaccount = 0;
                try {
                    Upaccount = rs.getInt("Uaccount");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                HttpSession session = req.getSession();
                session.setAttribute("account",Upaccount);
                session.setAttribute("Upassword",Uppassword);
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
