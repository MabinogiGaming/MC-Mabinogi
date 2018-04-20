package com.mabinogi.lib.logging;

import org.apache.logging.log4j.Logger;

public class LogHandler {
	
	public static final int LEVEL_DEBUG = 1;
	public static final int LEVEL_INFO = 2;
	public static final int LEVEL_WARN = 3;
	public static final int LEVEL_ERROR = 4;
	
	private Logger logger;
	private int logLevel;
	
	public LogHandler(Logger logger, int logLevel) 
	{
		this.logger = logger;
		this.logLevel = logLevel;
	}
	
	public void debug(String msg)
	{
		if (logger != null && logLevel <= LEVEL_DEBUG) logger.debug(msg);
	}
	
	public void info(String msg)
	{
		if (logger != null && logLevel <= LEVEL_INFO) logger.info(msg);
	}
	
	public void warn(String msg)
	{
		if (logger != null && logLevel <= LEVEL_WARN) logger.warn(msg);
	}
	
	public void error(String msg)
	{
		if (logger != null && logLevel <= LEVEL_ERROR) logger.error(msg);
	}

}
