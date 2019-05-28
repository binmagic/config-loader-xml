package org.hive.xml;

import org.hive.xml.exception.ConfigException;

/**
 * @author bianxueming
 * @version 1.0
 * @since 2017/9/22
 */
public interface ConfigFactory
{
	void registerConfigs() throws ConfigException;
}
