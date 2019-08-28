package com.abc.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author cjie
 * @Date 2018/12/8 0008.
 */
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringContextUtils.applicationContext == null) {
            SpringContextUtils.applicationContext = applicationContext;
        }
        System.out.println("========ApplicationContext配置成功,在普通类可以通过调用SpringContextUtils.getAppContext()获取applicationContext对象,applicationContext="+SpringContextUtils.applicationContext+"========");
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    // 获取当前环境
    public static Stream<String> getActiveProfilesStream() {
        return Arrays.stream(getActiveProfiles());
    }
    // 获取当前环境
    public static List<String> getActiveProfilesList() {
        return getActiveProfilesStream().collect(Collectors.toList());
    }
    // 获取当前环境
    public static String[] getActiveProfiles() {
        return getApplicationContext().getEnvironment().getActiveProfiles();
    }
}
