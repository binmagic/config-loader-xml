package org.hive.xml;

import org.hive.xml.loader.ConfigLoader;

import java.lang.reflect.Field;

/**
 * @author bianxueming
 * @version 1.0
 * @since 2017/9/22
 */
public abstract class Config<K> implements Cloneable
{
	protected ConfigLoader loader = ConfigLoader.getInstance();

	public abstract K getID();

	public void dump()
	{
		System.out.print(this.getClass().getSimpleName() + ":dump");
		Field[] fields = this.getClass().getFields();
		for(Field field : fields)
		{
			try
			{
				System.out.print(":" + field.getName() + "=" + field.get(this) + "");
			}
			catch(IllegalAccessException ignored)
			{

			}
		}
		System.out.println();
	}

	public final ConfigLoader getLoader()
	{
		return loader;
	}

	public final void invokePreparePostLoad() throws Exception
	{
		this.preparePostLoad();
	}

	public final void invokePostLoad() throws Exception
	{
		this.postLoad();
	}

	public void preparePostLoad() throws Exception
	{

	}

	public void postLoad() throws Exception
	{

	}
}
