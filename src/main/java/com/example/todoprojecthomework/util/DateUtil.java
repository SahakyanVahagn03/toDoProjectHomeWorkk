package com.example.todoprojecthomework.util;

import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class DateUtil {
    private final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public String dateToString(Date date) {
        return SIMPLE_DATE_FORMAT.format(date);
    }

    public Date stringToDate(String dateStr) throws ParseException {
        return SIMPLE_DATE_FORMAT.parse(dateStr);
    }

}
