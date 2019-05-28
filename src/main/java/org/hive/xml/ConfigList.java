package org.hive.xml;


import org.simpleframework.xml.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author bianxueming
 * @version 1.0
 * @since 2017/9/22
 */
public abstract class ConfigList<K, T extends Config<K>>
{
	public T get(K id)
	{
		return map().get(id);
	}

	public void dump()
	{
		for(T t : map().values())
		{
			t.dump();
		}
	}

	protected abstract List<T> list();

	protected abstract Map<K, T> map();

	public final void invokePreparePostLoad() throws Exception
	{
		for(T t : map().values())
		{
			t.preparePostLoad();
		}
		this.preparePostLoad();
	}

	public final void invokePostLoad() throws Exception
	{
		for(T t : map().values())
		{
			t.postLoad();
		}
		this.postLoad();
	}

	public void preparePostLoad() throws Exception
	{

	}

	public void postLoad() throws Exception
	{

	}

	@Validate
	protected void validate() throws PersistenceException
	{
		ArrayList<K> keys = new ArrayList<K>();
		for(T t : list())
		{
			if(keys.contains(t.getID()))
			{
				throw new PersistenceException("Duplicated " + t.getClass() + " id: " + t.getID());
			}
			keys.add(t.getID());
		}
	}

	@Commit
	protected void commit()
	{
		for(T t : list())
		{
			map().put(t.getID(), t);
		}
	}

	@Persist
	protected void prepare()
	{
		list().clear();
		list().addAll(map().values());
	}

	@Complete
	protected void complete()
	{

	}
}
