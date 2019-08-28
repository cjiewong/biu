package com.abc.webDev;

import java.sql.Connection;
import java.sql.Statement;

/**
 * <p>Title: 执行SQL语句：delete,update,insert适用</p>
 * @version 1.0
 */

public class DbExecute {

	public static int go(String DBDriver, String DBUrl, String DBUser, String DBPw,
                         String sqlStr) {
		Info.print("执行SQL：" + sqlStr);
		return goInner(DBDriver, DBUrl, DBUser, DBPw, sqlStr);
	}

	public static int go(String sqlStr) {
		return go(sqlStr, true);
	}
	public static int go(String sqlStr, StringBuffer log){
		if(log != null){
			if(log.length()==0) {
				log.append(sqlStr);
			} else{
				log.append(", ");
				log.append(sqlStr);
			}
		}
		return go(sqlStr);
	}
	public static int go(String sqlStr, boolean showable) {
		if(showable){
			Info.print("执行SQL：" + sqlStr);
		}
		return goInner(sqlStr, showable);
	}
	public static int go(String sqlStr, boolean showable, StringBuffer log){
		if(log != null){
			if(log.length()==0) {
				log.append(sqlStr);
			} else{
				log.append(", ");
				log.append(sqlStr);
			}
		}
		return go(sqlStr, showable);
	}
	private static int goInner(String sqlStr, boolean showable) {
		String teststr = sqlStr; //用来测试的语句
		DbConnection db = null;
		Connection con = null;
		Statement stmt = null;
		int i = -1; //若返回为-1代表出错
		try {
			db = new DbConnection();
			con = db.getConnection();
			stmt = con.createStatement();
			i = stmt.executeUpdate(teststr);
			if(showable) {
				Info.print("执行结果：影响 " + i + " 行 (" + teststr + ")");
			}

		} catch (java.sql.SQLException sqle) {
			Error.print("SQL语句执行出错\n" + teststr, sqle);
		} catch (Exception e) {
			Error.print("连接数据库出错", e);
		} finally {
			try {
				stmt.close();
			} catch (Exception e) {
			}
			try {
				con.close();
			} catch (Exception e) {
			}
			try {
				db.closeConnetion(con);
			} catch (Exception e) {
			}
		}
		return i;
	}
	/**
	 * 私有方法：多线程执行的SQL语句
	 * @param sqlStr
	 * @return
	 */
	private static int goInner(String DBDriver, String DBUrl, String DBUser,
                               String DBPw, String sqlStr) {
		return goInner(sqlStr, true);
	}

	public static void main(String[] argv) {
		String DBDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String DBUrl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=";
		DBUrl+="D:/project/kuaijianwang/site/gyy-web-dev/config.mdb";
		String DBUser = "";
		String DBPw = "";

		String sqlStr = "update test set v='66' ";
		Info.print("" + DbExecute.go(DBDriver, DBUrl, DBUser, DBPw, sqlStr));
	}
}
