package com.onekey.adapter;

/**
 * 配置输出路径，输出格式，px、dp等
 * @author onekey
 *
 */
public class Config {
	final static String SRC = "";
	final static String DEST = "output/";
	final static String XXHDPI = DEST + "values-land-xxhdpi";
	final static String XHDPI = DEST + "values-land-xhdpi";
	final static String HDPI = DEST + "values-land-hdpi";
	final static String TVDPI = DEST + "values-land-tvdpi";
	final static String MDPI = DEST + "values-land-mdpi";
	final static String LDPI = DEST + "values-land-ldpi";

	final static String P800_600 = "800x600";
	final static String P1024_768 = "1024x768";
	final static String P1220_720 = "1220x720";
	final static String P1280_720 = "1280x720";
	final static String P1280_800 = "1280x800";
	final static String P1920_1080 = "1920x1080";

	// 配置参数
	final static String[] fileNames = {"dimens.xml"};
	final static String[] DPI = {XXHDPI, XHDPI, HDPI, TVDPI, MDPI, LDPI};
	
	final static String[] heights = {P800_600, P1024_768, P1220_720, P1280_720, P1280_800, P1920_1080};
	
	final static boolean EXPORT_PX = true;
}
