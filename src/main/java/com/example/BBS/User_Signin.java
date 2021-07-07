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
        //接收登录界面传来的用户名密码信息
        String Uname = req.getParameter("Uname");
        String Upassword = req.getParameter("Upassword");
        int flag = 0;
        PrintWriter writer = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        //判断用户名是否为12位
        //不为12位时，返回-1
        if(Uname.length() != 12){
            flag = -1;
            //将flag放入JSONObject里以JSON格式返回flag
            jsonObject.put("flag",flag);
            writer.println(jsonObject);
            writer.flush();
            writer.close();
        }
        else {
            //否则调用数据库用户登录验证功能存储过程User_Signin，传入用户名密码，获取返回值给flag
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
                //调用数据库用户登录验证功能存储过程User_Signin
                sql = con.prepareCall("{call User_Signin(?,?,?)}");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                //传入用户名
                sql.setString(1,Uname);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                //传入密码
                sql.setString(2,Upassword);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                //注册输出参数
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
                //获取输出参数返回结果
                flag = sql.getInt(3);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //如果flag = 1时将返回的账号和从前端接收到密码保存到Session对象中，以便后面进行识别是否为用户
            if (flag == 1){
                try {
                    rs.next();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                int Uaccount = 0;
                try {
                    //从结果集获取用户账号
                    Uaccount = rs.getInt("Uaccount");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                HttpSession session = req.getSession();
                session.setAttribute("account",Uaccount);
                session.setAttribute("Upassword",Upassword);
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
