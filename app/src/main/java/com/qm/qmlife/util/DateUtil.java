package com.qm.qmlife.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 功能描述：
 *
 * @author Administrator
 * @version 1.0
 * @Date Jul 19, 2008
 * @Time 9:47:53 AM
 */
public class DateUtil {

    public static Date date = null;

    public static DateFormat dateFormat = null;

    public static Calendar calendar = null;

    /**
     * 判断当前时间是否与传入时间为同一天
     */
    public static boolean compareTime(String lastTime) {
        String nowTime = getMD();
        return nowTime.equals(lastTime);
    }

    /**
     * 比较两个日期的差
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean compareDate(Date a, Date b) {
        long interval = (b.getTime() - a.getTime()) / 1000;
        if (interval >= 1) {//超过1s超时
            return true;
        } else {
            return false;
        }
    }

    /**
     * 采集经纬度日期
     *
     * @return
     */
    public static boolean isLocationDate() {
        int day = DateUtil.getDay(new Date());
        return day == 1 || day == 2 || day == 3;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            dateFormat = new SimpleDateFormat(format);
            String dt = dateStr.replaceAll("-", "/");
            if ((!dt.equals("")) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]", "0");
            }
            date = (Date) dateFormat.parse(dt);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 航空发件批次号拼接格式
     * 00:00~08:00计为前一天的日期
     * 08:00~24:00计为当天的日期
     *
     * @return String "yyyyMMdd"格式的日期
     */
    public static String getYYYYMMDD() {
        String yyMMDD = "";
        String monS = "";
        String dayS = "";
        String hourS = "";
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, -1);//天数日期-1（前一天）
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DATE);
        int hour_of_day = ca.get(Calendar.HOUR_OF_DAY);//HOUR_OF_DAY 24小时制

        if (month < 10) {
            monS = "0" + month;
        } else {
            monS = month + "";
        }
        if (day < 10) {
            dayS = "0" + day;
        } else {
            dayS = day + "";
        }
        if (hour_of_day < 8) {//08:00之前的计为前一天
            yyMMDD = year + monS + dayS;
        } else {
            yyMMDD = new SimpleDateFormat("yyyyMMdd").format(new Date());
        }
        return yyMMDD;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期：YYYY-MM-DD 格式
     * @return Date
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy/MM/dd");
    }

    // /**
    // * 功能描述：格式化日期
    // *
    // * @param dateStr
    // * String 字符型日期：YYYY-MM-DD 格式
    // * @return Date
    // */
    // public static Date parsesDateTime(String dateStr){
    // return parseDate(dateStr, "yyyy-MM-dd");
    // }

    /**
     * 功能描述：格式化输出日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                dateFormat = new SimpleDateFormat(format);
                result = dateFormat.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 功能描述：
     *
     * @param date Date 日期
     */
    public static String format(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 功能描述：返回年份
     *
     * @param date Date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回月份
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日份
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前的年月日
     */
    public static String getYMD() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(now);
        // calendar = Calendar.getInstance();
        // return calendar.get(Calendar.YEAR) + "-"
        // + (calendar.get(Calendar.MONTH) + 1) + "-"
        // + calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 是否需要弹框条件
     * @return
     */
    public static Boolean isNeedDialogForJBH(){
        String ymd = getYMD();
        if ("2018-10-29".equals(ymd)||"2018-10-30".equals(ymd)||"2018-10-31".equals(ymd)||"2018-11-01".equals(ymd)||"2018-11-02".equals(ymd)||"2018-11-03".equals(ymd)||"2018-11-04".equals(ymd)||"2018-11-05".equals(ymd)||"2018-11-06".equals(ymd)||"2018-11-07".equals(ymd)||"2018-11-08".equals(ymd)||"2018-11-09".equals(ymd)){
            return true;
        }
        return false;
    }

    /**
     * 获取当前的月日
     */
    public static String getMD() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");
        return dateFormat.format(now);
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);

            if (dt1.getTime() >= dt2.getTime()) {
                isBigger = true;
            } else if (dt1.getTime() < dt2.getTime()) {
                isBigger = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isBigger;
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能描述：返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期 yyyy-MM-dd 格式
     */
    public static String getDate(Date date) {
        return format(date, "yyy-MM-dd");
    }

    /**
     *
     * @param date
     * @return
     */
    public static String getMD(Date date) {
        return format(date, "MM月dd日");
    }

    /**
     * 功能描述：返回字符型时间
     *
     * @param date Date 日期
     * @return 返回字符型时间 HH:mm:ss 格式
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 功能描述：返回字符型日期时间
     *
     * @param date Date 日期
     * @return 返回字符型日期时间 yyyy/MM/dd HH:mm:ss 格式
     */
    public static String getDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getFullTime(Date date) {
        String week = getWeekOfDate(date);
        String time = format(date, "yyyy-MM-dd HH:mm:ss");
        return time + " " + week;
    }

    /**
     * 获取对应的星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];

    }

    /**
     * 功能描述：日期相加
     *
     * @param date Date 日期
     * @param day  int 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 功能描述：日期相减
     *
     * @param date Date 日期
     * @return 返回相减后的日期
     */
    public static Date diffDate(Date date, int day) {
        calendar = Calendar.getInstance();
        long millis = getMillis(date) - ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 功能描述：日期相减
     *
     * @param date   Date 日期
     * @param minute int  分钟
     * @return 返回相减后的日期
     */
    public static Date diffDateMinute(Date date, int minute) {
        calendar = Calendar.getInstance();
        long millis = getMillis(date) - ((long) minute) * 60 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 功能描述：取得指定月份的第一天
     *
     * @param strdate String 字符型日期
     * @return String yyyy-MM-dd 格式
     */
    public static String getMonthBegin(String strdate) {
        date = parseDate(strdate);
        return format(date, "yyyy-MM") + "-01";
    }

    /**
     * 功能描述：取得指定月份的最后一天
     *
     * @param strdate String 字符型日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String getMonthEnd(String strdate) {
        date = parseDate(getMonthBegin(strdate));
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime());
    }

    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     * 功能描述：以指定的格式来格式化日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return String 日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String getFormatNowTime() {
        Date date = new Date();

        return getDateTime(date);
    }

    /**
     * 通过和今日的天数差异获得date
     *
     * @return date
     */
    public static Date getDate(int count) {
        Calendar d = Calendar.getInstance();
        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH);
        int day = d.get(Calendar.DAY_OF_MONTH);
        d.set(year, month, day + count);
        Date date = d.getTime();
        return date;
    }

    /**
     * 将日期格式化
     * 、
     */
    public static String getDate(String date) {
        if (date == null) return "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");        // 实例化模板对象
        Date d = null;
        try {
            d = sdf1.parse(date);   // 将给定的字符串中的日期提取出来
        } catch (Exception e) {            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace();       // 打印异常信息
        }
        return sdf2.format(d);
    }

    public static long getLong(String sDt) {
        long lTime = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//1399906892
            Date dt2 = sdf.parse(sDt);
            //继续转换得到秒数的long型
            lTime = dt2.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lTime;
    }

    public static long getLong2(String sDt) {
        long lTime = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//1399906892
            Date dt2 = sdf.parse(sDt);
            //继续转换得到秒数的long型
            lTime = dt2.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lTime;
    }

    /**
     * CST时间转标准时间
     *
     * @param cstTime cst格式时间
     * @return
     */
    public static String switchCSTTimeToStandard(String cstTime) {
        String dateStr = "Thu May 26 14:41:46 CST 2016";//CST格式时间
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date date = null;
        try {
            date = (Date) sdf.parse(cstTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return formatStr2;
    }
    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param dateStart
     * @param dateEnd
     * @return
     */
    public static int differentDaysByMillisecond(String dateStart,String dateEnd){
        int day = 0;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dateStarts =format.parse(dateStart);
            Date dateEnds = format.parse(dateEnd);

            day= differentDays(dateStarts,dateEnds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day+1;
    }
    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }
}
