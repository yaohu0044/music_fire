package com.musicfire.common.utiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <b>时间管理工具</b>
 * <br>
 * <font color = 'red'><b>时间，不在于你拥有多少，而在于你怎样使用。</b></font>
 * <ul>
 * 	<li>获取当前时间</li>
 * 	<li>时间分割:根据提供起始时间和结束时间以及分割份数，将时间分割为等间隔的多分时间，例如2016-1-1 到 2016-1-4 分为4份 就是 1,2,3,4</li>
 * 	<li>时间增减:根据增减量来改变单位时间，过去改变后的时间</li>
 * 	<li>时间格式化:格式化时间</li>
 * 	<li>其他功能还有待开发^^</li>
 * </ul>
 */
public class DateTool {
	
	private  Date date;
	
	private  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 日
	 */
	
	public static final long DAY=1000*60*60*24;
	/**
	 * 时
	 */
	
	public static final long HOUR=1000*60*60;
	/**
	 * 秒
	 */
	
	public static final long SECOND=1000;
	/**
	 * 分
	 */
	
	public static final long MINUTE=1000*60;
	/**
	 * 周
	 */
	
	public static final long WEEK=1000*60*60*24*7;

	//判断选择的日期是否是本周
	public static boolean isThisWeek(Date time)  {
		Calendar calendar = Calendar.getInstance();
		int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.setTime(time);
		int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        return paramWeek == currentWeek;
    }
	//判断选择的日期是否是今天
	public static boolean isToday(long time)  {
		return isThisTime(time,"yyyy-MM-dd");
	}
	//判断选择的日期是否是本月
	public static boolean isThisMonth(long time)  {
		return isThisTime(time,"yyyy-MM");
	}

	private static boolean isThisTime(long time,String pattern) {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String param = sdf.format(date);//参数时间
		String now = sdf.format(new Date());//当前时间
        return param.equals(now);
    }

	/**
	 * 获取指定时间是星期几
	 * @param date
	 * @return
	 * @throws Exception
	 */
    public static int getWeekOfDate(Object date) throws Exception {
    	Date dt = new Date();
    	if(date!=null){
	    	if(date instanceof String){
	    		dt = getDate(date);
	    	}else if(date instanceof Long){
	    		dt.setTime((long)date);
	    	}else if(date instanceof Date){
	    		dt = (Date)date;
	    	}else{
	    		throw new Exception("不能解析的时间！");
	    	}
    	}
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(w==0) w = 7;
        return w;
    }

	/**
	 * 当前时间是本月第几周
	 * @return
	 * @throws Exception
	 */
	public static int getWeekOfMonth() throws Exception{
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_MONTH);
	}
	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws Exception
	 */
	public static String NOW() throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	} 
	
	public DateTool(){}
	public DateTool(Date date){
		this.date=date;
	}
	
	/**
	 * 比较两个时间的大小
	 * @param dt1
	 * @param dt2
	 * @return
	 * @throws Exception
	 */
	public static boolean compleTo(Object dt1,Object dt2) throws Exception{
		long T1 = getTime(dt1);
		long T2 = getTime(dt2);
        return T1 - T2 >= 0;
		
	}
	/**
	 * 计算时间差
	 * @param dt1	时间1
	 * @param dt2	时间2
	 * @param field	时间单位，时分秒
	 * @return
	 */
	public static double compleTo(Date dt1,Date dt2,long field){
		return ((double)(dt1.getTime()-dt2.getTime())/field);
	}

	/**
	 * 计算时间差
	 * @param dt1
	 * @param dt2
	 * @return
	 * @throws Exception
	 */
	public static double compleTo(Object dt1,Object dt2,long field) throws Exception{
		long T1 = getTime(dt1);
		long T2 = getTime(dt2);
		return ((double)(T1-T2)/field);
	}

	public static Long getTime(Object dt) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long T1 = 0l;
		if(dt instanceof Date){
			T1 = ((Date) dt).getTime();
		}else if(dt instanceof String){
			T1 = getParse(dt.toString()).getTime();
		}else if(dt instanceof Long){
			T1 = (long) dt;
		}else{
			throw new Exception("不能识别的时间");
		}
		return T1;
	}

	public DateTool(String date) throws ParseException{
		this.date=sdf.parse(date);
	}
	
	public void setPattern(String pattern){
		this.sdf=new SimpleDateFormat(pattern); //重新赋值
	}
	
	public DateTool(Date date,String pattern){
		this.date=date;
		this.sdf=new SimpleDateFormat(pattern); //重新赋值
	}
	/**
	 * 时间增删
	 * @param field 时分秒
	 * @param interval	增删量
	 * @return
	 */
	public String DateChangeS(long field,int interval){ //根据传入的值获取更改时间
		Date dt=new Date(this.date.getTime()+(field*interval));
		return sdf.format(dt);
	}
	/**
	 * 时间增删
	 * @param field 时分秒
	 * @param interval 增删量
	 * @return
	 */
	public Date DateChangeD(long field,int interval){
		Date dt=new Date(this.date.getTime()+(field*interval));
		return dt;
	}

	/**
	 * 获取
	 * @return
	 * @throws Exception
	 */
	public static Date getFistOfMonth() throws Exception{
		Calendar  cale=Calendar.getInstance();//获取当前日期
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return cale.getTime();
	}

	/**
	 * 获取本月最后一天
	 * @return
	 * @throws Exception
	 */
	public static Date getLastOfMonth() throws Exception{
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.getTime();
	}


	public static String getHMS(Object obj) throws Exception{
		Date dt = getDate(obj);
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		return sdf.format(dt);
	}

	public static Date getParse(String date) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			return sdf.parse(date);
		}catch (Exception e){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(date);
		}
	}

	/**
	 * 獲取年月日
	 * @param date
	 * @return
	 */
	public static String getYMD(Date date){
		if(date==null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static String getFormat(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			return sdf.format(date);
		}catch (Exception e){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}
	}
	/**
	 * 获取字符串格式的时间
	 * @param dt
	 * @return
	 * @throws Exception
	 */
	public static String getString(Object dt) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String T1 = "";
		if(dt instanceof Date){
			T1 = getFormat((Date)dt);
		}else if(dt instanceof String){
			T1 = (String)dt;
		}else if(dt instanceof Long){
			T1 = getFormat(new Date((long)dt));
		}else{
			throw new Exception("不能识别的时间");
		}
		return T1;
	}

	/**
	 * 获取日期格式的时间
	 * @param dt
	 * @return
	 * @throws Exception
	 */
	public static Date getDate(Object dt) throws Exception{
		Date T1 = null;
		if(dt instanceof Date){
			T1 = (Date)dt;
		}else if(dt instanceof String){
			T1 = getParse((String)dt);
		}else if(dt instanceof Long){
			T1 = new Date((long)dt);
		}else{
			throw new Exception("不能识别的时间");
		}
		return T1;
	}

	/**
	 * 判断当前的单双周
	 * @param dt
	 * @param oddEven
	 * @return
	 * @throws Exception
	 */
	public static int numberOfDays(Object dt,int oddEven) throws Exception{
		return numberOfDays(dt,System.currentTimeMillis(),oddEven);
	}
	/**
	 * 判断指定时间为单双周
	 * @param dt 开学起始时间
	 * @param oddEven 指定时间
	 * @return
	 * @throws Exception
	 */
	public static int numberOfDays(Object dt,Object dt2,int oddEven) throws Exception{
		Calendar c = Calendar.getInstance();
		c.setTime(getDate(dt2));
		int week = c.get(Calendar.WEEK_OF_YEAR);
		c.setTime(getDate(dt));
		int beforeWeek = c.get(Calendar.WEEK_OF_YEAR);
		//System.out.println(beforeWeek);  // 指定日期是当年的第几周
		if((week-beforeWeek)%2==0){
			return oddEven;
		}else{
			if(oddEven==1){
				return 2;
			}else{
				return 1;
			}
		}
	}
	/**
	 * 获取周
	 * @param dt
	 * @return
	 * @throws Exception
	 */
	public static int getWeek(Object dt) throws Exception{
		Calendar c = Calendar.getInstance();
		c.setTime(getDate(dt));
		int week = c.get(Calendar.DAY_OF_WEEK)-1;
		if(week == 0) {
			return 7;
		}else{
			return week;
		}
	}
	/**
	 * 获取周中文
	 * @param dt
	 * @return
	 * @throws Exception
	 */
	public static String getWeeks(Object dt) throws Exception{
		String[] week =  {"皮一下","星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
		return week[getWeek(dt)];
	}

	/**
	 * 按单位分割时间
	 * @param start 起始时间
	 * @param end	结束时间
	 * @param field	分割单位（时分秒）
	 * @param type	返回类型
	 * @param <T>	泛型
	 * @return
	 * @throws Exception
	 */
	public static <T>List<T> split(Object start,Object end,long field,Class<T> type) throws Exception{
		Double cha = DateTool.compleTo(end,start,field)+1; //分割天数
		return DateTool.split(start,end,field,cha.intValue(),type); //将时间分割为每天
	}


	/**
	 * 获取时间段内最大的星期
	 * @param timelist 时间列表
	 * @return
	 * @throws Exception
	 */
	public static int maxWeek(List<Object> timelist) throws Exception{
		int temp = 7;
		if(timelist == null || timelist.size() <1) throw new Exception("请输入正确的时间段");
		for (Object obj :timelist){
			int i = getWeekOfDate(obj);
			if(i==7){
				return 7;
			}else{
				if(temp<i) temp = i;
			}
		}
		return temp;
	}

	/**
	 * 获取时间段内最小的星期
	 * @param timelist 时间列表
	 * @return
	 * @throws Exception
	 */
	public static int minWeek(List<Object> timelist) throws Exception{
		int temp = 1;
		if(timelist == null || timelist.size() <1) throw new Exception("请输入正确的时间段");
		for (Object obj :timelist){
			int i = getWeekOfDate(obj);
			if(i==1){
				return 1;
			}else{
				if(temp>i) temp = i;
			}
		}
		return temp;
	}

	/**
	 *
	 * @param start	起始时间
	 * @param end	结束时间
	 * @param field	分割单位
	 * @param interval	分割分数
	 * @param type 返回类型，支持Date,String,Long
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T>List<T> split(Object start,Object end,long field,double interval,Class<T> type) throws Exception{
		long ival=getTime(end) - getTime(start);
		if(ival<0) throw new Exception("The start time is greater than the end time"); //起始时间大于结束时间
		List<T> list=new ArrayList<T>();
		if(ival<field*(interval-1)){  //如果间隔时间小于或等于分割时间
			if(type.getSimpleName().equals("Date")){
				list.add((T)getDate(start));	//直接返回其实结束时间
				list.add((T)getDate(end));
			}else if (type.getSimpleName().equals("String")){
				list.add((T)getString(start));	//直接返回其实结束时间
				list.add((T)getString(end));
			}else if (type.getSimpleName().equals("Long")){
				list.add((T)getTime(start));	//直接返回其实结束时间
				list.add((T)getTime(end));
			}
		}else{
			for(int i=0;i<interval;i++){
				double inval=(i/(interval-1))*ival;
				long time=(getTime(start)+(long)inval);
				if(type.getSimpleName().equals("Date")){
					list.add((T)getDate(time));	//返回一个时间点
				}else if (type.getSimpleName().equals("String")){
					list.add((T)getString(time));	//返回一个时间点
				}else if (type.getSimpleName().equals("Long")){
					list.add((T)getTime(time));	//返回一个时间点
				}
			}
		}
		return list;
	}

	/**
	 * 获取时间月的第一天
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String firstDay(String time) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start=format.parse(time);
		//获取前月的第一天
		Calendar c = Calendar.getInstance();
		c.setTime(start);//把当前时间赋给日历
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		String first = format.format(c.getTime());
		return first;
	}

	/**
	 * 获取时间月的最后一天
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String lastDay(String time) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start=format.parse(time);
		Calendar ca = Calendar.getInstance();
		ca.setTime(start);//把当前时间赋给日历
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		return last;
	}

	/**
	 * 获取明天的时间
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String Tomorrow(String time) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start=format.parse(time);
		Calendar ca = Calendar.getInstance();
		ca.setTime(start);//把当前时间赋给日历
		ca.add(Calendar.DATE,1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
		Date time1 = ca.getTime();
		String dateString = format.format(time1);
		return dateString;
	}

	/**
	 * 获取上个星期的时间
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String LastWeek(String time) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start=format.parse(time);
		Calendar ca = Calendar.getInstance();
		ca.setTime(start);//把当前时间赋给日历
		ca.add(Calendar.DATE,-7);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
		Date time1 = ca.getTime();
		String dateString = format.format(time1);
		return dateString;
	}


	/**
	 * 获取昨天的时间
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String Yesterday(String time) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start=format.parse(time);
		Calendar ca = Calendar.getInstance();
		ca.setTime(start);//把当前时间赋给日历
		ca.add(Calendar.DATE,-1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
		Date time1 = ca.getTime();
		String dateString = format.format(time1);
		return dateString;
	}

	/**
	 * 获取星期几
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String xq(String time) throws Exception{
		String[] weeks = {"7","1","2","3","4","5","6"};
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
		Date date=sdf.parse(time);
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if(week_index<0){
			week_index = 0;
		}
		return weeks[week_index];
	}

	/**
	 * 获取当前月份
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String month(Object time) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(time));
		String month = (cal.get(Calendar.MONTH) + 1) + "";
		return month;
	}

	public static int day(Object time) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(time));
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取年
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String year(String time) throws Exception{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
		Date date=sdf.parse(time);
		cal.setTime(date);
		String year = cal.get(Calendar.YEAR)+"";
		return year;
	}

	/**
	 * 获取上个月的第一天
	 */
	public static String mFirstDay() throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal_1=Calendar.getInstance();//获取当前日期
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cal_1.getTime());
		return firstDay;
	}


	/**
	 * 获取上个月的最后一天
	 */
	public static String mLastDay() throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
		String lastDay = format.format(cale.getTime());
		return lastDay;
	}

	/**
	 * 指定时间周一的时间
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String firstDate(String time) throws Exception{
		SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
		Date parse = formater.parse(time);
		Calendar cal=new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(parse);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		Date first=cal.getTime();
		String firstDate = formater.format(first);
		return firstDate;
	}

	/**
	 * 指定时间获取这周最后一天的时间
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String lastDate(String time) throws Exception{
		SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
		Date parse = formater.parse(time);
		Calendar cal=new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(parse);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
		Date last=cal.getTime();
		String lastDate = formater.format(last);
		return lastDate;
	}

	/**
	 * 根据当前日期获得所在周的日期区间（周一和周日日期）
	 *
	 * @return
	 * @author zhaoxuepu
	 * @throws ParseException
	 */
	public static String getTimeInterval(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
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
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		// System.out.println("所在周星期日的日期：" + imptimeEnd);
		return imptimeBegin + "," + imptimeEnd;
	}


	/**
	 * 根据当前日期获得上周的日期区间（上周周一和周日日期）
	 *
	 * @return
	 * @author zhaoxuepu
	 */
	public static String getLastTimeInterval() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
		int offset1 = 1 - dayOfWeek;
		int offset2 = 7 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1 - 7);
		calendar2.add(Calendar.DATE, offset2 - 7);
		// System.out.println(sdf.format(calendar1.getTime()));// last Monday
		String lastBeginDate = sdf.format(calendar1.getTime());
		// System.out.println(sdf.format(calendar2.getTime()));// last Sunday
		String lastEndDate = sdf.format(calendar2.getTime());
		return lastBeginDate + "," + lastEndDate;
	}

	/**
	 * 获取时间月的总天数
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static int monthLastDay(String time) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date start=format.parse(time);
		Calendar ca = Calendar.getInstance();
		ca.setTime(start);//把当前时间赋给日历
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.get(Calendar.DATE);
	}

	/**
	 * 获取指定月的所有日期列表
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	public static List<String> monthOfDayList(String month) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(month));//month 为指定月份任意日期
		int year = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH);
		int dayNumOfMonth = monthLastDay(month);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < dayNumOfMonth; i++, cal.add(Calendar.DATE, 1)) {
			Date d = cal.getTime();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String df = simpleDateFormat.format(d);
			list.add(df);
		}
		return list;
	}
	public static String transferLongToDate(Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(millSec);
		return sdf.format(date);
	}

    /**
     * 获取指定天之前日期
     * @param day 天数
     * @return
     */
	public static Date getBefDay(Integer day){
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE,-7);
        return ca.getTime();
	}
}
