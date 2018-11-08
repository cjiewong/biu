package com.abc.annotation;

import com.abc.enums.DataSourceEnum;

import java.lang.annotation.*;

/**
 *
 * @author cjie
 * @date 2018/11/8 0008
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DataSourceEnum value() default DataSourceEnum.DB_BIU;
}
