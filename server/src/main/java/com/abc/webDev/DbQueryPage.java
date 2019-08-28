package com.abc.webDev;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * Title: 查询数据库，适用于分页的情况。
 */

public class DbQueryPage {
	private DbQuery dSearch;

	private int pageTotal; // 总页数

	private int recordTotal;// 总记录数

	private int pageIndex;// 当前显示的是第几页

	/**
	 * 设置初始查询条件
	 * 
	 * @param tableName
	 *            欲搜索的表名
	 * @param showColumn
	 *            返回的列名
	 * @param whereTerm
	 *            欲加上去的where条件，如：state='已收到'
	 * @param orderByColumnName
	 *            排序的字段名
	 * @param orderMode
	 *            排序方式：asc,desc
	 * @param pageSize
	 *            每页显示记录数
	 * @param pageIndex
	 *            当前需要返回第几页的数据，取值为1、2、3、...
	 * @param idColumn
	 *            唯一值的列，作为分页的参照物
	 */
	public DbQueryPage(String tableName, String showColumn, String whereTerm,
                       String orderByColumnName, String orderMode, int pageSize,
                       int pageIndex, String idColumn) {
		search(tableName, showColumn, whereTerm, orderByColumnName, orderMode,
				pageSize, pageIndex, idColumn);
	}
	public DbQueryPage(String tableName, String showColumn, String whereTerm,
                       String orderByColumnName, String groupByColumnName, String orderMode, int pageSize,
                       int pageIndex, String idColumn) {
		search1(tableName, showColumn, whereTerm, orderByColumnName,groupByColumnName, orderMode,
				pageSize, pageIndex, idColumn);
	}

	/**
	 * 设置初始查询条件
	 * 
	 * @param tableName
	 *            欲搜索的表名
	 * @param showColumn
	 *            返回的列名
	 * @param whereTerm
	 *            欲加上去的where条件，如：state='已收到'
	 * @param orderByColumnName
	 *            排序的字段名
	 * @param orderMode
	 *            排序方式：asc,desc
	 * @param pageSize
	 *            每页显示记录数
	 * @param pageIndex
	 *            当前需要返回第几页的数据，取值为1、2、3、...
	 * @param idColumn
	 */
	private void search1(String tableName, String showColumn, String whereTerm,
                         String orderByColumnName, String groupByColumnName, String orderMode, int pageSize,
                         int pageIndex, String idColumn) {
		if (pageIndex < 1) {
			Error.print("错误：DbQuery4构造函数中输入的最后一个参数int pageIndex现为" + pageIndex
					+ "，但不该小于1。\n已经默认改为1。\n请检查分页中是否有逻辑错误！");
			this.pageIndex = 1;
		} else {
			this.pageIndex = pageIndex;
		}

		// 先进行预查询，确定总记录数、总页数
		StringBuffer sql0 = new StringBuffer("select count(0) as num from "
				+ tableName);
		if (whereTerm != null && !whereTerm.trim().equals("")) {
			sql0.append(" where " + whereTerm + " ");
		}
		DbQuery d2 = new DbQuery(sql0.toString());
		recordTotal = Integer.parseInt(d2.get("num")); // 得到总记录数
		if (recordTotal == 0) {
			// 没有记录
			Info.print("没有找到符合条件的记录，SQL="+sql0.toString());
		}
		if (recordTotal % pageSize == 0) {
			pageTotal = (recordTotal / pageSize);// 获取总页数
		} else {
			pageTotal = (recordTotal / pageSize) + 1; // 获取总页数
		}

		if (pageTotal >0 && pageTotal < this.pageIndex) {
			Error.print("错误：DbQuery4构造函数中输入的最后一个参数int pageIndex现为" + pageIndex
					+ "，但最大只能是" + pageTotal + "。\n已经默认改为" + pageTotal
					+ "。\n请检查分页中是否有逻辑错误！");
			this.pageIndex = pageTotal;
		}

		// 再进行分页查询
		/*
		 * String sql = "SELECT "+showColumn+" FROM " + tableName + " WHERE " +
		 * whereTerm + " ORDER BY " + orderByColumnName + " " + orderMode + "
		 * limit " + (pageIndex - 1) * pageSize + "," + pageIndex*pageSize; SQL
		 * SERVER ======================= select top 10 *　from table1 　　 * where
		 * id> 　　 * (select max (id) from 　　(select top ((页码-1)*页大小) id from
		 * table1 order by id) as T)　order by id 数据库设计时，应该都设计一个ID字段，用作主键 新SQL
		 * SERVER SELECT TOP 页大小 * FROM Users WHERE (ID NOT IN (SELECT TOP
		 * (页大小*(页数-1)) ID FROM Users ORDER BY ID DESC)) ORDER BY ID DESC
		 */
		StringBuffer sql = new StringBuffer("select  ");
		if (showColumn != null && !showColumn.trim().equals("")) {
			sql.append(" " + showColumn);
		} else {
			sql.append(" *");
		}
		sql.append(" from ");
		sql.append(tableName);
		if (whereTerm != null && !whereTerm.trim().equals("")) {
			sql.append(" where " + whereTerm);
		}



		// sql.append(" order by "+idColumn+" )");
		// sql.append(" ( select max ("+idColumn+") from (select top "+((this.pageIndex-1)*pageSize)+" "+idColumn+" from "+tableName+" order by "+idColumn+"  ) T)"
		// );
		if (groupByColumnName != null && !groupByColumnName.trim().equals("")) {
			sql.append(" group by " + groupByColumnName + " ");
		}
		if (orderByColumnName != null && !orderByColumnName.trim().equals("")) {
			sql.append(" order by " + orderByColumnName + " ");
		}
		if (orderMode != null && !orderMode.trim().equals("")) {
			sql.append(orderMode + " ");
		}

		// 截取
		int topNumber = (this.pageIndex - 1) * pageSize;
		if (topNumber < 0) {
			topNumber = 0;
		}
		//int endNumber = pageSize;
		//if(this.pageIndex > 0)
			//endNumber = this.pageIndex*pageSize;
		sql.append(" limit "+topNumber+", "+pageSize);

		dSearch = new DbQuery(sql.toString());
	}


	private void search(String tableName, String showColumn, String whereTerm,
                        String orderByColumnName, String orderMode, int pageSize,
                        int pageIndex, String idColumn) {
		if (pageIndex < 1) {
			Error.print("错误：DbQuery4构造函数中输入的最后一个参数int pageIndex现为" + pageIndex
					+ "，但不该小于1。\n已经默认改为1。\n请检查分页中是否有逻辑错误！");
			this.pageIndex = 1;
		} else {
			this.pageIndex = pageIndex;
		}

		// 先进行预查询，确定总记录数、总页数
		StringBuffer sql0 = new StringBuffer("select count(0) as num from "
				+ tableName);
		if (whereTerm != null && !whereTerm.trim().equals("")) {
			sql0.append(" where " + whereTerm + " ");
		}
		DbQuery d2 = new DbQuery(sql0.toString());
		recordTotal = Integer.parseInt(d2.get("num")); // 得到总记录数
		if (recordTotal == 0) {
			// 没有记录
			Info.print("没有找到符合条件的记录，SQL="+sql0.toString());
		}
		if (recordTotal % pageSize == 0) {
			pageTotal = (recordTotal / pageSize);// 获取总页数
		} else {
			pageTotal = (recordTotal / pageSize) + 1; // 获取总页数
		}

		if (pageTotal >0 && pageTotal < this.pageIndex) {
			Error.print("错误：DbQuery4构造函数中输入的最后一个参数int pageIndex现为" + pageIndex
					+ "，但最大只能是" + pageTotal + "。\n已经默认改为" + pageTotal
					+ "。\n请检查分页中是否有逻辑错误！");
			this.pageIndex = pageTotal;
		}

		// 再进行分页查询
		/*
		 * String sql = "SELECT "+showColumn+" FROM " + tableName + " WHERE " +
		 * whereTerm + " ORDER BY " + orderByColumnName + " " + orderMode + "
		 * limit " + (pageIndex - 1) * pageSize + "," + pageIndex*pageSize; SQL
		 * SERVER ======================= select top 10 *　from table1 　　 * where
		 * id> 　　 * (select max (id) from 　　(select top ((页码-1)*页大小) id from
		 * table1 order by id) as T)　order by id 数据库设计时，应该都设计一个ID字段，用作主键 新SQL
		 * SERVER SELECT TOP 页大小 * FROM Users WHERE (ID NOT IN (SELECT TOP
		 * (页大小*(页数-1)) ID FROM Users ORDER BY ID DESC)) ORDER BY ID DESC
		 */
		StringBuffer sql = new StringBuffer("select  ");
		if (showColumn != null && !showColumn.trim().equals("")) {
			sql.append(" " + showColumn);
		} else {
			sql.append(" *");
		}
		sql.append(" from ");
		sql.append(tableName);
		if (whereTerm != null && !whereTerm.trim().equals("")) {
			sql.append(" where " + whereTerm);
		}
	
		

		// sql.append(" order by "+idColumn+" )");
		// sql.append(" ( select max ("+idColumn+") from (select top "+((this.pageIndex-1)*pageSize)+" "+idColumn+" from "+tableName+" order by "+idColumn+"  ) T)"
		// );
		
		if (orderByColumnName != null && !orderByColumnName.trim().equals("")) {
			sql.append(" order by " + orderByColumnName + " ");
		}
		if (orderMode != null && !orderMode.trim().equals("")) {
			sql.append(orderMode + " ");
		}
		// 截取
		int topNumber = (this.pageIndex - 1) * pageSize;
		if (topNumber < 0) {
			topNumber = 0;
		}
		//int endNumber = pageSize;
		//if(this.pageIndex > 0)
			//endNumber = this.pageIndex*pageSize;
		sql.append(" limit "+topNumber+", "+pageSize);

		dSearch = new DbQuery(sql.toString());
	}

	/**
	 * 返回所有页一共的记录数
	 * 
	 * @return
	 */
	public int getAllPageTotalRow() {
		return recordTotal;
	}

	/**
	 * 返回总页数
	 * 
	 * @return
	 */
	public int getAllPageNum() {
		return pageTotal;
	}

	/**
	 * 返回当前显示的第几页
	 * 
	 * @return
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * 返回当前页的总行数
	 * 
	 * @return 总行数
	 */
	public int row() {
		return dSearch.row();
	}

	/**
	 * 返回某一行记录集的某个字段
	 * 
	 * @param rowNo
	 *            行号，最小为0，最大为row()-1
	 * @return
	 */
	public String get(int rowNo, String columnName) {
		return dSearch.get(rowNo, columnName);
	}

	public String get2(int rowNo, String columnName) {
		String tmp = dSearch.get(rowNo, columnName);
		if (tmp.equals("")) {
			return "&nbsp;";
		} else {
			return tmp;
		}
	}

	public String get(String columnName) {
		return get(0, columnName);
	}

	public String get2(String columnName) {
		return get2(0, columnName);
	}



	public String getNum(int rowNo, String columnName) {
		String tmpValue = get(rowNo, columnName);
		if (StringUtils.isEmpty(tmpValue)) {
			return "0";
		} else {
			return tmpValue;
		}
	}

	public String getNum(String columnName) {
		String tmpValue = get(0, columnName);
		if (StringUtils.isEmpty(tmpValue)) {
			return "0";
		} else {
			return tmpValue;
		}
	}

	public static void main(String[] args) {
		String tableName = "JY_CLIENT"; // 表名
		String showColumn = "NAME,FULL_NAME"; // 欲查询的列名
		String whereTerm = null; // where条件
		String orderByColumnName = "id"; // 按何字段排序
		String orderMode = "asc"; // asc,desc
		String idColumn = "id";
		int pageSize = 2;// 每页显示记录数
		int pageIndex = 1;// 当前显示页数
		DbQueryPage dd = new DbQueryPage(tableName, showColumn, whereTerm,
				orderByColumnName, orderMode, pageSize, pageIndex, idColumn);
		System.out.println("共有" + dd.getAllPageTotalRow() + "条记录");
		System.out.println("共有" + dd.getAllPageNum() + "页");
		for (int i = 0; i < dd.row(); i++) {
			System.out.println(dd.get(i, "full_name"));
		}
	}

}
