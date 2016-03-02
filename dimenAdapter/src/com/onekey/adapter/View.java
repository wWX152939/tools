package com.onekey.adapter;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class View {
	private JFrame frame = new JFrame("登录");
	private Container c = frame.getContentPane();
//	private JTextField username = new JTextField();
//	private JPasswordField password = new JPasswordField();
	private JButton ok = new JButton("确定");
	private JLabel mTitleLabel;
	private JPanel mCenterPanel;
	private JPanel mBottomPanel;
	
	public void initFrame() {
		frame.setSize(305,150);
		c.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		//顶部
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		mTitleLabel = new JLabel("请将dimens.xml文件放入执行文件同目录下");
		titlePanel.add(mTitleLabel);
		c.add(titlePanel, BorderLayout.NORTH);
		
		//中部表单
		mCenterPanel = new JPanel();
		mCenterPanel.setLayout(null);
		JLabel l1 = new JLabel("如需额外适配，请在dimens.txt中添加,多个按逗号");
		l1.setBounds(4, 2, 305, 20);
		mCenterPanel.add(l1);
		JLabel l2 = new JLabel("分隔。格式如下：values-land-hdpi_1280x720");
		l2.setBounds(10, 22, 305, 20);
		mCenterPanel.add(l2);
		mCenterPanel.setVisible(false);
		c.add(mCenterPanel,"Center");
		
		//底部按钮
		mBottomPanel = new JPanel();
		mBottomPanel.setLayout(new FlowLayout());
		mBottomPanel.add(ok);
		ok.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				execExport();
			}
		});
		JButton hint = new JButton();
		hint.setText("提示");
		hint.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				mCenterPanel.setVisible(true);
			}
		});
		mBottomPanel.add(hint);
		c.add(mBottomPanel, BorderLayout.SOUTH);
	}
	
	public void execExport() {
		for (int i = 0; i < Config.fileNames.length; i++) {
			String srcFileName = Config.SRC + "./" + Config.fileNames[i];
			File file = new File(srcFileName);
			if (!file.exists()) {
				mTitleLabel.setText("文件不存在, 请将dimens.xml文件放入同路径下");
				return;
			}
			for (int j = 0; j < Config.DPI.length; j++) {
				for (int k = 0; k < Config.heights.length; k++) {
					if (Logic.checkExport(Config.DPI[j], Config.heights[k]))
						Logic.readFileByLines(srcFileName, Config.DPI[j], Config.fileNames[i], Config.heights[k]);
				}
				
			}
			mTitleLabel.setText("导出成功");
			mCenterPanel.setVisible(false);
			ok.setText("关闭");
			ok.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
		}
	}
}
