package com.abc.webDev;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 * Title: 根据输入的SQL语句进行查询，适用于多条记录的查询。
 * </p>
 */
public class DbQuery {
	//private static final Logger log = Logger.getLogger(DbQuery.class);
	private String sqlStr = null;

	private DbConnection db = null;

	private Connection con = null;

	private Statement stmt = null;

	private ResultSet rs = null;

	private ArrayList<HashMap<String, String>> rsss = null; // 存放最终得到的记录集，每行记录内部是一个HashTable

	private ArrayList<String> acolumnname = null; // 存放列名

	private ArrayList<String> acolumnTypeName = null; // 列的类型
	private ArrayList<Integer> acolumnDisplaySize = null; // 列的长度
	private ArrayList<Integer> acolumnNullable = null; // 列是否允许为空

	private int totalRow = 0; // 存放记录行数

	private static String[] COLUMN_TYPE_NUMBER = { "decimal", "double",
			"float", "money" };

	public DbQuery(String sqlStr) {
		select(sqlStr, true);
	}

	public DbQuery(String sqlStr, boolean showSql) {
		select(sqlStr, showSql);
	}
	
	public DbQuery(String sqlStr, StringBuffer log){
		if(log != null){
			if(log.length()==0) {
				log.append(sqlStr);
			} else{
				log.append(", ");
				log.append(sqlStr);
			}
		}
		select(sqlStr, true);
	}
	public DbQuery(String sqlStr, boolean showSql, StringBuffer log){
		if(log != null){
			if(log.length()==0) {
				log.append(sqlStr);
			} else{
				log.append(", ");
				log.append(sqlStr);
			}
		}
		select(sqlStr, showSql);
	}
	/**
	 * 用于在DbExecuteTra中调用，在同一会话中查询最后的ID用
	 * 
	 * @param stmt
	 *            Statement
	 * @param sqlStr
	 *            String
	 */
	public DbQuery(Statement stmt, String sqlStr, boolean showSql) {
		select(stmt, sqlStr);
	}
	public DbQuery(Statement stmt, String sqlStr, boolean showSql, StringBuffer log) {
		if(log != null){
			if(log.length()==0) {
				log.append(sqlStr);
			} else{
				log.append(", ");
				log.append(sqlStr);
			}
		}
		select(stmt, sqlStr);
	}
	
	private void select(String sqlStr, boolean showSql) {
		if (showSql) {
			Info.print("查询SQL：\n" + sqlStr);
		}
		this.execute_init(sqlStr);
		this.execute();
		// this.close();
		if (showSql) {
			Info.print("查询结果：返回 " + row() + " 行");
		}
	}

	private void select(Statement stmt, String sqlStr) {
		Info.print("查询SQL：\n" + sqlStr);
		this.execute_init(sqlStr);
		this.execute(stmt, sqlStr);
		Info.print("查询结果：返回 " + row() + " 行");
	}

	/**
	 * 返回查询结果
	 * 
	 * @return 查询结果
	 */
	public ArrayList<HashMap<String, String>> getResult() {
		return rsss;
	}

	/**
	 * 返回查询结果对应的列名清单
	 * 
	 * @return 查询结果对应的列名清单
	 */
	public ArrayList<String> getColumnName() {
		return acolumnname;
	}

	/**
	 * 返回查询结果对应的列名清单
	 * 
	 * @return 查询结果对应的列名清单
	 */
	public ArrayList<String> getColumnTypeName() {
		return acolumnTypeName;
	}

	/**
	 * 返回查询结果对应的列名清单
	 * 
	 * @return 查询结果对应的列名清单
	 */
	public ArrayList<Integer> getColumnDisplaySize() {
		return acolumnDisplaySize;
	}

	/**
	 * 返回查询结果对应的列名清单
	 * 
	 * @return 查询结果对应的列名清单
	 */
	public ArrayList<Integer> getColumnNullable() {
		return acolumnNullable;
	}

	/**
	 * 返回某一行记录集
	 * 
	 * @param rowNo
	 *            行号
	 * @return
	 */
	public HashMap<String, String> getRow(int rowNo) {
		if (rowNo >= 0 && rowNo < this.row() - 1) {
			HashMap<String, String> rowmap = new HashMap<String, String>();
			rowmap.putAll(rsss.get(rowNo));
			return rowmap;
		} else {
			Error.print("欲获取的行号越界.欲获取第 " + rowNo + " 行,但查询所得的行号范围为[0,"
					+ (this.row() - 1) + "]");
			return null;
		}

	}

	public String get(String columnName) {
		if (row() > 1) {
			Error
					.print(
							sqlStr
									+ "\n 查询结果大于1条，不能用getValue(String columnName)方法。(columnName="
									+ columnName + ")", null);
			return "出错!!";
		}
		return get(0, columnName);
	}

	public String get2(String columnName) {
		String tmp = get(columnName);
		if (tmp.equals("")) {
			return "&nbsp;";
		} else {
			return tmp;
		}
	}


	public String getNum(String columnName) {
		String tmp = get(columnName);
		if (StringUtils.isEmpty(tmp)) {
			return "0";
		} else {
			return tmp;
		}
	}

	/**
	 * 返回某一行记录集的某个字段
	 *
	 * @param rowNo
	 *            行号，最小为0，最大为row()-1
	 * @return
	 */
	public String get(int rowNo, String columnName) {
		if (rowNo >= 0 && rowNo <= (this.row() - 1)) {
			HashMap<String, String> rowmap = rsss.get(rowNo);
			String tmpValue = (String) rowmap.get(columnName.toLowerCase());
			if (tmpValue == null) {
				Error.print("欲获取的列名(" + columnName + ")不存在(" + sqlStr + ")!",
						null);
				return "出错！";
			} else {
				return tmpValue;
			}
		} else {
			Error.print("欲获取的行号越界.欲获取第 " + rowNo + " 行,但查询所得的行号范围为[0,"
					+ (this.row() - 1) + "]", null);
			return "出错！";
		}
	}

	/**
	 * 返回某一行记录集的某个字段
	 *
	 * @param rowNo
	 *            行号，最小为0，最大为row()-1
	 * @return
	 */
	public String get2(int rowNo, String columnName) {
		String r = get(rowNo, columnName);
		if (r == null || r.equals("")) {
			return "&nbsp;";
		} else {
			return r;
		}
	}



	public String getNum(int rowNo, String columnName) {
		String tmpValue = get(rowNo, columnName);
		if (StringUtils.isEmpty(tmpValue)) {
			return "0";
		} else {
			return tmpValue;
		}
	}

	/**
	 * 返回总行数
	 *
	 * @return 总行数
	 */
	public int row() {
		if (rsss != null) {
			totalRow = rsss.size();
		} else {
			totalRow = 0;
		}
		return totalRow;
	}

	/**
	 * 返回总列数
	 *
	 * @return 总列数
	 */
	public int column() {
		if (acolumnname != null) {
			return acolumnname.size();
		} else {
			return 0;
		}
	}



	/**
	 * 生成某行数据的文字记录，用于记在日志里
	 *
	 * @param i
	 *            int
	 * @return String
	 */
	public String getRowString(int rowNo) {
		StringBuffer sb = new StringBuffer("ROW_ID:" + rowNo);
		if (row() < (rowNo + 1)) {
			sb.append(" 超过总行数[" + row() + "]，无法取到！");
		} else {
			ArrayList<String> column = getColumnName();
			int r = column.size();
			for (int i = 0; i < r; i++) {
				if (i != 0) {
					sb.append(",");
				} else {
					sb.append("  ");
				}
				sb.append(column.get(i).toString() + "="
						+ get(rowNo, column.get(i).toString()));
			}
		}
		return sb.toString();
	}

	/**
	 * 生成某行数据的文字记录，用于记在日志里
	 *
	 * @return String
	 */
	public String getRowString() {
		StringBuffer sb = new StringBuffer("ROW_ID:0");
		if (row() != 1) {
			sb.append(" 有多条[" + row() + "]数据，请用getRowString(rowNo)方法！");
		} else {
			ArrayList<String> column = getColumnName();
			int r = column.size();
			for (int i = 0; i < r; i++) {
				if (i != 0) {
					sb.append(",");
				} else {
					sb.append("  ");
				}
				sb.append(column.get(i).toString() + "="
						+ get(0, column.get(i).toString()));
			}
		}
		return sb.toString();
	}

	private void execute_init(String querysqlStr) {
		sqlStr = querysqlStr.trim();
	}

	private ResultSet execute() {
		try {
			db = new DbConnection();
			con = db.getConnection();
			stmt = con.createStatement();
			execute(stmt, sqlStr);
		} catch (Exception e) {
			Error.print("连接数据库出错", e);
			e.printStackTrace();

		} finally {
			close();

		}
		return rs;
	}

	private ResultSet execute(Statement stmt, String sqlStr) {
		try {
			if (stmt == null) {
				return null;
			}
			// 进行真正的查询
			rs = stmt.executeQuery(sqlStr);
			ResultSetMetaData rsmd = rs.getMetaData();
			String columnName = null;
			String columnType = null;
			String columnValue = null;
			rsss = new ArrayList<HashMap<String, String>>(); // 存放记录集
			acolumnname = new ArrayList<String>(); // 存放列名
			acolumnTypeName = new ArrayList<String>();
			acolumnDisplaySize = new ArrayList<Integer>();
			acolumnNullable = new ArrayList<Integer>();

			// 生成列名
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				columnName = rsmd.getColumnName(i);
				columnName = columnName.trim().toLowerCase();
				acolumnname.add(columnName);
				acolumnTypeName.add(rsmd.getColumnTypeName(i));
				acolumnDisplaySize.add(rsmd.getColumnDisplaySize(i));
				acolumnNullable.add(rsmd.isNullable(i));
				// System.out.println("列名:"+columnName+" 类型名称:"+rsmd.getColumnTypeName(i)
				// +" 显示长度:"+rsmd.getColumnDisplaySize(i)
				// +"  是否可空:"+rsmd.isNullable(i));
			}

			if (rs.next()) {
				java.util.Arrays.sort(COLUMN_TYPE_NUMBER);
				do {
					HashMap<String, String> amap = new HashMap<String, String>(); // 存放一行数据，临时变量
					// System.out.println("adsfalsdfjlasd鑫");
					for (int i = 0; i < acolumnname.size(); i++) {
						columnName = (String) acolumnname.get(i);
						columnType = acolumnTypeName.get(i).toString();
						// System.out.println("列名:"+columnName+"  类型:["+columnType+"]");
						if (java.util.Arrays.binarySearch(COLUMN_TYPE_NUMBER,
								columnType) > -1) {
							// System.out.println(java.util.Arrays.binarySearch(COLUMN_TYPE_NUMBER,
							// columnType));
							columnValue = String.valueOf(rs
									.getDouble(columnName));
							// System.out.println("列名:"+columnName+"  类型:"+columnType+"  值:"+columnValue);
						} else {
							columnValue = rs.getString(columnName);
						}
						if (columnValue != null) {
							columnValue = columnValue.trim();
						} else {
							columnValue = "";
						}
						amap.put(columnName, columnValue);
						// ReportInfo.debug(columnName+"="+columnValue);
					}
					rsss.add(amap);
					amap = null;
				} while (rs.next());

			} else {
				// 记录集为空
			}
		} catch (Exception e) {
			Error.print("连接数据库出错", e);
			e.printStackTrace();

		}
		return rs;
	}

	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			// Error.print("关闭连接出错", e);
		}
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			// Error.print("关闭连接出错", e);
		}
		try {
			// Info.print("关闭连接!");
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			// Error.print("关闭连接出错", e);
		}
		try {
			if (db != null) {
				db.closeConnetion(con);
			}
		} catch (Exception e) {
			// Error.print("关闭连接出错", e);
		}
	}

	public static void main(String[] argv) {
		java.util.Arrays.sort(COLUMN_TYPE_NUMBER);

		System.out.println("包含 money? "
				+ java.util.Arrays.binarySearch(COLUMN_TYPE_NUMBER, "money"));
		System.out.println("包含 numeric? "
				+ java.util.Arrays.binarySearch(COLUMN_TYPE_NUMBER, "numeric"));
		System.out.println("包含 decimal? "
				+ java.util.Arrays.binarySearch(COLUMN_TYPE_NUMBER, "decimal"));
		System.out.println("包含 int? "
				+ java.util.Arrays.binarySearch(COLUMN_TYPE_NUMBER, "int"));
		System.out.println("包含 float? "
				+ java.util.Arrays.binarySearch(COLUMN_TYPE_NUMBER, "float"));
		System.out.println("包含 double? "
				+ java.util.Arrays.binarySearch(COLUMN_TYPE_NUMBER, "double"));
		// {"money","numeric","decimal","int","float","double"};
		// if(true)
		// return;
		System.out.println("演示：取多行记录-------");
		DbQuery d = new DbQuery("select * from jy_log"); // "select * from userinfo");
		for (int i = 0; i < d.row(); i++) {
			System.out.println(d.getRowString(i));
		}
		// 查所有表：select * from information_schema.tables
		/*
		 * for (int i = 0; i < d.row(); i++) { Info.print("第" + i +
		 * "行记录："); for (int j = 0; j < d.getColumnName().size(); j++) { String
		 * columnName = d.getColumnName().get(j) .toString();
		 * System.out.print(columnName + " =" + d.getValue(i, columnName) +
		 * " | "); } Info.print("\n"); }
		 */
	}
}
