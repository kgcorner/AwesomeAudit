package com.aa.constants;

import com.aa.util.UtilityFuncs;

public class Command {
	public static final String UNTAR_PLUGIN="tar";
	public static final String UNTAR_PLUGIN_OPT="-xvf";
	public static final String UNTAR_PLUGIN_LOCATION_OPT="-C";
	public static final String UNTAR_LOCATION=UtilityFuncs.getPWD()+"/plugins/"; 
	public static final String MKDIR_PLUGIN_DIR="mkdir -p "+UtilityFuncs.getPWD()+"/plugins/";
	public static final String PLUGIN_TAR_LIST="tar";
	public static final String PLUGIN_TAR_LIST_OPT="-tf";
	public static final String PLGUIN_RUN_METHOD_NAME_CODEBASE="processCodeBase";
}
