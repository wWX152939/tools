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

import com.onekey.adapter.Main.WindowInterface;

public class View {
	private WindowInterface mWindowInterface;
	private JFrame frame = new JFrame("登录");
	private Container c = frame.getContentPane();
//	private JTextField username = new JTextField();
//	private JPasswordField password = new JPasswordField();
	private JButton ok = new JButton("确定");
	private JLabel mTitleLabel;
	private JPanel mCenterPanel;
	private JPanel mBottomPanel;
	
	public View(WindowInterface windowInterface) {
		mWindowInterface = windowInterface;
	}
	
	public void initFrame() {
		frame.setSize(305,150);
		c.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		//顶部
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		mTitleLabel = new JLabel(mWindowInterface.getTitle());
		titlePanel.add(mTitleLabel);
		c.add(titlePanel, BorderLayout.NORTH);
		
		//中部表单
		mCenterPanel = new JPanel();
		mCenterPanel.setLayout(null);
		JLabel l1 = new JLabel(mWindowInterface.getTip1());
		l1.setBounds(4, 2, 305, 20);
		mCenterPanel.add(l1);
		JLabel l2 = new JLabel(mWindowInterface.getTip2());
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
	
	/**
	 * 点击相应事件
	 */
	public void execExport() {
		String srcFileName = Config.SRC + "./" + mWindowInterface.getConfigName();
		File file = new File(srcFileName);
		if (!file.exists()) {
			mTitleLabel.setText("文件不存在, 请将" + mWindowInterface.getConfigName() + "文件放入同路径下");
			return;
		}
		mWindowInterface.clickFunction();
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
