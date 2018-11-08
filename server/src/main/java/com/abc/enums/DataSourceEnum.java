package com.abc.enums;

/**
 *
 * @author cjie
 * @date 2018/11/8 0008
 */
public enum  DataSourceEnum {
    /**
     * 主库
     */
    DB_BIU("biu"),
    /**
     * 变啦库
     */
    DB_BIANLA("bianla"),
    /**
     * 社区库
     */
    DB_COMMUNITY("community");

    private String value;

    DataSourceEnum(String value){this.value=value;}

    public String getValue() {
        return value;
    }
}
