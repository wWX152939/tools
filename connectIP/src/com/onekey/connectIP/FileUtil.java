package com.onekey.connectIP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
	public FileUtil () {
		
	}
	
	public static String read(int line) {
		final File file = new File("ipsave");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}
		BufferedReader reader;
		String retString = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			String readLine;
			if ((readLine = reader.readLine()) != null) {
				if (line == 1) {
					retString = readLine;
				} else if (line == 2) {
					retString = reader.readLine();
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retString;
	}
	
	public void write(String ip) {
		final File file = new File("ipsave");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(ip);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
