package com.example.BBS;

import com.alibaba.fastjson.JSONObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/Main_Delete")
public class Main_Delete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //接收传来的留言号
        int Mid = Integer.parseInt(req.getParameter("Mid"));
        //调用删除留言功能存储过程Main_Delete，传入留言号
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
            //调用删除留言功能存储过程Main_Delete
            sql = con.prepareCall("{call Main_Delete(?,?)}");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            //传入留言号
            sql.setInt(1,Mid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
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
        int flag = 0;
        try {
            //获取输出参数返回结果
            flag = sql.getInt("flag");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //将flag放入JSONObject里以JSON格式返回flag
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",flag);
        PrintWriter writer = resp.getWriter();
        writer.println(jsonObject);
        writer.flush();
        writer.close();
    }
}
