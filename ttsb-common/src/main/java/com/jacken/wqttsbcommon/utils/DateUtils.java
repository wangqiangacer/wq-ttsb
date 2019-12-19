package com.jacken.wqttsbcommon.utils;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 */

public final class DateUtils {

    transient static final Log logger = LogFactory.getLog(DateUtils.class);

    public static final Integer YEAR_START = 1;
    public static final Integer YEAR_END = 0;

    /**
     * yyyy-MM-dd
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";

    public static final String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";

    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String DISPLAY_FORMAT = "yyyy/MM/dd HH:mm";
    public static final String HOUR_FORMAT = "HH:mm";

    /**
     * 一毫秒
     */
    public static final long TIME_ONE_MILL_SECOND = 1;

    /**
     * 一秒
     */
    public static final long TIME_ONE_SECOND = 1000 * TIME_ONE_MILL_SECOND;

    /**
     * 一分钟
     */
    public static final long TIME_ONE_MINUTE = TIME_ONE_SECOND * 60;

    /**
     * 一小时
     */
    public static final long TIME_ONE_HOUR = TIME_ONE_MINUTE * 60;

    /**
     * 一天
     */
    public static final long TIME_ONE_DAY = TIME_ONE_HOUR * 24;

    private DateUtils() {
    }
    private final static ThreadLocal<DateFormat> formaterDate = new ThreadLocal<DateFormat>();

    private final static ThreadLocal<DateFormat> formaterDateTime = new ThreadLocal<DateFormat>();

    private final static ThreadLocal<DateFormat> formaterDateTimeNoSeparator = new ThreadLocal<DateFormat>();

    private final static ThreadLocal<DateFormat> formaterDateTimeNoSecond = new ThreadLocal<DateFormat>();

    private final static String ZEROZONE = "00:00:00-02:59:59";
    private final static String ONEZONE  = "03:00:00-05:59:59";
    private final static String TWOZONE =  "06:00:00-08:59:59";
    private final static String THREEZONE= "09:00:00-11:59:59";
    private final static String FOURZONE = "12:00:00-14:59:59";
    private final static String FIVEZONE   ="15:00:00-17:59:59";
    private final static String SIXZONE ="18:00:00-20:59:59";
    private final static String SEVENZONE ="21:00:00-23:59:59";

    /**
     * 获取线程安全的 SimpleDateFormat/yyyy-MM-dd
     *
     * @return
     */
    public static DateFormat getFormaterDate() {
        DateFormat formater = formaterDate.get();
        if (formater == null) {
            formater = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
            formaterDate.set(formater);
        }
        return formater;
    }

    public static String getDateZone(Date parmDate){

        String[] dates = new String[8];
        dates[0] = ZEROZONE;
        dates[1] = ONEZONE;
        dates[2] = TWOZONE;
        dates[3] = THREEZONE;
        dates[4] = FOURZONE;
        dates[5] = FIVEZONE;
        dates[6] = SIXZONE;
        dates[7] = SEVENZONE;
        for(int i=0;i<dates.length;i++){
           String dataStr = DateUtils.formatDate(parmDate,DATE_FORMAT_YYYY_MM_DD);
            String[] zoneDataStr = dates[i].split("-");
            Date startDate = DateUtils.parseDate(dataStr+" "+zoneDataStr[0],DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            Date endDate = DateUtils.parseDate(dataStr+" "+zoneDataStr[1],DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            if(parmDate.after(startDate) && parmDate.before(endDate)){
                return DateUtils.formatDate(endDate,DATE_FORMAT_YYYYMMDDHHMMSS);
            }
        }
        return null;
    }



    /**
     * 获取线程安全的 SimpleDateFormat/yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static DateFormat getFormaterDateTimer() {
        DateFormat formater = formaterDateTime.get();
        if (formater == null) {
            formater = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            formaterDateTime.set(formater);
        }
        return formater;
    }


    /**
     * 获取线程安全的 SimpleDateFormat/yyyyMMddHHmmss
     *
     * @return
     */
    public static DateFormat getFormaterDateTimeNoSeparator() {
        DateFormat formater = formaterDateTimeNoSeparator.get();
        if (formater == null) {
            formater = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
            formaterDateTimeNoSeparator.set(formater);
        }
        return formater;
    }

    /**
     * 获取线程安全的 SimpleDateFormat
     *
     * @return
     * @see #DATE_FORMAT_YYYY_MM_DD_HH_MM
     */
    public static DateFormat getFormaterDateTimeNoSecond() {
        DateFormat formater = formaterDateTimeNoSeparator.get();
        if (formater == null) {
            formater = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM);
            formaterDateTimeNoSecond.set(formater);
        }
        return formater;
    }

    /**
     * 获取startDate和endDate中的日期
     *
     * @param index
     * @param startDate
     * @param endDate
     * @return List<String> String :yyy-MM-dd
     */
    public static List<String> getDaysStringByDates(Date index, Date startDate, Date endDate) {
        Date date = index;
        List<String> list = new ArrayList<String>();
        if (date == null) {
            return Collections.<String>emptyList();
        }
        if (index != null && DateUtils.compareDate(startDate, date) < 0) {
            startDate = date;
        }
        for (; DateUtils.compareDate(startDate, date) >= 0 && DateUtils.compareDate(startDate, endDate) <= 0; ) {
            list.add(DateUtils.getFormaterDate().format(startDate));
            startDate = DateUtils.getAfterDayTime(startDate, 1);
        }
        return list;
    }

    public static List<Date> getDaysDateByDates(Date index, Date startDate, Date endDate) {
        Date date = index;
        List<Date> list = new ArrayList<Date>();
        if (date == null) {
            return Collections.<Date>emptyList();
        }
        if (index != null && DateUtils.compareDate(startDate, date) < 0) {
            startDate = date;
        }
        for (; DateUtils.compareDate(startDate, date) >= 0 && DateUtils.compareDate(startDate, endDate) <= 0; ) {
            list.add(startDate);
            startDate = DateUtils.getAfterDayTime(startDate, 1);
        }
        return list;
    }

    /**
     * 字符串转换为日期格式
     *
     * @param dateString
     * @param pattern
     * @return
     */
    public static Date parseDate(String dateString, String pattern) {
        try {
            return getFormat(pattern).parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当月开始时间
     * @return
     */
    public static Date dayStartOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date monthStart = calendar.getTime();
        return monthStart;
    }

    /**
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        try {
            if (date == null) {
                return StringUtils.EMPTY;
            }
            return getFormat(pattern).format(date);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 获得指定格式的当前日期
     *
     * @param pattern
     * @return
     */
    public static String formatCurrentDate(String pattern) {
        return getFormat(pattern).format(new Date());
    }

    /**
     * 获取 DateFormat
     *
     * @param pattern
     * @return
     */
    public static DateFormat getFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 取得某时间后n天,格式为yyyy-mm-dd
     *
     * @param date
     * @param n
     * @return
     */
    public static String getAfterDays(Date date, int n) {
        Date d = getAfterDays(date, Calendar.DAY_OF_MONTH, n);
        return getFormaterDate().format(d);
    }

    /**
     * 取得某时间前n天,格式为yyyy-mm-dd
     *
     * @param date
     * @param n
     * @return
     */
    public static String getBeforeDays(Date date, int n) {
        Date d = getAfterDays(date, Calendar.DAY_OF_MONTH, -n);
        return getFormaterDate().format(d);
    }

    /**
     * @param date
     * @param n
     * @return
     */
    public static Date getBeforeDay(Date date, int n) {
        return getAfterDays(date, Calendar.DAY_OF_MONTH, -n);
    }

    /**
     * 获取日期,
     *
     * @param date
     * @param type
     * @return
     * @see Calendar
     * @see Calendar#YEAR
     * @see Calendar#MONTH
     * @see Calendar#DAY_OF_MONTH
     * @see Calendar#HOUR_OF_DAY
     * @see Calendar#MINUTE
     * @see Calendar#SECOND
     * @see Calendar#MILLISECOND
     */
    public static Date getAfterDays(Date date, int type, int amount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(type, amount);
        return c.getTime();
    }

    /**
     * 计算两段日期之间的天数
     *
     * @param beginDate
     * @param endDate
     * @param amount
     * @return
     * @see Calendar#DATE
     * @see Calendar#HOUR
     * @see Calendar#MINUTE
     * @see Calendar#SECOND
     * @see Calendar#MILLISECOND
     */
    public static long getDaysByDate(Date beginDate, Date endDate, int amount) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("argu is ellegal,beginDate or endDate is null");
        }
        long times = endDate.getTime() - beginDate.getTime();
        long result = 0;
        switch (amount) {
            case Calendar.MILLISECOND:
                result = times;
                break;
            case Calendar.SECOND:
                result = times / 1000;
                break;
            case Calendar.MINUTE:
                result = times / TIME_ONE_MINUTE;
                break;
            case Calendar.HOUR:
                result = times / TIME_ONE_HOUR;
                break;
            case Calendar.DATE:
                result = times / TIME_ONE_DAY;
                break;
            default:
                throw new IllegalArgumentException("argu is ellegal,amount is error.");
        }
        return result;
    }

    /**
     * 计算两段日期之间的天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long getDaysByDate(Date beginDate, Date endDate) {
        long days = 0;
        if (beginDate == null || endDate == null) {
            return days;
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(beginDate);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(endDate);

        long l1 = c1.getTimeInMillis();
        long l2 = c2.getTimeInMillis();
        // 计算天数
        days = (l2 - l1) / TIME_ONE_DAY;
        return days;
    }

    /**
     * 比较日期大小，dt1>dt2返回1，dt1=dt2返回0，dt1<dt2返回-1
     *
     * @param dt1
     * @param dt2
     * @return
     */
    public static int compareDate(Date dt1, Date dt2) {
        if (dt1.getTime() > dt2.getTime()) {
            return 1;
        } else if (dt1.getTime() == dt2.getTime()) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 根据日期返回星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 返回java code里面的星期
     *
     * @param date
     * @return
     */
    public static Integer getWeekOfDateSource(Date date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 年月日
     *
     * @param date
     * @return
     */
    public static Date getDate4YMD(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    // 得到日期的星期
    public static String getDayOfWeek(Date date) {
        String str = StringUtils.EMPTY;
        if (date == null) {
            return str;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_WEEK);
        return StringUtils.EMPTY + day;
    }

    /**
     * 得到日期的星期 汉子
     *
     * @param date
     * @return
     */
    public static String getDayOfWeek(String date) {
        String str = StringUtils.EMPTY;
        Calendar c = parseCalendar(date);
        if (c == null) {
            return str;
        }

        int day = c.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                str = "日";
                break;
            case Calendar.MONDAY:
                str = "一";
                break;
            case Calendar.TUESDAY:
                str = "二";
                break;
            case Calendar.WEDNESDAY:
                str = "三";
                break;
            case Calendar.THURSDAY:
                str = "四";
                break;
            case Calendar.FRIDAY:
                str = "五";
                break;
            case Calendar.SATURDAY:
                str = "六";
                break;
            default:
                str = StringUtils.EMPTY;
        }
        return str;
    }

    /**
     * @param strDate yyyy-MM-dd
     * @return
     */
    public static Calendar parseCalendar(String strDate) {
        Calendar c = null;
        Date d = null;
        try {
            d = getFormaterDate().parse(strDate);
        } catch (ParseException e) {
            logger.warn(e);
        }
        if (d != null) {
            c = Calendar.getInstance();
            c.setTime(d);
        }
        return c;
    }

    /**
     * 获取某时间后n天的时间
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getAfterDayTime(Date date, int n) {
        return getAfterDays(date, Calendar.DAY_OF_MONTH, n);
    }

    /**
     * 获取指定时间的上一天
     *
     * @param time
     * @return 2012-3-16
     * @author dqzhai
     */
    public static Date returnPreviousDay(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        calendar.get(Calendar.DAY_OF_MONTH);
        return calendar.getTime();
    }

    /**
     * 获取指定时间的下一天
     *
     * @param time
     * @return 2012-3-16
     * @author dqzhai
     */
    public static Date getNextDay(Date time) {
        return getAfterDayTime(time, 1);
    }

    /**
     * 日期间隔数
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 日期间隔数
     */
    public static int getDiffDate(Date beginTime, Date endTime) {
        return (int) ((beginTime.getTime() - endTime.getTime()) / TIME_ONE_DAY);
    }

    /**
     * 返回两个日期相差的天数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 两个日期相差的天数
     * @throws ParseException
     */
    public static long getDistDates(Date startDate, Date endDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(startDate);
        long time0 = aCalendar.getTimeInMillis();
        aCalendar.setTime(endDate);
        long time1 = aCalendar.getTimeInMillis();
        return (time1 - time0) / (TIME_ONE_DAY);
    }

    /**
     * 获取两个时间相差的秒
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getDistDatesBySecond(Date startTime, Date endTime) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(startTime);
        long time0 = aCalendar.getTimeInMillis();
        aCalendar.setTime(endTime);
        long time1 = aCalendar.getTimeInMillis();
        return (time1 - time0) / (TIME_ONE_SECOND);
    }

    /**
     * 计算当前月有多少天
     *
     * @param
     * @see
     */
    public static int getDateMonthByDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    /**
     * 获取开始和结束日期之间的所有日期
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(beginDate);// 把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);// 把结束时间加入集合
        return lDate;
    }

    /**
     * 日期是否有交集
     *
     * @param leftStartDate
     * @param leftEndDate
     * @param rightStartDate
     * @param rightEndDate
     * @return
     */
    public static boolean isOverlap(Date leftStartDate, Date leftEndDate, Date rightStartDate, Date rightEndDate) {
        return ((leftStartDate.getTime() >= rightStartDate.getTime())
                && leftStartDate.getTime() <= rightEndDate.getTime())
                || ((rightStartDate.getTime() >= leftStartDate.getTime())
                && rightStartDate.getTime() <= leftEndDate.getTime());

    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param date1 date1
     * @param date2 date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 由过去的某一时间,计算距离当前的时间
     */
    public static String calculateTime(Date setTime) {
        if (null == setTime) {
            return null;
        }
        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
        String msg = null;

        long reset = setTime.getTime(); // 获取指定时间的毫秒数
        long dateDiff = nowTime - reset;

        if (dateDiff < 0) {
            msg = formatYMDHM(new Date());
        } else {

            long dateTemp1 = dateDiff / 1000; // 秒
            long dateTemp2 = dateTemp1 / 60; // 分钟
            long dateTemp3 = dateTemp2 / 60; // 小时
            long dateTemp4 = dateTemp3 / 24; // 天数
            long dateTemp5 = dateTemp4 / 30; // 月数
            long dateTemp6 = dateTemp5 / 12; // 年数

            if (dateTemp6 > 0 || dateTemp5 > 0 || dateTemp4 > 0 || !isSameDate(setTime, new Date())) {
                msg = formatYMDHM(setTime);

            } else if (dateTemp3 > 0) {
                double dt = (double) dateTemp2 / 60;
                double dt2 = (double) dateTemp2 % 60;

                // System.err.println(dt2 + "=====" + dt);
                msg = String.format("%.0f小时", Math.floor(dt));
                if (Math.floor(dt2) >= 1) {
                    msg += String.format("%.0f分钟", Math.floor(dt2));
                }
                msg += "前";
            } else if (dateTemp2 > 0) {
                msg = dateTemp2 + "分钟前";

            } else if (dateDiff >= 0) {
                msg = "刚刚";
            }
        }
        return msg;

    }

    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DATE, amount);
    }

    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }

    /**
     * 格式化年月日
     *
     * @param date
     * @return
     */
    public static String formatYMD(Date date) {
        return formatDate(date, DATE_FORMAT_YYYY_MM_DD);
    }

    /**
     * 格式化年月日小时分钟
     *
     * @param date
     * @return
     */
    public static String formatYMDHM(Date date) {
        return formatDate(date, DATE_FORMAT_YYYY_MM_DD_HH_MM);
    }

    /**
     * 格式化年月日小时分钟秒
     *
     * @param date
     * @return
     */
    public static String formatYMDHMS(Date date) {
        return formatDate(date, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public static Date getDateStart(Date date) {
        try {
            if (date == null) {
                return null;
            }
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            now.set(Calendar.HOUR_OF_DAY, 0);
            now.set(Calendar.MINUTE, 0);
            now.set(Calendar.SECOND, 0);

            return now.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getDateEnd(Date date) {
        try {
            if (date == null) {
                return null;
            }
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            now.set(Calendar.HOUR_OF_DAY, 23);
            now.set(Calendar.MINUTE, 59);
            now.set(Calendar.SECOND, 59);

            return now.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据间隔数推算日期
     *
     * @param interval
     * @return
     */
    public static Map<String, String> getFromAndTo(int interval, int unit) {
        Calendar calendar = Calendar.getInstance();
        String now = String.valueOf(calendar.getTime().getTime());
        calendar.add(unit, interval);
        String result = String.valueOf(calendar.getTime().getTime());
        Map<String, String> map = new HashMap<>();
        map.put("now", now);
        map.put("result", result);
        return map;
    }

    public static String parseTimestamp(String timstamp) {
        Date date = new Date(Long.parseLong(timstamp));
        return formatDate(date, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public static String parseTimestampByFormat(String timstamp, String format) {
        Date date = new Date(Long.parseLong(timstamp));
        return formatDate(date, format);
    }

    public static Long getCurrentDayTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static Long getCurrentMonthTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 根据生日获取年龄
     *
     * @param birthday
     * @return
     */
    public static int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return 0;
        }
    }

    /**
     * 根据年龄获取出生日期
     *
     * @param age
     * @param key
     * @return
     */
    public static Date getBirthTime(Integer age, Integer key) {
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.YEAR, 0 - age);

        if (Objects.equals(key, YEAR_END)) {
            c.set(Calendar.MONTH, 11);//十二月
            c.set(Calendar.DATE, 31);
            c.set(Calendar.HOUR, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
        } else {
            c.set(Calendar.MONTH, 0);//一月
            c.set(Calendar.DATE, 1);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
        }

        return c.getTime();
    }

//    public static void main(String[] args) {
//        System.out.println(getCurrentMonthTimestamp());
//    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
