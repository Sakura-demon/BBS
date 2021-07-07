package com.example.BBS;

import com.alibaba.fastjson.JSONArray;
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

@WebServlet("/User_pastBackQuery")
public class User_pastBackQuery extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收用户的Session对象账号信息
        HttpSession session = req.getSession();
        int Uaccount = (int) session.getAttribute("account");
        //调用个人页面过去回复查询功能存储过程User_pastBackQuery，传入账号
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
            //调用个人页面过去回复查询功能存储过程User_pastBackQuery
            sql = con.prepareCall("{call User_pastBackQuery(?)}");
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
        int Page = Integer.parseInt(req.getParameter("Page"));
        JSONArray jsonArray = new JSONArray();
        //返回当前页数
        if (Page == -6){
            int curPage = (int) session.getAttribute("curPage");
            JSONObject jsonObjectcurPage = new JSONObject();
            jsonObjectcurPage.put("curPage",curPage);
            jsonArray.add(jsonObjectcurPage);
        }
        //返回数据总条数
        else if (Page == -5){
            int length = 0;
            while (true){
                try {
                    if (!rs.next()) break;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                length++;
            }
            JSONObject jsonObjectlength = new JSONObject();
            jsonObjectlength.put("length",length);
            jsonArray.add(jsonObjectlength);
        }
        //上一页
        else if (Page == -4){
            int curPage = (int) session.getAttribute("curPage");
            curPage --;
            try {
                for (int i = 0; i < (curPage-1)*10; i++) {
                    rs.next();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                try {
                    if (rs.next()){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Mid",rs.getInt("Mid"));
                        jsonObject.put("Mtitle",rs.getString("Mtitle"));
                        jsonObject.put("Mtime",rs.getString("Mtime"));
                        jsonObject.put("Bid",rs.getInt("Bid"));
                        jsonObject.put("Uname",rs.getString("Uname"));
                        jsonObject.put("Uimgurl",rs.getString("Uimgurl"));
                        jsonObject.put("Btime",rs.getString("Btime"));
                        jsonObject.put("Bmessage",rs.getString("Bmessage"));
                        jsonArray.add(jsonObject);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //下一页
        else if (Page == -3){
            int curPage = (int) session.getAttribute("curPage");
            curPage++;
            try {
                for (int i = 0; i < (curPage-1)*10; i++) {
                    rs.next();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                try {
                    if (rs.next()){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Mid",rs.getInt("Mid"));
                        jsonObject.put("Mtitle",rs.getString("Mtitle"));
                        jsonObject.put("Mtime",rs.getString("Mtime"));
                        jsonObject.put("Bid",rs.getInt("Bid"));
                        jsonObject.put("Uname",rs.getString("Uname"));
                        jsonObject.put("Uimgurl",rs.getString("Uimgurl"));
                        jsonObject.put("Btime",rs.getString("Btime"));
                        jsonObject.put("Bmessage",rs.getString("Bmessage"));
                        jsonArray.add(jsonObject);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //首页/尾页/页码
        else {
            Page += 3;
            session.setAttribute("curPage",Page);
            try {
                for (int i = 0; i < (Page-1)*10; i++) {
                    rs.next();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                try {
                    if (rs.next()){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Mid",rs.getInt("Mid"));
                        jsonObject.put("Mtitle",rs.getString("Mtitle"));
                        jsonObject.put("Mtime",rs.getString("Mtime"));
                        jsonObject.put("Bid",rs.getInt("Bid"));
                        jsonObject.put("Uname",rs.getString("Uname"));
                        jsonObject.put("Uimgurl",rs.getString("Uimgurl"));
                        jsonObject.put("Btime",rs.getString("Btime"));
                        jsonObject.put("Bmessage",rs.getString("Bmessage"));
                        jsonArray.add(jsonObject);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //以JSON格式返回上述数据
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.println(jsonArray);
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
