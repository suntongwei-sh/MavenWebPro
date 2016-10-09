package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {
	private static final String date_format_1 = "yyyy-MM-dd HH:mm:ss";
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

	public static DateFormat getDateFormat(String format) {
		DateFormat df = threadLocal.get();
		if (df == null) {
			df = new SimpleDateFormat(format);
			threadLocal.set(df);
		}
		return df;
	}

	/**
	 * 格式化
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String formatDate(Date date, String format) throws ParseException {
		return getDateFormat(format).format(date);
	}

	/**
	 * 解析
	 * 
	 * @param strDate
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String strDate, String format) throws ParseException {
		return getDateFormat(format).parse(strDate);
	}
}
