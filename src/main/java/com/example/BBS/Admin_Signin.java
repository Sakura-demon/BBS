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

@WebServlet("/Admin_Signin")
public class Admin_Signin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //接收登录界面传来的口令信息
        String Apassword = req.getParameter("Apassword");
        int flag = 0;
        PrintWriter writer = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        //判断口令是否为12位
        //不为12位时，返回-1
        if (Apassword.length() != 12){
            flag = -1;
            //将flag放入JSONObject里以JSON格式返回flag
            jsonObject.put("flag",flag);
            writer.println(jsonObject);
            writer.flush();
            writer.close();
        }
        else {
            //否则调用数据库管理员登录验证功能存储过程Admin_Signin，传入口令，获取返回值给flag
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection con= null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BBS?serverTimezone=GMT&characterEncoding=utf-8","root","12345678");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            CallableStatement sql = null;
            try {
                //调用数据库管理员登录验证功能存储过程Admin_Signin
                sql = con.prepareCall("{call Admin_Signin(?,?)}");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                //传入口令
                sql.setString(1,Apassword);
                //注册输出参数
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
            try {
                //获得输出参数返回结果
                flag = sql.getInt(2);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //如果flag = 1时将口令保存到Session对象中，以便后面进行识别是否为管理员
            if(flag == 1){
                HttpSession session = req.getSession();
                session.setAttribute("account",Apassword);
            }
            //将flag放入JSONObject里以JSON格式返回flag
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
