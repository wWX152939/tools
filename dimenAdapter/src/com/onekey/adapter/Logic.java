package com.onekey.adapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logic {

	public static void readFileByLines(String srcFileName, String destFileName, String fileName, String height) {
		
		int dpi = 0;
		switch (destFileName) {
		case Config.XXHDPI:
			dpi = 480;
			break;
		case Config.XHDPI:
			dpi = 320;
			break;
		case Config.HDPI:
			dpi = 240;
			break;
		case Config.TVDPI:
			dpi = 213;
			break;
		case Config.MDPI:
			dpi = 160;
		case Config.LDPI:
			dpi = 120;
		default:
			break;
		}
		destFileName = destFileName + "-" + height + "/" + fileName;
		
		File srcFile = new File(srcFileName);
		File destFile = new File(destFileName);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		try {
			
			if (!destFile.exists()) {
				destFile.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(srcFile));
			writer = new BufferedWriter(new FileWriter(destFile)); 
			String tempString = null;
			
			@SuppressWarnings("unused")
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
//				System.out.println("line " + line + ": " + tempString);
				String regEx = ">([\\s\\S]*?)</dimen>";
				Pattern patValue = Pattern.compile(regEx);
				Matcher matValue = patValue.matcher(tempString);
				String xmlValue = null;
				while (matValue.find()) {
					xmlValue = matValue.group(1);
				}
				String regName = "\"[^\"]*\"";
				Pattern patName = Pattern.compile(regName);
				Matcher matName = patName.matcher(tempString);
				String xmlName = null;
				while (matName.find()) {
					xmlName = matName.group(0);
				}
				if (xmlValue != null
						&& (xmlValue.contains("dp") || xmlValue.contains("dip"))
						&& !xmlValue.contains("dimen")) {
					if (xmlValue.contains("dp")) {
						xmlValue = xmlValue.replace("dp", "");
					} else if (xmlValue.contains("dip")) {
						xmlValue = xmlValue.replace("dip", "");
					}
					int value = Integer.parseInt(xmlValue);
					String[] heights = height.split("x");
					int h = Integer.parseInt(heights[1]);
					int w = Integer.parseInt(heights[0]);
					float ret = 0;
					String suffix = "dip";
					float pxRadio = 1;
					if (Config.EXPORT_PX) {
						pxRadio = 1.33125f;
						suffix = "px";
					}
					
					// handle 
					if (xmlName.endsWith("_h\"")) {
						ret = (float) ((value * h / 736.0)) * pxRadio;
					} else if (xmlName.endsWith("_w\"")) {
						ret = (float) ((value * w / 1280.0)) * pxRadio;
					}
					
					String outputLine = "\t<dimen name=" + xmlName + ">" + (int)Math.rint(ret)
							+ suffix + "</dimen>\n";
//					System.out.println("2 line " + line + ": " + outputLine);

					writer.write(outputLine);
				} else if (xmlValue != null && xmlValue.contains("sp")
						&& !xmlValue.contains("dimen")) {
					float ret = 0;
					xmlValue = xmlValue.replace("sp", "");
					int value = Integer.parseInt(xmlValue);
					String[] heights = height.split("x");
					int h = Integer.parseInt(heights[1]);
					int w = Integer.parseInt(heights[0]);
					
					/**
					 * px /dp = dpi /160 -> 160 = dpi *dp /px = dpi2 * dp2 /px2
					 * 
					 * -> dp2 = (dpi * dp * px2) / (px * dpi2)
					 */
					if (xmlName.endsWith("_s\"")) {
						ret = (float) ((value  * 213 / 1476.5) * Math.sqrt(w * w + h * h) / dpi);
//						System.out.println("2 ret " + ret);
					}
					String outputLine = "\t<dimen name=" + xmlName + ">" + ret
							+ "sp</dimen>\n";
					writer.write(outputLine);
				} 
				else if (xmlValue == null) {
					if (!tempString.contains("!") && tempString.length() != 0)
						writer.write(tempString + "\n");
				}
				writer.flush();

				line++;
			}
			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	public static boolean checkExport(String dpi, String height) {
		boolean ret = false;
		switch (dpi) {
		case Config.XXHDPI:
			if (height.equals(Config.P1920_1080))
				ret = true;
			break;
		case Config.XHDPI:
			if (height.equals(Config.P1280_720)
					|| height.equals(Config.P1220_720))
				ret = true;
			break;
		case Config.HDPI:
			if (height.equals(Config.P1280_720))
				ret = true;
			break;
		case Config.TVDPI:
			if (height.equals(Config.P1280_720)
					|| height.equals(Config.P1280_800)
					|| height.equals(Config.P1920_1080))
				ret = true;
			break;
		case Config.MDPI:
			if (height.equals(Config.P1024_768))
				ret = true;
			break;
		case Config.LDPI:
			if (height.equals(Config.P800_600))
				ret = true;
			break;
		default:
			break;
		}
		
		if (ret) {
			return ret;
		}
	
		File srcFile = new File(Config.SRC + "./dimens.txt");
		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(srcFile));
			String lineString;
			while ((lineString = reader.readLine()) != null) {
				String[] params = lineString.split(",");
				for (String param : params) {
					String[] names = param.split("_");
					if (names != null) {
						names[0] = Config.DEST + names[0];
						if (names[0].equals(dpi) && names[1].equals(height)) {
							ret = true;
							return ret;
						}
					}
				}
			}
		} catch (IOException e) {
			
		}
		
		return ret;
	}

}
