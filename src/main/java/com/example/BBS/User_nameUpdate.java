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
        //接收用户传来的用户名称信息和Session对象账号信息
        String Uname = req.getParameter("Uname");
        int flag = 0;
        //判断用户名是否为12位
        //用户名不为12位时
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
            //否则调用用户名称修改功能存储过程User_nameUpdate，传入用户名称和账号信息
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
                //调用用户名称修改功能存储过程User_nameUpdate
                sql = con.prepareCall("{call User_nameUpdate(?,?,?)}");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                //传入用户名称
                sql.setInt(1,Uaccount);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                //传入账号
                sql.setString(2,Uname);
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
            flag = 0;
            try {
                //获取输出参数返回结果
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
}
