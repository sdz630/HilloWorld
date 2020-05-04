package com.sdz.hilloworld.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    private String url;//设置数据库的地址 设置编码  支持汉字
    private String user;//用户名
    private String password;//用户密码



    private DBUtil(){
        this(new Builder());
    }

    private DBUtil(Builder builder) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.url = builder.url;
        this.user = builder.user;
        this.password = builder.password;
    }

    public static final class Builder {
        private String url;//设置数据库的地址 设置编码  支持汉字
        private String user;//用户名
        private String password;//用户密码

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public DBUtil build(){
            return new DBUtil(this);
        }

        public Builder() {
        }

        public Builder init(){
            url = "jdbc:mysql://localhost:3306/online_chat";//设置数据库的地址 设置编码  支持汉字
            user="root";//用户名
            password = "Sdz_926419";//用户密码
            return this;
        }

    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }

    public int QueryNum(String command) throws SQLException {
        Connection connection = getConnection();
        Statement stat = connection.createStatement();
        ResultSet resultSet = stat.executeQuery(command);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 1;
    }

    public void InsertUser(String command) throws SQLException {
        Connection connection = getConnection();
        Statement stat = connection.createStatement();
        stat.executeUpdate(command);
    }

    public String QueryPassword(String command) throws SQLException {
        Connection connection = getConnection();
        Statement stat = connection.createStatement();
        ResultSet result = stat.executeQuery(command);
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }
}
