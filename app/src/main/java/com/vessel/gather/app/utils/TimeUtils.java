package com.vessel.gather.app.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeUtils {
    public final static String mMdFormat = "M月d日";
    public static final String[] s_weekOfDays = {"星期日", "星期一", "星期二", "星期三",
            "星期四", "星期五", "星期六"};
    public static final String[] s_weekOfDays_2 = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    public final static String YYYYnianMMyueDDHHMM = "yyyy年MM月dd日 HH:mm";
    public final static String MD_HHMM = "M/d HH:mm";
    public final static String MMyueDD = "MM月dd日";
    public final static String mFormat = "yyyy-MM-dd";
    public final static String YYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    public final static String mFormat_n = "yyyyMMdd";
    public final static String mFormat_chattime = "yy-MM-dd";
    public final static String YYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public final static String HHMM = "HH:mm";
    public final static String mHourTimeFormat = "kk:mm";
    public final static String FORMAT_HHmm = "HH:mm";
    public final static String myesterday = "昨天";
    public final static String YYYYnianMMyueDD = "yyyy年MM月dd日";
    public final static String ENGLISH_MMMddYYYY = "MMM dd, yyyy";//Apr 25, 2016


    public final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * 获取给定日期所在月的自然周
     *
     * @param date
     * @return
     */
    public static int getWeekOfMonth(Date date) {
        if (date == null) return 0;
        LocalDate ldb = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        AtomicInteger count = new AtomicInteger(0);
        int days = Math.toIntExact(ldb.range(ChronoField.DAY_OF_MONTH).getMaximum());
        AtomicInteger atomicDays = new AtomicInteger(days);
        LocalDate firstWeek = ldb.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.SUNDAY));
        LocalDate lastWeek = ldb.with(TemporalAdjusters.dayOfWeekInMonth(-1, DayOfWeek.SUNDAY));
        if ((DayOfWeek.SUNDAY.getValue() - firstWeek.getDayOfMonth()) > 0) {
            count.incrementAndGet();
            atomicDays.addAndGet((~firstWeek.getDayOfMonth() + 1));
        }
        if (lastWeek.getDayOfMonth() < days) {
            count.incrementAndGet();
            atomicDays.addAndGet(~Math.subtractExact(days, lastWeek.getDayOfMonth()) + 1);
        }
        if (Math.floorMod(atomicDays.get(), 7) == 0) {
            count.accumulateAndGet(atomicDays.accumulateAndGet(7, Math::floorDiv), Math::addExact);
        }
        return count.get();
    }


    /**
     * 给定一个日期，返回该日期所在周的开始和结束日期
     *
     * @param date
     * @return
     */
    public static Map<DatePointEnum, Date> getDateIntervalByWeek(Date date) {
        if (date == null) return Collections.emptyMap();
        ZoneId zoneId = ZoneId.systemDefault();
        HashMap<DatePointEnum, Date> dateHashMap = new HashMap<>();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ldb = getSunday(localDate);
        int currentDay = ldb.getDayOfMonth();
        int days = Math.toIntExact(ldb.range(ChronoField.DAY_OF_MONTH).getMaximum());
        DayOfWeek week = ldb.getDayOfWeek();
        LocalDate startDate = null, endDate = null;
        if (week == DayOfWeek.SUNDAY) {
            if (currentDay - 6 <= 1) {
                startDate = ldb.with(TemporalAdjusters.firstDayOfMonth());
            } else {
                startDate = ldb.minusDays(6);
            }
            endDate = ldb;
        }
        if (DayOfWeek.SUNDAY != week && currentDay == days) {
            startDate = ldb.minusDays(week.getValue() - 1);
            endDate = ldb.with(TemporalAdjusters.lastDayOfMonth());
        }

        if (startDate != null && endDate != null) {
            dateHashMap.put(DatePointEnum.START, Date.from(startDate.atStartOfDay(zoneId).toInstant()));
            dateHashMap.put(DatePointEnum.END, Date.from(endDate.atStartOfDay(zoneId).toInstant()));
        }
        return dateHashMap;
    }

    private static LocalDate getSunday(LocalDate localDate) {
        LocalDate result = localDate.with(TemporalAdjusters.ofDateAdjuster(ldb -> ldb.minusDays(ldb.getDayOfWeek().getValue() - DayOfWeek.SUNDAY.getValue())));
        if (result.getMonthValue() > localDate.getMonthValue()) {
            result = localDate.with(TemporalAdjusters.lastDayOfMonth());
        }
        return result;
    }


    public static List<Map<DatePointEnum, Date>> getMonthOfWeekInterval(Date date) throws ParseException {
        if (date == null) return Collections.EMPTY_LIST;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<DatePointEnum, Date>> result = new ArrayList<>();
        LocalDate ldb = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long days = ldb.range(ChronoField.DAY_OF_MONTH).getMaximum();
        String mStr = String.valueOf(ldb.getMonthValue());
        if (ldb.getMonthValue() < 10) {
            mStr = "0" + mStr;
        }
        String datePrefix = ldb.getYear() + "-" + mStr;
        for (int i = 1; i <= days; i++) {
            Map<DatePointEnum, Date> map = new HashMap<>();
            String startDate = "", endDate = "";
            String dayStr = String.valueOf(i);
            if (i < 10) {
                dayStr = "0" + i;
            }
            LocalDate localDate = LocalDate.parse(datePrefix + "-" + dayStr);
            DayOfWeek k = localDate.getDayOfWeek();
            if (DayOfWeek.SUNDAY == k) {
                if (i - 6 <= 1) {
                    startDate = datePrefix + "-" + 1;
                } else {
                    startDate = datePrefix + "-" + (i - 6);
                }
                endDate = datePrefix + "-" + i;
            }
            if (DayOfWeek.SUNDAY != k && i == days) {
                startDate = datePrefix + "-" + (i - k.getValue() + 1);
                endDate = datePrefix + "-" + days;
            }
            if (!"".equals(startDate) && !"".equals(endDate)) {
                map.put(DatePointEnum.START, dateFormat.parse(startDate));
                map.put(DatePointEnum.END, dateFormat.parse(endDate));
                result.add(map);
            }
        }
        return result;
    }


    /**
     * 获取日期
     *
     * @param unix_timestamp
     * @return
     */
    public static String getDate(long unix_timestamp) {
        Date dt = new Date(unix_timestamp);
        return (String) android.text.format.DateFormat.format(mFormat, dt);
    }

    public static String getSysMonth(long unix_timestamp) {
        long monthLong =  30L*24L*60L*60L*1000L;
        long data = System.currentTimeMillis() - unix_timestamp;
        long num = data / monthLong;
        return num+" 月";
    }

    /**
     * 将Date类型转换为对应格式的String类型
     *
     * @param dt
     * @param inFormat
     * @return
     */
    public static String getFormatDate(Date dt, CharSequence inFormat) {
        return (String) android.text.format.DateFormat.format(inFormat, dt);
    }

    enum DatePointEnum {
        START, END
    }
}