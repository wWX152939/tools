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
	private JFrame frame = new JFrame("��¼");
	private Container c = frame.getContentPane();
//	private JTextField username = new JTextField();
//	private JPasswordField password = new JPasswordField();
	private JButton ok = new JButton("ȷ��");
	private JLabel mTitleLabel;
	private JPanel mCenterPanel;
	private JPanel mBottomPanel;
	
	public void initFrame() {
		frame.setSize(305,150);
		c.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		//����
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		mTitleLabel = new JLabel("�뽫dimens.xml�ļ�����ִ���ļ�ͬĿ¼��");
		titlePanel.add(mTitleLabel);
		c.add(titlePanel, BorderLayout.NORTH);
		
		//�в���
		mCenterPanel = new JPanel();
		mCenterPanel.setLayout(null);
		JLabel l1 = new JLabel("����������䣬����dimens.txt�����,���������");
		l1.setBounds(4, 2, 305, 20);
		mCenterPanel.add(l1);
		JLabel l2 = new JLabel("�ָ�����ʽ���£�values-land-hdpi_1280x720");
		l2.setBounds(10, 22, 305, 20);
		mCenterPanel.add(l2);
		mCenterPanel.setVisible(false);
		c.add(mCenterPanel,"Center");
		
		//�ײ���ť
		mBottomPanel = new JPanel();
		mBottomPanel.setLayout(new FlowLayout());
		mBottomPanel.add(ok);
		ok.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				execExport();
			}
		});
		JButton hint = new JButton();
		hint.setText("��ʾ");
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
				mTitleLabel.setText("�ļ�������, �뽫dimens.xml�ļ�����ͬ·����");
				return;
			}
			for (int j = 0; j < Config.DPI.length; j++) {
				for (int k = 0; k < Config.heights.length; k++) {
					if (Logic.checkExport(Config.DPI[j], Config.heights[k]))
						Logic.readFileByLines(srcFileName, Config.DPI[j], Config.fileNames[i], Config.heights[k]);
				}
				
			}
			mTitleLabel.setText("�����ɹ�");
			mCenterPanel.setVisible(false);
			ok.setText("�ر�");
			ok.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
		}
	}
}
