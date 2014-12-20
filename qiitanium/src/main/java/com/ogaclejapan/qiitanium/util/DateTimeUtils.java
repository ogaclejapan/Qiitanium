package com.ogaclejapan.qiitanium.util;

import android.text.format.DateUtils;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

public final class DateTimeUtils {

    private static final String TIMEAGO_JUST_NOW = "just now";
    private static final String TIMEAGO_HOURS_AGO = "%d hours ago";
    private static final String TIMEAGO_MINUTES_AGO = "%d mins ago";

    public static Date parse3339(String time) {
        final Time t = new Time();
        t.parse3339(time);
        return new Date(t.toMillis(false));
    }

    public static Date parse(String dateFormat, String dateString) {
        return parse(new SimpleDateFormat(dateFormat), dateString);
    }

    public static Date parse(SimpleDateFormat dateFormat, String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException pe) {
            Timber.e(pe, "Failed to date parse: %s", dateString);
            return null;
        }
    }

    public static String format(Date d, String dateFormat) {
        final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(d);
    }

    public static String timeAgo(Date d) {
        final long referenceTime = d.getTime();
        final long nowTime = System.currentTimeMillis();
        final long diffTime = Math.abs(nowTime - referenceTime);
        if (diffTime > DateUtils.DAY_IN_MILLIS) {
            return format(d, "yyyy-MM-dd HH:mm");
        } else if (diffTime > DateUtils.HOUR_IN_MILLIS) {
            return String.format(TIMEAGO_HOURS_AGO, diffTime / DateUtils.HOUR_IN_MILLIS);
        } else if (diffTime > DateUtils.MINUTE_IN_MILLIS) {
            return String.format(TIMEAGO_MINUTES_AGO, diffTime / DateUtils.MINUTE_IN_MILLIS);
        } else {
            return TIMEAGO_JUST_NOW;
        }
    }

    private DateTimeUtils() {
        //No instances
    }

}
