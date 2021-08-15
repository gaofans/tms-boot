package com.bettem.tms.boot.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

	/**
	 * 时间格式化格式.
	 */
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 文件命名时间格式化
	 */
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
	public static SimpleDateFormat sf2 = new SimpleDateFormat("yyyy/MM/dd H:mm");
	public static SimpleDateFormat sf3 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 特定的方式计算出的年月日周返回该实体
	 */
	public static class DateDetail {
		private Integer dateOfYmd;
		private Integer dateOfYear;
		private Integer dateOfQuarter;
		private Integer dateOfMonth;
		private Integer dateOfYearMonth;
		private Integer dateOfWeek;
		private Integer dateOfWeekw;
		private Integer dateOfWeekm;
		private Calendar calendar;
		private String dateOfYmdStr;
		private String nowWeekDate;  // 星期几对应的日期
		private String nowWeekName;  // 星期几的名称

		public Integer getDateOfYmd() {
			return dateOfYmd;
		}

		public void setDateOfYmd(Integer dateOfYmd) {
			this.dateOfYmd = dateOfYmd;
		}

		public Integer getDateOfYear() {
			return dateOfYear;
		}

		public void setDateOfYear(Integer dateOfYear) {
			this.dateOfYear = dateOfYear;
		}

		public Integer getDateOfQuarter() {
			return dateOfQuarter;
		}

		public void setDateOfQuarter(Integer dateOfQuarter) {
			this.dateOfQuarter = dateOfQuarter;
		}

		public Integer getDateOfMonth() {
			return dateOfMonth;
		}

		public void setDateOfMonth(Integer dateOfMonth) {
			this.dateOfMonth = dateOfMonth;
		}

		public Integer getDateOfYearMonth() {
			return dateOfYearMonth;
		}

		public void setDateOfYearMonth(Integer dateOfYearMonth) {
			this.dateOfYearMonth = dateOfYearMonth;
		}

		public Integer getDateOfWeek() {
			return dateOfWeek;
		}

		public void setDateOfWeek(Integer dateOfWeek) {
			this.dateOfWeek = dateOfWeek;
		}

		public Integer getDateOfWeekw() {
			return dateOfWeekw;
		}

		public void setDateOfWeekw(Integer dateOfWeekw) {
			this.dateOfWeekw = dateOfWeekw;
		}

		public Integer getDateOfWeekm() {
			return dateOfWeekm;
		}

		public void setDateOfWeekm(Integer dateOfWeekm) {
			this.dateOfWeekm = dateOfWeekm;
		}

		public Calendar getCalendar() {
			return calendar;
		}

		public void setCalendar(Calendar calendar) {
			this.calendar = calendar;
		}

		public String getDateOfYmdStr() {
			return dateOfYmdStr;
		}

		public void setDateOfYmdStr(String dateOfYmdStr) {
			this.dateOfYmdStr = dateOfYmdStr;
		}

		public String getNowWeekDate() {
			return nowWeekDate;
		}
		public void setNowWeekDate(String nowWeekDate) {
			this.nowWeekDate = nowWeekDate;
		}
		public String getNowWeekName() {
			return nowWeekName;
		}
		public void setNowWeekName(String nowWeekName) {
			this.nowWeekName = nowWeekName;
		}
	}

	public static Date stringFormatDate(String key) throws ParseException {
		Date date = null;
		if (StringUtils.isEmpty(key)) {
			date = sf.parse(key);
		}
		return date;
	}

	public static Date stringFormatDateByPattern(String key, String pattern) throws ParseException {
		Date date = null;
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		if (StringUtils.isNotEmpty(key)) {
			date = sf.parse(key);
		}
		return date;
	}

	public static Date stringFormatDate2(String key) throws ParseException {
		Date date = null;
		if (StringUtils.isEmpty(key)) {
			date = sf2.parse(key);
		}
		return date;
	}

	/**
	 * 时间格式化.
	 * 
	 * @param time
	 * @return String
	 */
	public static String timeFormatStr(Date time) {
		return time != null ? format.format(time) : "";
	}

	/**
	 * 文件命名时间格式化
	 * 
	 * @param time
	 * @return String
	 */
	public static String fileTimeFormatStr(Date time) {
		return time != null ? formatter.format(time) : "";
	}

	/**
	 * 
	 * @Title:代码测速
	 * @Description: long timeStart = System.currentTimeMillis();
	 *               TimeUtil.timeSpentServiceRequest(timeStart);
	 * @param timeStart
	 */
	public static void timeSpentServiceRequest(long timeStart) {
		long timeUsed = System.currentTimeMillis() - timeStart;
		StringBuilder sb = new StringBuilder();
		sb.append("用时：");
		sb.append(timeUsed);
		sb.append("ms");
	}

	/***
	 * 
	 * @param date
	 * @param dateFormat (ss mm HH dd MM ? yyyy)
	 * @return
	 */
	public static String formatDateByPattern(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String formatTimeStr = null;
		if (date != null) {
			formatTimeStr = sdf.format(date);
		}
		return formatTimeStr;
	}

	/***
	 * 
	 * @param date
	 * @param dateFormat (ss mm HH dd MM ? yyyy)
	 * @param timeZone   时区
	 * @return
	 */
	public static String formatDateByPattern(Date date, String dateFormat, String timeZone) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		String formatTimeStr = null;
		if (date != null) {
			formatTimeStr = sdf.format(date);
		}
		return formatTimeStr;
	}

	/**
	 * cron默认格式 "0 06 10 15 1 ? 2014"
	 * 
	 * @param date
	 * @return
	 */
	public static String getCron(Date date) {
		String dateFormat = "ss mm HH dd MM ? yyyy";
		return formatDateByPattern(date, dateFormat);
	}

	// 获取当前年份
	public static Integer getCurrentYear() {
		Calendar date = Calendar.getInstance();
		Integer year = date.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 加减时间 负数为减 正数为加
	 *
	 * @param date 日期
	 * @param yyyy 年份
	 * @param MM   月份
	 * @param dd   天数
	 * @param HH   小时
	 * @param mm   分钟
	 * @param W    周 （加减一周）
	 * @return
	 */
	public static Date getDateAddsub(Date date, int yyyy, int MM, int dd, int HH, int mm, int W) {
		Calendar C = Calendar.getInstance();
		C.setTime(date);
		if (yyyy != 0)
			C.add(Calendar.YEAR, yyyy);
		if (MM != 0)
			C.add(Calendar.MONTH, MM);
		if (dd != 0)
			C.add(Calendar.DAY_OF_YEAR, dd);
		if (HH != 0)
			C.add(Calendar.HOUR_OF_DAY, HH);
		if (mm != 0)
			C.add(Calendar.MINUTE, mm);
		if (W != 0)
			C.add(Calendar.DAY_OF_WEEK_IN_MONTH, W);
		return C.getTime();
	}

	/**
	 * 字符串转时间
	 *
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	public static Date parseString(String string) throws ParseException {
		if (!StringUtils.isEmpty(string)) {
			return format.parse(string);
		} else {
			return null;
		}
	}

	public static Date dateAddYear(Date date, int year) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}

	public static Date dateAddMon(Date date, int month) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	public static Date dateAdd(Date date, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	public static Date dateAddHour(Date date, int hour) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		return calendar.getTime();
	}

	public static Date dateAddMin(Date date, int min) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, min);
		return calendar.getTime();
	}

	public static Date getDateStart(Date date) throws ParseException {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return formater2.parse(formater.format(date) + " 00:00:00");
	}

	public static Date getDateEnd(Date date) throws ParseException {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return formater2.parse(formater.format(new Date()) + " 23:59:59");
	}

	/**
	 * 获取月份所属季度
	 *
	 * @param month
	 * @return
	 */
	public static int checkQuarter(int month) {
		if (1 <= month && month <= 3) {
			return 1;
		} else if (4 <= month && month <= 6) {
			return 2;
		} else if (7 <= month && month <= 9) {
			return 3;
		} else {
			return 4;
		}
	}

	/**
	 * 获取当前季度的下一季度
	 *
	 * @param month
	 * @return
	 */
	public static int checkNextQuarter(int quarter) {
		if (1 == quarter) {
			return 2;
		} else if (2 == quarter) {
			return 3;
		} else if (3 == quarter) {
			return 4;
		} else if (4 == quarter) {
			return 1;
		} else {
			return 1;
		}
	}

	/**
	 * 获取当前月份最后一天
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getMaxMonthDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 获取本年周列表
	 *
	 * @param minWeek
	 * @return
	 */
	public static List<Integer> weekList(Integer minWeek) {
		Calendar a = new GregorianCalendar();
		a.set(a.get(Calendar.YEAR), Calendar.DECEMBER, 31, 23, 59, 59);
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(a.getTime());
		Integer maxWeek = c.get(Calendar.WEEK_OF_YEAR);
		List<Integer> list = new ArrayList<Integer>();
		for (int i = minWeek; i <= maxWeek; i++) {
			list.add(i);
		}
		return list;
	}

	/**
	 * 获取年列表
	 *
	 * @param minYear	最小年
	 * @param isAsc		是否正序
	 * @return
	 */
	public static List<Integer> yearList(Integer minYear, boolean isAsc) {
		Integer maxYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
		List<Integer> list = new ArrayList<Integer>();
		if(isAsc) {
			for (int i = minYear; i <= maxYear; i++) {
				list.add(i);
			}
		} else {
			for (int i = maxYear; i >= minYear; i--) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * 获取季度
	 *
	 * @param year
	 * @return
	 */
	public static List<Integer> getQuarterList() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= 4; i++) {
			list.add(i);
		}
		return list;
	}

	/**
	 * 根据季度获取月份
	 *
	 * @param year
	 * @return
	 */
	public static List<Integer> getQuarterMonthList(Integer quarter) {
		List<Integer> list = new ArrayList<Integer>();

		if (1 == quarter) {
			for (int i = 1; i <= 3; i++) {
				list.add(i);
			}
		} else if (2 == quarter) {
			for (int i = 4; i <= 6; i++) {
				list.add(i);
			}
		} else if (3 == quarter) {
			for (int i = 7; i <= 9; i++) {
				list.add(i);
			}
		} else {
			for (int i = 10; i <= 12; i++) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * 获取全年月份
	 *
	 * @param year
	 * @return
	 */
	public static List<Integer> getMonthList() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			list.add(i);
		}
		return list;
	}

	/**
	 * 获取某一月的日列表
	 * @return
	 */
	public static List<Integer> findDayList(Integer month){
		Calendar calendar = getCalendar();
		calendar.set(2019,month - 1,1);
		int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= maximum; i++) {
			list.add(i);
		}
		return list;
	}
	/**
	 * 获取指定年月周数
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static List<Integer> getWeekList(Integer year, Integer month) {
		Calendar calendar = getCalendar();
		calendar.set(year, month - 1, 1);
		Integer maxMonth = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= maxMonth; i++) {
			list.add(i);
		}
		return list;
	}

	/**
	 * 获取某年\月 的年周列表数 （只有年参数， 1-5x周，有月参数，获得级联某月的年周）
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static List<Integer> getYearWeekList(Integer year, Integer month) {
		Integer minWeek = null;// 返回的最小年周
		Integer maxWeek = null;// 返回的最大年周
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 1周第1天 为周一
		cal.setMinimalDaysInFirstWeek(1);// 跨年周大于1天，就为单独为1年的第1周
		cal.set(Calendar.YEAR, year);// 重新注入年
		// 如果月为空，查询全年所有周
		if (null == month || month < 1 || month >12) {
			minWeek = 1;
			maxWeek = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);// 获得年度最大周数
		} else {// 如果查询条件有月份
			cal.set(Calendar.MONTH, month - 1);// 月份从0开始
			// 获得这个月第一天所在年周
			cal.set(Calendar.DATE, 1);
			minWeek = cal.get(Calendar.WEEK_OF_YEAR);
			// ！！！如果 月份为12月 并且 跨月最小周天数为(1) 最后一周 直接全年最大周，不要用12月31日获得当前周 避免一年最后一周算入下一年
			if (month == 12) {//  12月的最后一个跨年周 属于下一年的情况
				maxWeek = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);// 获得年度最大周数
				// 如果年底最后一天不是 周日 跨年周算入下一年 所以最大周要加1

				// 获取某月最大天数
				int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				// 设置日历中月份的最大天数
				cal.set(Calendar.DAY_OF_MONTH, lastDay);
				int weekNum = cal.get(Calendar.DAY_OF_WEEK) - 1;
				if (weekNum < 6) {// 如果不是周日，则跨年周计入下一年，需要手工在本年最大周加1周
					maxWeek = maxWeek + 1;
				}
			} else {// 不是12月的时候
					// 获得这个月的最后一天所在年周
				cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
				maxWeek = cal.get(Calendar.WEEK_OF_YEAR);// 最大周
			}
		}
		// 生成周列表
		List<Integer> list = new ArrayList<Integer>();
		for (int i = minWeek; i <= maxWeek; i++) {
			list.add(i);
		}
		return list;
	}

	/**
	 * 获取星期几
	 *
	 * @param year  年份
	 * @param month 月份
	 * @param day   第几天
	 * @return
	 */
	public static int checkDateOfWeek(Integer year, Integer month, Integer day) {
		Calendar calendar = getCalendar();
		calendar.set(year, month - 1, day);
		return calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 7 : (calendar.get(Calendar.DAY_OF_WEEK) - 1);
	}

	/**
	 * 获取指定日期所在的周属于本月第几周
	 *
	 * @param year  年份
	 * @param month 月份
	 * @param day   第几天
	 * @return
	 */
	public static int checkDateWeekOfMonth(Integer year, Integer month, Integer day) {
		Calendar calendar = getCalendar();
		calendar.set(year, month - 1, day);
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获取指定日期所在的周属于本年的第几周
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static int checkDateWeekOfYear(Integer year, Integer month, Integer day) {
		Calendar calendar = getCalendar();
		calendar.set(year, month - 1, day);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public static Calendar getCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(1);
		return calendar;
	}

	public static Calendar getCalendar(Calendar calendar) {
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(1);
		return calendar;
	}

	/**
	 * 获取YYYY格式
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 获取MM格式
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 获取YYYY格式
	 */
	public static String getYear(Date date) {
		return formatDate(date, "yyyy");
	}

	/**
	 * 获取YYYY-MM-DD格式
	 */
	public static String getDay() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 获取YYYY-MM-DD格式
	 */
	public static String getDay(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 获取YYYYMMDD格式
	 */
	public static String getDays() {
		return formatDate(new Date(), "yyyyMMdd");
	}

	/**
	 * 获取YYYYMMDD格式
	 */
	public static String getDays(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 */
	public static String getTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss.SSS格式
	 */
	public static String getMsTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 获取YYYYMMDDHHmmss格式
	 */
	public static String getAllTime() {
		return formatDate(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 */
	public static String getTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatDate(Date date, String pattern) {
		String formatDate = null;
		if (StringUtils.isNotBlank(pattern)) {
			formatDate = DateFormatUtils.format(date, pattern);
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 日期比较，如果s>=e 返回true 否则返回false)
	 *
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (parseDate(s) == null || parseDate(e) == null) {
			return false;
		}
		return parseDate(s).getTime() >= parseDate(e).getTime();
	}

	/**
	 * 格式化日期
	 */
	public static Date parseDate(String date) {
		return parse(date, "yyyy-MM-dd");
	}

	/**
	 * 格式化日期
	 */
	public static Date parseTimeMinutes(String date) {
		return parse(date, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 格式化日期
	 */
	public static Date parseTime(String date) {
		return parse(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期
	 */
	public static Date parse(String date, String pattern) {
		try {
			return DateUtils.parseDate(date, pattern);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 */
	public static String format(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 把日期转换为Timestamp
	 */
	public static Timestamp format(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 校验日期是否合法
	 */
	public static boolean isValidDate(String s) {
		return parse(s, "yyyy-MM-dd HH:mm:ss") != null;
	}

	/**
	 * 校验日期是否合法
	 */
	public static boolean isValidDate(String s, String pattern) {
		return parse(s, pattern) != null;
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
					/ 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	public static String getWeekName(Integer week) {
		switch (week) {
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:

			return "星期三";
		case 4:

			return "星期四";
		case 5:

			return "星期五";
		case 6:

			return "星期六";
		case 7:

			return "星期日";
		default:
			return "";
		}
	}

	/**
	 * 自定义的周计算
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static DateDetail checkDateDetail(Integer year, Integer month, Integer day) {
		DateDetail dateDetail = new DateUtil.DateDetail();
		Calendar calendar = getCalendar();
		if(year != null && month != null && day != null) {
			calendar.set(year, month - 1, day);
		} else {
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
			day = calendar.get(Calendar.DAY_OF_MONTH);
		}
		// 年周需要特殊处理 （如果年末12月31日不是星期日）需要在年最大周数上加1
		if (month == 12) {
			// 判断12月31日是否是星期日
			int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			// 设置日历中月份的最大天数
			calendar.set(Calendar.DAY_OF_MONTH, lastDay);
			int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;// 获得12月31日的星期数
			if (weekDay < 7) {// 如果不是周日，则跨年周计入下一年，需要手工在本年最大周加1周
				// 判断新增计划日期是否是跨年周
				// 获得最后一周的周一的日期
				calendar.add(Calendar.DAY_OF_MONTH, -(weekDay - 1));
				// 判断本计划日期是否在一年最后一周
				int firstWeekDay = calendar.get(Calendar.DAY_OF_MONTH);// 一年最后跨年周周1是几号
				if (firstWeekDay <= day && day <= 31) {// 计划日期是最后一周，年周要加1
					Integer maxWeek = calendar.getActualMaximum(Calendar.WEEK_OF_YEAR) + 1;
					dateDetail.setDateOfWeekw(maxWeek);
				}
			}
		} else {// 不是12月 年周不存在计入下一年的情况
			dateDetail.setDateOfWeekw(calendar.get(Calendar.WEEK_OF_YEAR));
		}
		dateDetail.setDateOfYear(year);
		dateDetail.setDateOfMonth(month);
		dateDetail.setDateOfQuarter(checkQuarter(month));
		dateDetail.setDateOfYearMonth(Integer.valueOf(year.toString() + (month < 10 ? "0" + month.toString() : month.toString())));
		dateDetail.setDateOfWeek(checkDateOfWeek(year, month, day));
		dateDetail.setDateOfWeekm(calendar.get(Calendar.WEEK_OF_MONTH));
		dateDetail.setDateOfYmd(Integer.valueOf(dateDetail.getDateOfYearMonth().toString() + (day < 10 ? "0" + day.toString() : day.toString())));
		dateDetail.setCalendar(calendar);
		return dateDetail;
	}

	/**
	 * 获取上周一时间
	 *
	 * @param date
	 * @return
	 */
	public static Date geLastWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getThisWeekMonday(date));
		cal.add(Calendar.DATE, -7);
		return cal.getTime();
	}

	/**
	 * 获取本周一时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getThisWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(7);
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}

	/**
	 * 获取下周一时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getNextWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getThisWeekMonday(date));
		cal.add(Calendar.DATE, 7);
		return cal.getTime();
	}

	/**
	 * 根据年、月、日获取下周对应的日期
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getNextDayOfWeek(Integer year, Integer month, Integer day) {
		Calendar cal = getCalendar();
		if(year != null && month != null && day != null) {
			cal.set(year, month - 1, day);
			cal.add(Calendar.DATE, 7);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1;
			day = cal.get(Calendar.DAY_OF_MONTH);
			return year.toString() + (month < 10 ? "0" + month.toString() : month.toString()) + (day < 10 ? "0" + day.toString() : day.toString());
		}
		return null;
	}

	/**
	 * 获取星期几，日历类默认为：周日至周六对应1-7，变为周一至周日对应1-7
	 *
	 * @param calendar 日历对象
	 * @return
	 */
	public static Integer getDayOfWeek(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 7 : (calendar.get(Calendar.DAY_OF_WEEK) - 1);
	}

	/**
	 * 获取某日期的相关信息，配合本类中的 getCalendar() 方法使用
	 * @param calendar
	 * @return
	 */
	public static DateDetail getDateDetail(Calendar calendar) {
		DateDetail dateDetail = new DateUtil.DateDetail();
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH) + 1;
		Integer day = calendar.get(Calendar.DAY_OF_MONTH);

		// 年周需要特殊处理 （如果年末12月31日不是星期日）需要在年最大周数上加1
		if (month == 12) {
			// 判断12月31日是否是星期日
			int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			// 设置日历中月份的最大天数
			calendar.set(Calendar.DAY_OF_MONTH, lastDay);
			int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;// 获得12月31日的星期数
			if (weekDay < 7) {// 如果不是周日，则跨年周计入下一年，需要手工在本年最大周加1周
				// 判断新增计划日期是否是跨年周
				// 获得最后一周的周一的日期
				calendar.add(Calendar.DAY_OF_MONTH, -(weekDay - 1));
				// 判断本计划日期是否在一年最后一周
				int firstWeekDay = calendar.get(Calendar.DAY_OF_MONTH);// 一年最后跨年周周1是几号
				if (firstWeekDay <= day && day <= 31) {// 计划日期是最后一周，年周要加1
					Integer maxWeek = calendar.getActualMaximum(Calendar.WEEK_OF_YEAR) + 1;
					dateDetail.setDateOfWeekw(maxWeek);
				}
			}
		} else {// 不是12月 年周不存在计入下一年的情况
			dateDetail.setDateOfWeekw(calendar.get(Calendar.WEEK_OF_YEAR));
		}
		
		dateDetail.setDateOfYmdStr(year.toString() + "-" + (month < 10 ? "0" + month.toString() : month.toString()) + "-" + (day < 10 ? "0" + day.toString() : day.toString()));
		dateDetail.setDateOfYear(year);
		dateDetail.setDateOfMonth(month);
		dateDetail.setDateOfQuarter(checkQuarter(month));
		dateDetail.setDateOfYearMonth(Integer.valueOf(year.toString() + (month < 10 ? "0" + month.toString() : month.toString())));
		dateDetail.setDateOfWeek(checkDateOfWeek(year, month, day));
		dateDetail.setDateOfWeekm(calendar.get(Calendar.WEEK_OF_MONTH));
		dateDetail.setDateOfYmd(Integer.valueOf(dateDetail.getDateOfYearMonth().toString() + (day < 10 ? "0" + day.toString() : day.toString())));
		dateDetail.setCalendar(calendar);
		return dateDetail;
	}

	/**
	 * 根据当前日期获得所在周的日期区间（周一和周五日期）
	 * @param date
	 * @return
	 */
	public static String getTimeInterval(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		// System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 4); //星期五为4，星期日为6
		String imptimeEnd = sdf.format(cal.getTime());
		// System.out.println("所在周星期五的日期：" + imptimeEnd);
		return imptimeBegin + "," + imptimeEnd;
	}
	
	/**
	 * 获取一周开始到结束的list集合
	 * @param dBegin
	 * @param dEnd
	 * @return
	 */
	@SuppressWarnings("all")
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List lDate = new ArrayList();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}
	
	/**
	 * 获取星期列表（带名称和日期）
	 * @param year（年）
	 * @param month（月）
	 * @param week（年周）
	 * @return
	 */
	public static List<DateDetail> getWeekDateNameList(Integer year, Integer month, Integer week) {
		List<DateDetail> weekDateNameList = new ArrayList<DateDetail>();
		try {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year); // 2019年
			cal.set(Calendar.WEEK_OF_YEAR, week); // 设置为2019年的第几周
			cal.set(Calendar.DAY_OF_WEEK, 2); // 1表示周日，2表示周一，7表示周六
			Date date = cal.getTime();
			
			String yz_time = getTimeInterval(date);// 获取本周时间
			String array[] = yz_time.split(",");
			String start_time = array[0];// 本周第一天
			String end_time = array[1]; // 本周最后一天
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dBegin = sdf.parse(start_time);
			
			Date dEnd = sdf.parse(end_time);
			List<Date> lDate = findDates(dBegin, dEnd);// 获取这周所有date
			for (int i = 1; i <= lDate.size(); i++) {
				DateDetail dateDetail = new DateDetail();
				dateDetail.setNowWeekName(getWeekName(i));
				dateDetail.setNowWeekDate(sdf.format(lDate.get(i - 1)));
				weekDateNameList.add(dateDetail);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return weekDateNameList;
	}
	
	/**
	 * 获取指定日期所在月份开始的时间戳
	 * 
	 * @param date 指定日期
	 * @return
	 */
	public static Long getMonthBegin(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		// 将小时至0
		c.set(Calendar.HOUR_OF_DAY, 0);
		// 将分钟至0
		c.set(Calendar.MINUTE, 0);
		// 将秒至0
		c.set(Calendar.SECOND, 0);
		// 将毫秒至0
		c.set(Calendar.MILLISECOND, 0);
		// 获取本月第一天的时间戳
		return c.getTimeInMillis();
	}
	
	/**
	 * 获取指定日期所在月份结束的时间戳
	 * 
	 * @param date 指定日期
	 * @return
	 */
	public static Long getMonthEnd(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		// 设置为当月最后一天
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		// 将小时至23
		c.set(Calendar.HOUR_OF_DAY, 23);
		// 将分钟至59
		c.set(Calendar.MINUTE, 59);
		// 将秒至59
		c.set(Calendar.SECOND, 59);
		// 将毫秒至999
		c.set(Calendar.MILLISECOND, 999);
		// 获取本月最后一天的时间戳
		return c.getTimeInMillis();
	}

	
	/**
	* 根据具体年份周数获取开始日期
	* @param year
	* @param week
	* @return
	*/
	public static String getWeekDays(Integer year, Integer week) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Calendar cal = Calendar.getInstance();

		// 设置每周的开始日期
		cal.setFirstDayOfWeek(Calendar.MONDAY);

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week);

		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		String beginDate = sdf.format(cal.getTime());

		return beginDate;
	}
	
	public static void main(String[] args) throws ParseException {
//		Calendar calendar = getCalendar();
//		calendar.set(2019, 4, 31);
//		int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
//		System.out.println("one:" + weekOfMonth);
//		if (weekOfMonth == 1) {
//			calendar.add(Calendar.MONTH, -1);
//			System.out.println("two:" + calendar.getActualMaximum(Calendar.WEEK_OF_MONTH));
//		}
//		System.out.println(DateUtil.getNextDayOfWeek(2019, 0, 28));
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String nextWeekMonday = sdf.format(DateUtil.getNextWeekMonday(new Date()));
//		System.out.println(nextWeekMonday);
//		Calendar cal = Calendar.getInstance();
//		cal.set(2018, 11, 31);
//		Iterator<Calendar> cals = DateUtils.iterator(cal, DateUtils.RANGE_WEEK_MONDAY);
//		Calendar cale = null;
//		while(cals.hasNext()) {
//			cale = cals.next();
//			System.out.println(sf3.format(cale.getTime()) + ":周" + cale.get(Calendar.DAY_OF_WEEK));
//		}
//		Date date = new Date();
//		System.out.println(date);
//		Iterator<Calendar> cals = DateUtils.iterator(date, DateUtils.RANGE_WEEK_MONDAY);
//		Calendar cale = null;
//		while(cals.hasNext()) {
//			cale = cals.next();
//			System.out.println(sf3.format(cale.getTime()) + ":周" + cale.get(Calendar.DAY_OF_WEEK));
//		}
//		date = DateUtils.addDays(date, 7);
//		cals = DateUtils.iterator(date, DateUtils.RANGE_WEEK_MONDAY);
//		while(cals.hasNext()) {
//			cale = cals.next();
//			System.out.println(sf3.format(cale.getTime()) + ":周" + cale.get(Calendar.DAY_OF_WEEK));
//		}
	}

}
