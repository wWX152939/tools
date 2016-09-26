package com.onekey.adapter;

public class Main {
	private static WindowInterface sWindowInterface;
	public static void main(String[] args) {
		sWindowInterface = new Logic();
		View view = new View(sWindowInterface);
		view.initFrame();
	}
	
	public interface WindowInterface {
		/**
		 * 显示头
		 * @return
		 */
		String getTitle();
		
		/**
		 * 显示提示信息
		 * @return
		 */
		String getTip1();
		
		/**
		 * 显示提示信息
		 * @return
		 */
		String getTip2();
		
		/**
		 * 获取配置文件名称
		 * @return
		 */
		String getConfigName();
		
		/**
		 * 相应事件
		 */
		void clickFunction();
	}

}
