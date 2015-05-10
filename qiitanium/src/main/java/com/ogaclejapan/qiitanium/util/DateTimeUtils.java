package com.ogaclejapan.qiitanium.util;

import android.text.format.DateUtils;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public final class DateTimeUtils {

  private static final String TIMEAGO_JUST_NOW = "just now";
  private static final String TIMEAGO_DAYS_AGO = "%d days ago";
  private static final String TIMEAGO_HOURS_AGO = "%d hours ago";
  private static final String TIMEAGO_MINUTES_AGO = "%d mins ago";
  private static final String TIMEFORMAT_DEFAULT = "yyyy-MM-dd";

  private DateTimeUtils() {}

  public static Date parse3339(String time) {
    final Time t = new Time();
    t.parse3339(time);
    return new Date(t.toMillis(false));
  }

  public static Date parse(String dateFormat, String dateString) {
    return parse(new SimpleDateFormat(dateFormat, Locale.getDefault()), dateString);
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
    final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
    return sdf.format(d);
  }

  public static String timeAgo(Date d) {
    final long referenceTime = d.getTime();
    final long nowTime = System.currentTimeMillis();
    final long diffTime = Math.abs(nowTime - referenceTime);
    if (diffTime > DateUtils.WEEK_IN_MILLIS) {
      return format(d, TIMEFORMAT_DEFAULT);
    } else if (diffTime > DateUtils.DAY_IN_MILLIS) {
      return String.format(TIMEAGO_DAYS_AGO, diffTime / DateUtils.DAY_IN_MILLIS);
    } else if (diffTime > DateUtils.HOUR_IN_MILLIS) {
      return String.format(TIMEAGO_HOURS_AGO, diffTime / DateUtils.HOUR_IN_MILLIS);
    } else if (diffTime > DateUtils.MINUTE_IN_MILLIS) {
      return String.format(TIMEAGO_MINUTES_AGO, diffTime / DateUtils.MINUTE_IN_MILLIS);
    } else {
      return TIMEAGO_JUST_NOW;
    }
  }

}
