package org.hive.xml.util;

import java.sql.Time;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author bianxueming
 * @version 1.0
 * @since 2017/9/22
 */
public class TimeSpan implements Comparable<TimeSpan>
{
	public final static TimeSpan ZERO = new TimeSpan(0);

	private long totalMilliSeconds = 0;

	/**
	 * 当天的时分秒
	 */
	public TimeSpan()
	{
		this(System.currentTimeMillis() / 1000);
	}

	public TimeSpan(long totalMilliSeconds)
	{
		totalMilliSeconds = totalMilliSeconds;
	}

	public TimeSpan(Date afterDate, Date beforeDate)
	{
		this(afterDate.getTime() - beforeDate.getTime());
	}

	public TimeSpan(Time time)
	{
		Calendar now = Calendar.getInstance();
		now.setTime(time);
		int hours = now.get(Calendar.HOUR_OF_DAY);
		int minutes = now.get(Calendar.MINUTE);
		int seconds = now.get(Calendar.SECOND);
		totalMilliSeconds = (hours * 60L * 60L + minutes * 60L + seconds) * 1000L;
	}

	public TimeSpan(int hours, int minutes, int seconds)
	{
		totalMilliSeconds = (hours * 60L * 60L + minutes * 60L + seconds) * 1000L;
	}

	public TimeSpan(int days, int hours, int minutes, int seconds)
	{
		totalMilliSeconds = (days * 24L * 60L * 60L + hours * 60L * 60L + minutes * 60L + seconds) * 1000L;
	}

	public long getTotalSeconds()
	{
		return totalMilliSeconds == 0 ? 0 : totalMilliSeconds / 1000L;
	}
	public long getMilliSeconds()
	{
		return totalMilliSeconds;
	}
	public long  getSeconds()
	{
		return (totalMilliSeconds % (1000L * 60L * 60L * 24L) % (1000L * 60L * 60L) % (1000L * 60L)) / 1000L;
	}
	public long getMinutes()
	{
		return (totalMilliSeconds % (1000L * 60L * 60L * 24L) % (1000L * 60L * 60L)) / (1000L * 60L);
	}
	public long getHours()
	{
		return (totalMilliSeconds % (1000L * 60L * 60L * 24L)) / (1000L * 60L * 60L);
	}

	public long getDays()
	{
		return totalMilliSeconds / (1000L * 60L * 60L * 24L);
	}

	@Override
	public int compareTo(TimeSpan timeSpan)
	{
		if(this == timeSpan) return 0;
		if(timeSpan == null) throw new NullPointerException("TimeSpan compare timeSpan=null");
		long diff = this.totalMilliSeconds - timeSpan.totalMilliSeconds;
		if(diff < 0) return -1;
		if(diff > 0) return 1;
		return 0;
	}

	public boolean after(TimeSpan timeSpan)
	{
		return timeSpan == null || this.totalMilliSeconds > timeSpan.totalMilliSeconds;
	}
	public boolean before(TimeSpan timeSpan)
	{
		return timeSpan != null && this.totalMilliSeconds < timeSpan.totalMilliSeconds;
	}

	public static TimeSpan parse(String s) throws ParseException
	{
		String[] ss = s.split("\\.|:");
		if(ss.length != 3 && ss.length != 4) throw new ParseException("More Than three ':', " + s, -1);

		if(ss.length == 4)
		{
			int days = Integer.parseInt(ss[0]);
			int hours = Integer.parseInt(ss[1]);
			int minutes = Integer.parseInt(ss[2]);
			int seconds = Integer.parseInt(ss[3]);
			return new TimeSpan(days, hours, minutes, seconds);
		}
		else
		{
			int hours = Integer.parseInt(ss[0]);
			int minutes = Integer.parseInt(ss[1]);
			int seconds = Integer.parseInt(ss[2]);
			return new TimeSpan(hours, minutes, seconds);
		}
	}

	public String format()
	{
		if(getDays() <= 0)
			return String.format(getHours() < 10 ? "%02d:%02d:%02d" : "%d:%02d:%02d", getHours(), getMinutes(), getSeconds());
		else
			return String.format(getHours() < 10 ? "%d:%02d:%02d:%02d" : "%d:%d:%02d:%02d", getDays(), getHours(), getMinutes(), getSeconds());
	}

	@Override
	public String toString()
	{
		return format();
	}


	public long getTotalMilliSeconds()
	{
		return totalMilliSeconds;
	}

	public void setTotalMilliSeconds(long totalMilliSeconds)
	{
		this.totalMilliSeconds = totalMilliSeconds;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		TimeSpan timeSpan = (TimeSpan)o;
		return totalMilliSeconds == timeSpan.totalMilliSeconds;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(totalMilliSeconds);
	}
}
