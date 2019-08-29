package com.makergo.webDev;

import com.makergo.util.SpringContextUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 建立连接
 * @author Administrator
 *
 */
public class DbConnection {


  public DbConnection() {
  }

  public Connection getConnection() {
    Connection conn = null;
    try {
      DataSource dataSource = (DataSource) SpringContextUtils.getBean("biu");
      conn = dataSource.getConnection();
    }
    catch (Exception e) {
      Error.print(e);
    }
    return conn;
  }

  public void closeConnetion(Connection conn) {
    try {
      if (conn != null) {
        conn.close();
      }
    }
    catch (SQLException e) {
      Error.print(e);
    }
  }

  /**
   * 测试数据库是否可连通
   *
   * @return
   */
  public boolean test() {
    boolean result = false;
    DbConnection db = new DbConnection();
    Connection con = db.getConnection();
    try {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select 1 ");

      if (rs.next() && rs.getString(1).equals("1")) {
        result = true;
      }
      rs.close();
      stmt.close();
      con.close();
    }
    catch (Exception e) {
      result = false;
      // ReportError.reportError("测试连接数据库出错",e);
    }
    finally {
      db.closeConnetion(con);
    }
    return result;
  }

  public static void main(String[] argv) {

    //DbConnection g = new DbConnection("org.gjt.mm.mysql.Driver", "jdbc:mysql://localhost:3306/kuaijianwang?characterEncoding=gbk","kuaijianwang", "kuaijianwang");
    DbConnection g = new DbConnection();
    System.out.println("测试结果：" + g.test());
    StringBuffer sb = new StringBuffer("1 | d | e | ");
    sb.delete(sb.lastIndexOf("|"), sb.length());
    System.out.println(sb.toString());
  }
}
