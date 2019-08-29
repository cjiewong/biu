package com.makergo.webDev;


import com.makergo.util.DateTimeUtil;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * <p>Title: 执行SQL语句：delete,update,insert适用.语句提交后，必须调用commit方法，才会生效。</p>
 * <p>Description: 实现事务处理中的SQL执行。</p>
 */

public class DbExecuteTra {
  private ArrayList<String> sqlList = new ArrayList<String>(); //存放需要执行的一系列SQL语句
  private int[] changeRowCount; //存放每个SQL语句的影响行数

  private DbConnection db = null;
  private Connection con = null;
  private Statement stmt = null;
  boolean autoCommit = false;
  private StringBuffer log = null;

  public DbExecuteTra(StringBuffer log) {
	  if(log != null) {
        this.log = log;
      } else {
        this.log = new StringBuffer();
      }
  }

  /**
   * 返回执行的结果
   * @return
   */
  public int[] getResult() {
    return changeRowCount;
  }

  public boolean beginTra(){
    Info.print(DateTimeUtil.getDateTimeNow()+" 开始事务!");
    if(log.length()!=0) {
      log.append(", ");
    }
    log.append(DateTimeUtil.getDateTimeNow()+" 开始事务!");
    try {
      db = new DbConnection();
      con = db.getConnection();
      autoCommit = con.getAutoCommit();
      con.setAutoCommit(false); // 更改JDBC事务的默认提交方式
      stmt = con.createStatement();
      return true;
    }
    catch (Exception e) {
      Error.print("开始事务出错:", e);
      if(log.length()!=0) {
        log.append(", ");
      }
      log.append("开始事务出错:"+e.toString());
      return false;
    }
  }

  public int exe(String sql) {
	if(log.length()!=0) {
      log.append(", ");
    }
    log.append(DateTimeUtil.getDateTimeNow()+" 执行SQL["+sql+"]");
    Info.print(DateTimeUtil.getDateTimeNow()+" 执行SQL["+sql+"]");
    int i = -1;
    try {
      i = stmt.executeUpdate(sql);
      Info.print(DateTimeUtil.getDateTimeNow() + "DbExecuteTra commit SQL[" + i +
                 "]：\n" + sql + " 预执行影响行数:" + i);
      if(log.length()!=0) {
        log.append(", ");
      }
      log.append(DateTimeUtil.getDateTimeNow() + "DbExecuteTra commit SQL[" + i +
              "]：" + sql + " 预执行影响行数:" + i);
      return i;
    }
    catch (Exception ex) {
      Error.print("执行SQL出错["+sql+"]:", ex);
      if(log.length()!=0) {
        log.append(", ");
      }
      log.append("执行SQL出错["+sql+"]:"+ex.toString());
      return -1;
    }
  }

  public boolean addBatch(String sql){
    Info.print(DateTimeUtil.getDateTimeNow()+" 向批执行中添加SQL["+sql+"]!");
    if(log.length()!=0) {
      log.append(", ");
    }
    log.append(DateTimeUtil.getDateTimeNow()+" 向批执行中添加SQL["+sql+"]!");
    try{
      stmt.addBatch(sql);
    } catch (Exception e) {
      Error.print("添加SQL["+sql+"]出错:", e);
      if(log.length()!=0) {
        log.append(", ");
      }
      log.append("添加SQL["+sql+"]出错:"+e.toString());
      return false;
    }
    sqlList.add(sql);
    return true;
  }
  public boolean clearBatch(){
    Info.print(DateTimeUtil.getDateTimeNow()+" 清除批执行中的SQL!");
    if(log.length()!=0) {
      log.append(", ");
    }
    log.append(DateTimeUtil.getDateTimeNow()+" 清除批执行中的SQL!");
    try{
      stmt.clearBatch();
    } catch (Exception e) {
      Error.print("清除批SQL出错:", e);
      if(log.length()!=0) {
        log.append(", ");
      }
      log.append("清除批SQL出错:"+e.toString());
      return false;
    }
    sqlList.clear();
    return true;
  }
  public int[] exeBatch(){
    Info.print(DateTimeUtil.getDateTimeNow()+" 执行批执行!");
    if(log.length()!=0) {
      log.append(", ");
    }
    log.append(DateTimeUtil.getDateTimeNow()+" 执行批执行!");
    try{
      changeRowCount = stmt.executeBatch();
      return changeRowCount;
    } catch (Exception e) {
      Error.print("执行批SQL出错:", e);
      if(log.length()!=0) {
        log.append(", ");
      }
      log.append("执行批SQL出错:"+e.toString());
      return null;
    }
  }

  public boolean commitTra(){
    try{
      Info.print(DateTimeUtil.getDateTimeNow()+" 提交事务!");
      if(log.length()!=0) {
        log.append(", ");
      }
      log.append(DateTimeUtil.getDateTimeNow()+" 提交事务!");
      con.commit();
      con.setAutoCommit(autoCommit); // 恢复JDBC事务的默认提交方式
      stmt.close();
      stmt = null;
      con.close();
      db.closeConnetion(con);
      con = null;
      return true;
    } catch (Exception e) {
      Error.print("提交事务出错:", e);
      if(log.length()!=0) {
        log.append(", ");
      }
      log.append("提交事务出错:"+ e.toString());
      return false;
    }
  }
  public boolean rollbackTra(){
    try{
      Info.print(DateTimeUtil.getDateTimeNow()+" 回滚事务!");
      if(log.length()!=0) {
        log.append(", ");
      }
      log.append(DateTimeUtil.getDateTimeNow()+" 回滚事务!");
      con.rollback();
      con.setAutoCommit(autoCommit); // 恢复JDBC事务的默认提交方式
      stmt.close();
      stmt = null;
      con.close();
      db.closeConnetion(con);
      con = null;
      return true;
    } catch (Exception e) {
      Error.print("回滚事务出错:", e);
      if(log.length()!=0) {
        log.append(", ");
      }
      log.append("回滚事务出错:"+ e.toString());
      return false;
    }
  }
  public DbQuery getDbQuery(String sqlStr){
    boolean newStmt = false;
    if(stmt == null){
      Info.print("没有stmt，新启事务");
      beginTra();
      newStmt = true;
    }
    DbQuery dd = new DbQuery(stmt, sqlStr, true, log);
    if(newStmt){
      commitTra();
    }
    return dd;
  }

  public DbQuery getDbQuery(String sqlStr, boolean showSql) {
    boolean newStmt = false;
    if (stmt == null) {
      Info.print("没有stmt，新启事务");
      beginTra();
      newStmt = true;
    }
    DbQuery dd = new DbQuery(stmt, sqlStr, showSql, log);
    if (newStmt) {
      commitTra();
    }
    return dd;
  }

  public StringBuffer getLog(){
	  return log;
  }
  
  public void setLog(StringBuffer sqllog){
	  log = sqllog;
  }
  
  public static void main(String[] argv) {
    try{
      DbExecuteTra d = new DbExecuteTra(null);
      if (d.beginTra()) {
        for (int i = 0; i < 100000; i++) {
          int t = d.exe("insert into test(test) values('值" + i + "')");
          if(t == -1) {
            break;
          }
          if (i % 1000 == 0) {
            Thread.sleep(30);
          }
        }
        //System.out.println("影响行数:"+i);
        //DbQuery dd = new DbQuery("select SCOPE_IDENTITY() as ID");
        //System.out.println("最新ID："+dd.getValue("ID"));
        //d.addBatch("insert into test(test) values('222')");
        //d.addBatch("insert into test(test) values('333')");
        //d.exeBatch();
        //dd = d.getDbQuery("select SCOPE_IDENTITY() as ID");
        //System.out.println(dd.getValue("ID"));
        d.commitTra();
      }

      //d.add("insert into test(test) values('111')");
      //d.add("insert into test(test) values('222')");

      //boolean result = d.commit();

      //Info.print("执行结果：" + result);
    }catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
