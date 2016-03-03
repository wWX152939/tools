package com.onekey.connectIP;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View {
	private JFrame frame = new JFrame("登录");
	private Container c = frame.getContentPane();
	private JTextField connectIP = new JTextField();
	private JLabel mHintLabel;
//	private JPasswordField password = new JPasswordField();
	private JButton ok = new JButton("确定");
	private JLabel mTitleLabel;
	private JPanel mCenterPanel;
	private JPanel mBottomPanel;
	
	public void initFrame() {
		frame.setSize(305,180);
		c.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		//顶部
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		mTitleLabel = new JLabel("配置CEPM360网络IP");
		titlePanel.add(mTitleLabel);
		c.add(titlePanel, BorderLayout.NORTH);
		
		//中部表单
		mCenterPanel = new JPanel();
		mCenterPanel.setLayout(null);
		JLabel l1 = new JLabel("connectIP:");
		l1.setBounds(14, 2, 60, 20);
		mCenterPanel.add(l1);
		connectIP.setBounds(80, 2, 160, 20);
		mHintLabel = new JLabel("");
		mHintLabel.setBounds(14, 22, 280, 20);
		mCenterPanel.add(connectIP);
		mCenterPanel.add(mHintLabel);
		c.add(mCenterPanel,"Center");
		
		//底部按钮
		mBottomPanel = new JPanel();
		mBottomPanel.setLayout(new FlowLayout());
		
		JButton t1 = new JButton("t1");
		JButton t2 = new JButton("t2");
		t1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				connectIP.setText(FileUtil.read(1) == "" ? "192.168.18.118" : FileUtil.read(1));
			}
		});
		t2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				connectIP.setText(FileUtil.read(2) == "" ? "cepm360.f3322.net" : FileUtil.read(2));
			}
		});
		mBottomPanel.add(t1);
		mBottomPanel.add(t2);
		
		mBottomPanel.add(ok);
		ok.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				execFuction();
			}
		});

		c.add(mBottomPanel, BorderLayout.SOUTH);
	}
	
	private void execFuction() {
		String ip = connectIP.getText().toString();
		if (ip != null && !ip.isEmpty()) {
			ip = "connectIP=" + ip;
			final File file = new File("tmp.txt");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
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
				e.printStackTrace();
			}
			try {
				Runtime runtime = Runtime.getRuntime();
				Process p = runtime.exec("adb push tmp.txt /data/data/com.pm360.cepm360/files/connect_ip.txt");
				BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(err));
				String readLine = br.readLine();
//				System.out.print("readLine:" + readLine);
				if (!readLine.contains("KB/s")) {
					mHintLabel.setText(readLine);
				} else {
					mHintLabel.setText("设置成功");
					ok.setText("关闭");
					ok.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent arg0) {
							file.delete();
							System.exit(0);
						}
					});
				}
//				process.destroy();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			mHintLabel.setText("请输入IP");
		}
	}
	
}
