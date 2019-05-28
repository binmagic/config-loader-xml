package org.hive.xml.format;

import org.hive.xml.util.TimeSpan;
import org.simpleframework.xml.transform.Transform;

/**
 * @author bianxueming
 * @version 1.0
 * @since 2017/9/22
 */
public class TimeSpanTransformer implements Transform<TimeSpan>
{
	@Override
	public TimeSpan read(String s) throws Exception
	{
		return TimeSpan.parse(s);
	}

	@Override
	public String write(TimeSpan timeSpan) throws Exception
	{
		return timeSpan.format();
	}
}
