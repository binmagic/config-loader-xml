package org.hive.xml.validator;

import org.hive.xml.ConfigFactory;
import org.hive.xml.exception.ConfigException;
import org.hive.xml.loader.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;

/**
 * @author bianxueming
 * @version 1.0
 * @since 2017/9/22
 */
public class ConfigValidator
{
	private static final Logger logger = LoggerFactory.getLogger(ConfigValidator.class);

	public static void main(String[] args)
	{
		File file = new File("./config");
		if(args.length == 1)
		{
			file = new File(args[0]);
		}
		if(!file.isDirectory())
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("请选择配置文件所在目录");
			chooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setMultiSelectionEnabled(false);
			int ret = chooser.showDialog(chooser, "冲吧，少年！");
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				file = chooser.getSelectedFile();
				if(file == null || !file.isDirectory())
				{
					logger.error("要选择目录");
					System.exit(1);
				}
			}
			else
			{
				System.exit(1);
			}
		}
		else
		{
			logger.debug("读取目录: " + file.getAbsolutePath());
		}

		// copying override folder
		File override = new File(file.getPath() + "_override");
		if(file.isDirectory())
		{
			try
			{
				copyFolder(override, file);
			}
			catch(IOException ignore)
			{
				logger.debug("copying override error! " + ignore.toString());
			}
		}

		String configPath = file.getAbsolutePath();
		try
		{
			ConfigFactory factory = (ConfigFactory)Class.forName(System.getProperty("config.factory")).newInstance();
			ConfigLoader.loadAll(factory, configPath, false);
		}
		catch(IllegalAccessException | InstantiationException | ClassNotFoundException e)
		{
			logger.error("Java启动 -Dconfig.factory 参数的值不对, 生成不了相应的ConfigFactory, 这个找服务器程序员.", e);
			error();
		}
		catch(ConfigException e)
		{
			String configName = e.getConfigName();
			if(configName == null)
			{
				logger.error("有奇怪的错误,请联系任何一个服务器程序员,给他看下面的输出内容:", e);
			}
			else
			{
				String fileName = getFileName(configPath, configName);
				if(fileName == null)
				{
					logger.error("{} 出错了, 具体错误参看上面输出, 找不到它的配置文件, 应该是配置文件被删掉了", configName);
					fileName = configPath;
				}
				else
				{
					logger.error("{} 出错了, 具体错误参看上面输出, 配置文件在这里 {}", configName, fileName);
				}
				try
				{
					dumpSubversionInfo(fileName);
				}
				catch(Exception se)
				{
					logger.error("本来想试图看看是谁应该接这个锅的,但是无奈有错误发生了,所以看不到了", se);
				}
			}

			error();
		}
		catch(Exception e)
		{
			logger.error("有奇怪的错误,请联系任何一个服务器程序员,给他看下面的输出内容:", e);
			error();
		}

		logger.debug("校验配置成功，可以尝试上传脚本了。");
	}

	private static void error()
	{
		logger.error("校验配置失败，以上是错误代码。");
		logger.error("校验配置失败，以上是错误代码。");
		logger.error("校验配置失败，以上是错误代码。");

		System.exit(2);
	}

	private static String getFileName(String configPath, String configName)
	{
		String filePrefix = configPath + File.separator + configName.toLowerCase();
		File is = new File(filePrefix + ".xml");
		if(is.exists() && is.isFile())
		{
			return is.getAbsolutePath();
		}
		else
		{
			is = new File(filePrefix);
			if(is.exists() && is.isDirectory())
			{
				return is.getAbsolutePath();
			}
		}
		return null;
	}

	private static void dumpSubversionInfo(String filePath) throws Exception
	{
		Process process = Runtime.getRuntime().exec(new String[]{"svn", "info", filePath});

		process.waitFor();

		int ret = process.exitValue();
		if(ret != 0)
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String str = "";
			String line;
			while((line = reader.readLine()) != null)
			{
				str += line + System.getProperty("line.separator", "\n");
			}
			throw new Exception(str);
		}
		else
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while((line = reader.readLine()) != null)
			{
				if(line.contains("Last Changed Author: "))
				{
					String user = line.substring(line.indexOf("Last Changed Author: ") + "Last Changed Author: ".length());
					logger.error("最后一次提交: {} -> {}", user, user.substring(user.lastIndexOf('_') + 1));
					logger.error("最后一次提交: {} -> {}", user, user.substring(user.lastIndexOf('_') + 1));
					logger.error("最后一次提交: {} -> {}", user, user.substring(user.lastIndexOf('_') + 1));
					logger.error("重要的事情说三遍!!!");
				}
				else if(line.contains("Last Changed Date: "))
				{
					logger.error("产生时间: " + line.substring(line.indexOf("Last Changed Date: ") + "Last Changed Date: ".length()));
				}
			}
		}
	}

	private static void copyFolder(File src, File dest) throws IOException
	{
		if (src.isDirectory())
		{
		    if (!dest.exists())
		    {
		        dest.mkdir();
		    }

		    String files[] = src.list();
		    for (String file : files)
		    {
		        File srcFile = new File(src, file);
		        File destFile = new File(dest, file);
		        // 递归复制
		        copyFolder(srcFile, destFile);
		    }
		}
		else
		{
		    InputStream in = new FileInputStream(src);
		    OutputStream out = new FileOutputStream(dest);

		    byte[] buffer = new byte[2097152];

		    int length;

		    while ((length = in.read(buffer)) > 0)
		    {
		        out.write(buffer, 0, length);
		    }
		    in.close();
		    out.close();
		}
	}
}
