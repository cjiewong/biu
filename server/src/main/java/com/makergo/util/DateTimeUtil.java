package com.makergo.util;

/**
 * Created by cjie on 2018/2/2 0002.
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>Title: 取日期和时间的函数，单独写这样的类，避免因区域设置的不同而带来系统日期格式的不同
 * 注意格式字符串：MM代表两位的月，1月是01，M则1月是1，12月是12；同样HH、mm、ss和dd都是这样的</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author gyy
 * @version 1.0
 */
public class DateTimeUtil {
    /**
     *函数名称：取得当前日期、时间
     *@return 时间串，格式：14位时间yyyy-MM-dd HH:mm:ss来表示XXXX年XX月XX日XX时XX分XX秒
     */
   public static String getDateTimeNow() {
        return getDateTimeNow("yyyy-MM-dd HH:mm:ss");
    }

    /**
     *函数名称：取得当前日期
     *@return 时间串，格式：YYYY-MM-DD
     */
    public static String getDateNow() {
        return getDateTimeNow("yyyy-MM-dd");
    }

    /**
     *函数名称：取得当前日期
     *@return 时间串，格式：YYYYMMDD
     */
    public   static String getDateNow2() {
        return getDateTimeNow("yyyyMMdd");
    }

    /**
     *函数名称：取得当前时间
     *@return 时间串，格式：HHMMSS
     */
    public static String getTimeNow() {
        return getDateTimeNow("HH:mm:ss");
    }

    /**
     *函数名称：取得当前日期、时间
     *@param  format 格式串 如：YYYY-MM-DD HH:MM:SS
     *@return 时间串
     */
    public static String getDateTimeNow(String format) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        GregorianCalendar cal = new GregorianCalendar();
        return bartDateFormat.format(cal.getTime());
    }

    /**
     * 日期类型转换成字符串类型
     * @param date
     * @param format
     * @return
     */
    public static String dateTimeToStr(Date date, String format) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        return bartDateFormat.format(date);
    }

    /**
     * 字符串类型转换成日期类型
     * @param str
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date strToDateTime(String str, String format)
            throws ParseException {
        try {
            SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
            return bartDateFormat.parse(str);
        } catch (ParseException ex) {
            throw ex;
        }
    }

    /**
     * 日期字符串格式相互转换（默认yyyy-mm-dd转成yyyymmdd格式）
     * @param str     被转换字符串
     * @return
     * @throws ParseException */
    public static String dateStrConvert(String str) throws ParseException {
        Date date = strToDateTime(str, "yyyy-MM-dd");
        return dateTimeToStr(date, "yyyyMMdd");
    }

    /**
     * 取得一个输入日期指定天数以后的日期的字符串
     * @param dateStr 输入日期
     * @param dateAfter 指定的偏移天数
     * @return
     */
    public static String getDateAfter(String dateStr, int dateAfter) {
        int year = Integer.parseInt(dateStr.substring(0, 4));
        int month = Integer.parseInt(dateStr.substring(5, 7)) - 1;
        int day = Integer.parseInt(dateStr.substring(8, 10)) + dateAfter;
        GregorianCalendar calendar = new GregorianCalendar(year, month, day);
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return bartDateFormat.format(calendar.getTime());
    }

    /**
     * 取得一个输入日期指定天数以前的日期的字符串
     * @param dateStr 输入日期
     * @param dateBefore 指定的偏移天数
     * @return
     */
    public  static String getDateBefore(String dateStr, int dateBefore) {
        int year = Integer.parseInt(dateStr.substring(0, 4));
        int month = Integer.parseInt(dateStr.substring(5, 7)) - 1;
        int day = Integer.parseInt(dateStr.substring(8, 10)) - dateBefore;
        GregorianCalendar calendar = new GregorianCalendar(year, month, day);
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return bartDateFormat.format(calendar.getTime());
    }

    /**
     * 判断输入时期是否在起始日期和终止日期之间
     * @param theDate 被判断的日期，格式：yyyy-MM-dd
     * @param startDate 起始日期，格式：yyyy-MM-dd
     * @param endDate 终止日期，格式：yyyy-MM-dd
     * @return
     */
    public  static boolean isBetween(String theDate, String startDate,
                                     String endDate) {
        try {
            int _theDate = Integer.parseInt(dateStrConvert(theDate));
            int _startDate = Integer.parseInt(dateStrConvert(startDate));
            int _endDate = Integer.parseInt(dateStrConvert(endDate));
            return (_theDate >= _startDate && _theDate <= _endDate);
        } catch (Exception e) {
            e.printStackTrace();
            //      ReportError.reportException(0,"","判断日期出错",null);
            return false;
        }
    }

    /**
     * 计算两个日期间的天数
     */
    public   static int getDateDiff(String sDate1, String sDate2) {
        try {
            Date oDate1 = strToDateTime(sDate1, "yyyy-MM-dd");
            Date oDate2 = strToDateTime(sDate2, "yyyy-MM-dd");
            int iDays = (int) (Math.abs(oDate1.getTime() - oDate2.getTime()) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
            return iDays;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 计算两个日期间的天数,正数为前者日期大于后者，负数为前者日期小于后者
     */
    public   static int getDateDiff2(String sDate1, String sDate2) {
        try {
            Date oDate1 = strToDateTime(sDate1, "yyyy-MM-dd");
            Date oDate2 = strToDateTime(sDate2, "yyyy-MM-dd");
            int iDays = (int) ((oDate1.getTime() - oDate2.getTime()) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
            return iDays;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author huangfei
     */
    public static boolean isEffectiveDate(String nowTime, String startTime, String endTime) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date begin = sim.parse(startTime);
            Date end = sim.parse(endTime);
            Date nowdate = sim.parse(nowTime);
            long between1 = (nowdate.getTime() - begin.getTime())/1000;
            long between2 = (nowdate.getTime() - end.getTime())/1000;
            if(between1>0 && between2<0){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 获取时间Date格式返回 yyyy-MM-dd
     *
     * */
    public static Date getNowDate(){
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        try {
        Date date = sim.parse(sim.format(new Date()));
        return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取时间Date格式返回 yyyy-MM-dd HH:mm:ss
     *
     * */
    public static Date getNowDateTime(){
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sim.parse(sim.format(new Date()));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getTimeToDate(String time){
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sim.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Date getTimeToDateTime(String time){
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sim.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getNextDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        return date;
    }

    public static Date getBeforDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }
    public static Date getBeforDay(String format) {
        SimpleDateFormat sim = new SimpleDateFormat(format);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        try {
           return sim.parse(sim.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String dateTimeToStr(Date date) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return bartDateFormat.format(date);
    }
    public static String dateTimeToStrWithTime(Date date) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return bartDateFormat.format(date);
    }

    public static int getDateDiffer3(String str1, String str2){
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar c1= Calendar.getInstance();

        Calendar c2= Calendar.getInstance();

        try
        {
            c1.setTime(df.parse(str1));

            c2.setTime(df.parse(str2));

        }catch(ParseException e){
            System.err.println("格式不正确");
        }

        int result=c1.compareTo(c2);

        return result;
    }

    public static Date getBeforDay(String format, Date date, int days) {
        SimpleDateFormat sim = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        date = calendar.getTime();
        try {
            return sim.parse(sim.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 计算两个日期之间间隔的天数
    public static int getDateDiffer(Date big, Date small) {
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date oDate1 = df.parse(df.format(big));
            Date oDate2 = df.parse(df.format(small));
            int iDays = (int) ((oDate1.getTime() - oDate2.getTime()) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
            return iDays;
        } catch (Exception e) {
            return 0;
        }
    }

    //计算某一天之后的固定天数之后的日期
    public static Date getBeforDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获取时间Date格式返回 yyyy-MM-dd
     *  @param  date 待转换的日期
     *  @return Date 格式转换后的日期
     * */
    public static Date getFormatDate(Date date){
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sim.parse(sim.format(date));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description 获取当月的0点时间
     * @author fjj
     * @time 10:54 2018/11/9
     */
    public static Date getMouthFirstDay() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cale = Calendar.getInstance();
            cale.add(Calendar.MONTH, 0);
            cale.set(Calendar.DAY_OF_MONTH, 1);
            return format.parse(format.format(cale.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        //System.out.println(isEffectiveDate("2018-05-09 14:12:12","2018-05-09 07:12:12","2018-05-09 09:12:12"));
        //System.out.println(getDateDiff2(DateTimeUtil.getDateNow(),"2018-08-01"));

//        System.out.println(getDateNow());
//        System.out.println(getTimeNow());
//        System.out.println(DateTimeUtil.dateTimeToStrWithTime(new Date()));
//        String time="10:00:00";
//        System.out.println(getDateDiffer3(dateTimeToStrWithTime(new Date()),getDateNow()+" "+time));
        //System.out.println(dateTimeToStr(DateTimeUtil.getNowDate(),"yyyy-MM-dd"));
        //System.out.println(getDateDiffer3("2018-05-09 14:12:31","2018-05-09 14:12:12"));
        //System.out.println(DateTimeUtil.getDateBefore(DateTimeUtil.getDateNow(),1));
        //System.out.println(DateTimeUtil.getDateDiffer3(getDateTimeNow(),"2018-05-17 21:05:50"));
        //System.out.println(getBeforDay("yyyy-MM-dd HH:mm:ss"));
        //System.out.println(getTimeToDate(getDateBefore(getDateNow(),1)));
        //System.out.println(getBeforDay("yyyy-MM-dd",getTimeToDateTime("2018-06-28 12:03:59"),-2));
        System.out.println(getDateNow());
        System.out.println((5.3/2.0) > (1.0/3.0));
        System.out.println(10>8 && 10<15);
    }

}
