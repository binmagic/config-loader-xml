package org.hive.xml.format;

import org.simpleframework.xml.transform.Transform;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author bianxueming
 * @version 1.0
 * @since 2017/9/22
 */
public class DateFormatTransformer implements Transform<Date>
{
	private DateFormat dateFormat;

	public DateFormatTransformer(DateFormat dateFormat)
	{
		this.dateFormat = dateFormat;
	}

	@Override
	public Date read(String s) throws Exception
	{
		return dateFormat.parse(s);
	}

	@Override
	public String write(Date date) throws Exception
	{
		return dateFormat.format(date);
	}
}
