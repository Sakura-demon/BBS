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
        //接收注册界面传来的用户名密码信息
        String Upname = req.getParameter("Upname");
        String Uppassword = req.getParameter("Uppassword");
        int flag = 0;
        PrintWriter writer = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        //判断用户名是否为12位
        //不为12位时，返回-1
        if(Upname.length() != 12){
            flag = -1;
            //将flag放入JSONObject里以JSON格式返回flag
            jsonObject.put("flag",flag);
            writer.println(jsonObject);
            writer.flush();
            writer.close();
        }
        else {
            //否则调用数据库用户登录验证功能存储过程User_Signup，传入用户名密码和用户图像路径，获取返回值给flag
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
                //调用数据库用户登录验证功能存储过程User_Signup
                sql = con.prepareCall("{call User_Signup(?,?,?,?)}");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                //传入用户名
                sql.setString(1,Upname);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                //传入密码
                sql.setString(2,Uppassword);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //随机设置用户图片
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
                    //传入用户图像路径
                    sql.setString(3,accountImg3);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            try {
                //注册输出参数
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
                //获得输出参数返回结果
                flag = sql.getInt(4);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //如果flag = 1时将返回的账号和从前端接收到密码保存到Session对象中，以便后面进行识别是否为用户
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
