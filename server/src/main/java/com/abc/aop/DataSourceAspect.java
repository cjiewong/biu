package com.abc.aop;

import com.abc.annotation.DataSource;
import com.abc.enums.DataSourceEnum;
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
    //@within：用于匹配所有持有指定注解类型内的方法；
    // @annotation：用于匹配当前执行方法持有指定注解的方法；
    @Pointcut("@within(com.abc.annotation.DataSource) || @annotation(com.abc.annotation.DataSource)")
    //spring AOP 会忽略切面类从父类继承的方法,除非在切面类,也就是子类中对父类方法覆盖@Override.　
//    @Pointcut("execution(public * com.abc.service.impl.TagsServiceImpl.*(..))")
    public void pointCut(){

    }
//    @Before("pointCut() && @annotation(dataSource)")
//    public void doBefore(DataSource dataSource){
//        log.info("选择数据源---"+dataSource.value().getValue());
//        DataSourceContextHolder.setDataSource(dataSource.value().getValue());
//    }
@Before("pointCut()")
public void doBefore(){
    log.info("选择数据源---"+ DataSourceEnum.DB_BIANLA);
    DataSourceContextHolder.setDataSource(DataSourceEnum.DB_BIANLA.getValue());
}

    @After("pointCut()")
    public void doAfter(){
        DataSourceContextHolder.clear();
    }
}
