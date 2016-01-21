package com.aa.exception;
import com.aa.constants.StaticMessages;

/***
 *  This Exception should be thrown when there's error occurs while running plugin
 * @author kumar
 *
 */
public class PluginRunException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3176250537080340276L;
	public PluginRunException(String msg)
	{
		super(msg);
	}
	public PluginRunException()
	{
		super(StaticMessages.PLUGIN_RUN_EXCEPTION);
	}
}
