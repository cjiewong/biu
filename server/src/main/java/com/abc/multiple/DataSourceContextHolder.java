package com.abc.multiple;

/**
 *
 * @author cjie
 * @date 2018/11/8 0008
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new InheritableThreadLocal<>();

    /**
     *  设置数据源
     * @param db 数据库名
     */
    public static void setDataSource(String db){
        CONTEXT_HOLDER.set(db);
    }

    /**
     * 取得当前数据源
     * @return 当前数据库名
     */
    public static String getDataSource(){
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clear(){
        CONTEXT_HOLDER.remove();
    }
}
