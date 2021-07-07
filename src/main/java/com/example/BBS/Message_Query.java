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

@WebServlet("/Message_Query")
public class Message_Query extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收Session对象中的留言号
        HttpSession session = req.getSession();
        int Mid = (int) session.getAttribute("Mid");
        int flag = Integer.parseInt(req.getParameter("flag"));
        //调用留言页面查询功能存储过程Message_Query，传入留言号
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
        if (flag == 0){
            try {
                //调用留言页面查询功能存储过程Message_Query
                sql = con.prepareCall("{call Message_Query(?)}");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                //传入留言号
                sql.setInt(1,Mid);
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
            //将结果集中的留言标题，留言时间，用户图像路径，用户名称，留言信息放入JSONObject里以JSON格式返回
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Mtitle",rs.getString("Mtitle"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                jsonObject.put("Mtime",rs.getString("Mtime"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                jsonObject.put("Uimgurl",rs.getString("Uimgurl"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                jsonObject.put("Uname",rs.getString("Uname"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                jsonObject.put("Mmessage",rs.getString("Mmessage"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            resp.setContentType("text/html;charset=utf-8");
            PrintWriter writer = resp.getWriter();
            writer.println(jsonObject);
            writer.flush();
            writer.close();
        }else{
            //调用留言页面回复查询功能存储过程Back_Query，传入留言号
            try {
                //调用留言页面回复查询功能存储过程Back_Query
                sql = con.prepareCall("{call Back_Query(?)}");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                //传入留言号
                sql.setInt(1,Mid);
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
}
