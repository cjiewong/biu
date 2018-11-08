package com.abc.aop;

import com.abc.annotation.DataSource;
import com.abc.multiple.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author cjie
 * @date 2018/11/8 0008
 */
@Component
@Slf4j
@Aspect
@Order(-1)
public class DataSourceAspect {
    @Pointcut("@within(com.abc.annotation.DataSource) || @annotation(com.abc.annotation.DataSource)")
    public void pointCut(){

    }
    @Before("pointCut() && @annotation(dataSource)")
    public void doBefore(DataSource dataSource){
        log.info("选择数据源---"+dataSource.value().getValue());
        DataSourceContextHolder.setDataSource(dataSource.value().getValue());
    }

    @After("pointCut()")
    public void doAfter(){
        DataSourceContextHolder.clear();
    }
}
