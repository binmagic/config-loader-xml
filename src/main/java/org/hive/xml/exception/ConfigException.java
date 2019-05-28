package org.hive.xml.exception;

/**
 * @author bianxueming
 * @version 1.0
 * @since 2017/9/22
 */
public class ConfigException extends Exception
{
	private final String configName;

	public ConfigException(String configName)
	{
		this.configName = configName;
	}

	public ConfigException(String configName, String message)
	{
		super(message);
		this.configName = configName;
	}

	public ConfigException(String configName, String message, Throwable cause)
	{
		super(message, cause);
		this.configName = configName;
	}

	public ConfigException(String configName, Throwable cause)
	{
		super(cause);
		this.configName = configName;
	}

	public String getConfigName()
	{
		return configName;
	}

	@Override
	public String toString()
	{
		return '{'
				+ " configName='" + configName + ".xml" + '\''
				+ " exception='" + super.toString() + '\''
				+ '}';
	}
}
