/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wikift.common.utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Calendar.*;

/**
 * CalendarUtils <br/>
 * 描述 : CalendarUtils <br/>
 * 作者 : qianmoQ <br/>
 * 版本 : 1.0 <br/>
 * 创建时间 : 2018-02-06 上午10:26 <br/>
 * 联系作者 : <a href="mailTo:shichengoooo@163.com">qianmoQ</a>
 */
public class CalendarUtils {

    private static String FIRST = "first";
    private static String LAST = "last";

    public static Map<String, Date> getCurrentYearFirstAndLastDate() {
        Map<String, Date> map = new ConcurrentHashMap<>();
        Calendar calendar = getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        map.put(FIRST, calendar.getTime());
        calendar.add(YEAR, 1);
        calendar.set(DAY_OF_YEAR, -1);
        map.put(LAST, calendar.getTime());
        calendar.clear();
        return map;
    }

    public static List<Date> getRangerAllDays(){
        return getRangerAllDays(getCurrentYearFirstDay(), getCurrentYearLastDay());
    }

    public static List<Date> getRangerAllDays(Date startDate, Date endDate) {
        Calendar start = getInstance();
        start.setTime(startDate);
        start.set(DAY_OF_YEAR, 0);
        Calendar end = getInstance();
        end.setTime(endDate);
        List<Date> yearDays = new ArrayList<>();
        while (endDate.after(start.getTime())) {
            start.add(DAY_OF_YEAR, 1);
            yearDays.add(start.getTime());
        }
        return yearDays;
    }

    /**
     * 获取当年第一天
     *
     * @return 当年第一天
     */
    public static Date getCurrentYearFirstDay() {
        Calendar current = getInstance();
        int currentYear = current.get(YEAR);
        return getYearFirstDay(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirstDay(int year) {
        Calendar calendar = getInstance();
        calendar.clear();
        calendar.set(YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获取当年的最后一天
     *
     * @return 当年最后一天
     */
    public static Date getCurrentYearLastDay() {
        Calendar current = Calendar.getInstance();
        int currentYear = current.get(Calendar.YEAR);
        return getYearLastDay(currentYear);
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLastDay(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

}
