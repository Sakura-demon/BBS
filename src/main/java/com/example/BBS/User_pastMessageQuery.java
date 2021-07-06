package com.example.BBS;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/User_pastMessageQuery")
public class User_pastMessageQuery extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
            sql = con.prepareCall("{call User_pastMessageQuery(?)}");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
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
        if (Page == -6){
            int curPage = (int) session.getAttribute("curPage");
            JSONObject jsonObjectcurPage = new JSONObject();
            jsonObjectcurPage.put("curPage",curPage);
            jsonArray.add(jsonObjectcurPage);
        }
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
        else if (Page == -4){
            int curPage = (int) session.getAttribute("curPage");
            curPage --;
            session.setAttribute("curPage",curPage);
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
                        jsonObject.put("Uname",rs.getString("Uname"));
                        jsonObject.put("Uimgurl",rs.getString("Uimgurl"));
                        jsonObject.put("Mtitle",rs.getString("Mtitle"));
                        jsonObject.put("Mtime",rs.getDate("Mtime"));
                        jsonArray.add(jsonObject);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        else if (Page == -3){
            int curPage = (int) session.getAttribute("curPage");
            curPage++;
            session.setAttribute("curPage",curPage);
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
                        jsonObject.put("Uname",rs.getString("Uname"));
                        jsonObject.put("Uimgurl",rs.getString("Uimgurl"));
                        jsonObject.put("Mtitle",rs.getString("Mtitle"));
                        jsonObject.put("Mtime",rs.getDate("Mtime"));
                        jsonArray.add(jsonObject);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
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
                        jsonObject.put("Uname",rs.getString("Uname"));
                        jsonObject.put("Uimgurl",rs.getString("Uimgurl"));
                        jsonObject.put("Mtitle",rs.getString("Mtitle"));
                        jsonObject.put("Mtime",rs.getDate("Mtime"));
                        jsonArray.add(jsonObject);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
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
