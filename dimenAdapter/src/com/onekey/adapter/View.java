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
	private JFrame frame = new JFrame("��¼");
	private Container c = frame.getContentPane();
//	private JTextField username = new JTextField();
//	private JPasswordField password = new JPasswordField();
	private JButton ok = new JButton("ȷ��");
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
		//����
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		mTitleLabel = new JLabel(mWindowInterface.getTitle());
		titlePanel.add(mTitleLabel);
		c.add(titlePanel, BorderLayout.NORTH);
		
		//�в���
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
	
	/**
	 * �����Ӧ�¼�
	 */
	public void execExport() {
		String srcFileName = Config.SRC + "./" + mWindowInterface.getConfigName();
		File file = new File(srcFileName);
		if (!file.exists()) {
			mTitleLabel.setText("�ļ�������, �뽫" + mWindowInterface.getConfigName() + "�ļ�����ͬ·����");
			return;
		}
		mWindowInterface.clickFunction();
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
