package com.itheima.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {
    private static ComboPooledDataSource ds = new ComboPooledDataSource();
    private static ThreadLocal<Connection> tl=new ThreadLocal<>();

    public static DataSource getDataSource() {
        return ds;
    }
 

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
 

    public static void closeResource(Connection conn, Statement st, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
        closeConn(conn);
    }
 

    public static void closeConn(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
 
    }
 
    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            st = null;
        }
 
    }
 
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
 
    }
    
  //开启事务
    public static void startTransaction() throws SQLException{
        getConnection().setAutoCommit(false);
    }

    /**
     * 事务提交且释放连接
     */
    public static void commitAndClose(){
        Connection conn = null;
        try {
            conn=getConnection();
            //事务提交
            conn.commit();
            //关闭资源
            conn.close();
            //解除版定
            tl.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 事务回滚且释放资源
     */
    public static void rollbackAndClose(){
        Connection conn = null;
        try {
            conn=getConnection();
            //事务回滚
            conn.rollback();
            //关闭资源
            conn.close();
            //解除版定
            tl.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}