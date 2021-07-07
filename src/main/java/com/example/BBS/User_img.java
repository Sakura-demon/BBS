package com.example.BBS;

import com.alibaba.fastjson.JSONObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/User_img")
public class User_img extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //接收Session对象账号
        int Uaccount = (int) req.getSession().getAttribute("account");
        //调用嵌套页面个人图像功能存储过程User_img，传入账号
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
            //调用嵌套页面个人图像功能存储过程User_img
            sql = con.prepareCall("{call User_img(?)}");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //传入账号
            sql.setInt(1,Uaccount);
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
            rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String Uimgurl = null;
        try {
            //获得结果集中的用户图片路径
            Uimgurl = rs.getString("Uimgurl");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //将Uimgurl放入JSONObject里以JSON格式返回Uimgurl用户图像路径
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Uimgurl",Uimgurl);
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
