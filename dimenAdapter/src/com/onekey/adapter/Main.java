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
		 * ��ʾͷ
		 * @return
		 */
		String getTitle();
		
		/**
		 * ��ʾ��ʾ��Ϣ
		 * @return
		 */
		String getTip1();
		
		/**
		 * ��ʾ��ʾ��Ϣ
		 * @return
		 */
		String getTip2();
		
		/**
		 * ��ȡ�����ļ�����
		 * @return
		 */
		String getConfigName();
		
		/**
		 * ��Ӧ�¼�
		 */
		void clickFunction();
	}

}
