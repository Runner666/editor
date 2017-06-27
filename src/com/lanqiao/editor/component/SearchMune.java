package com.lanqiao.editor.component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SearchMune {
	public static final JMenuItem lookupItem = new JMenuItem("查找/替换");

	/**
	 * @param index
	 *            查找
	 */
	public void doLookup(final JTextArea jTextArea) {
		JDialog searchDialog = new JDialog(EditorFrame.jFrame);
		// Dialog的设计
		searchDialog.setTitle("查找/替换");
		GridBagLayout gridBagLayout = new GridBagLayout();
		searchDialog.setLayout(gridBagLayout);
		JLabel lookupLable = new JLabel("查找目标:");
		final JTextField lookupTextFile = new JTextField(16);
		JButton searchButton = new JButton("查找下一个");
		JLabel replceLable = new JLabel("替换为:");
		final JTextField replaceTextFile = new JTextField(16);
		JButton replaceButton = new JButton("替换");
		JButton replaceAllButton = new JButton(" 替换全部   ");
		GridBagConstraints lookupLableGBC = new GridBagConstraints();
		lookupLableGBC.gridx = 1;
		lookupLableGBC.gridy = 1;
		searchDialog.add(lookupLable, lookupLableGBC);
		GridBagConstraints lookupTextFileGBC = new GridBagConstraints();
		lookupTextFileGBC.gridx = 2;
		lookupTextFileGBC.gridy = 1;
		searchDialog.add(lookupTextFile, lookupTextFileGBC);
		GridBagConstraints searchButtonGBC = new GridBagConstraints();
		searchButtonGBC.gridx = 3;
		searchButtonGBC.gridy = 1;
		searchDialog.add(searchButton, searchButtonGBC);
		GridBagConstraints replceLableGBC = new GridBagConstraints();
		replceLableGBC.gridx = 1;
		replceLableGBC.gridy = 2;
		searchDialog.add(replceLable, replceLableGBC);
		GridBagConstraints replaceTextFileGBC = new GridBagConstraints();
		replaceTextFileGBC.gridx = 2;
		replaceTextFileGBC.gridy = 2;
		searchDialog.add(replaceTextFile, replaceTextFileGBC);
		GridBagConstraints replaceAllButtonGBC = new GridBagConstraints();
		replaceAllButtonGBC.gridx = 3;
		replaceAllButtonGBC.gridy = 2;
		searchDialog.add(replaceAllButton, replaceAllButtonGBC);
		GridBagConstraints replaceButtonGBC = new GridBagConstraints();
		replaceButtonGBC.gridx = 4;
		replaceButtonGBC.gridy = 2;
		searchDialog.add(replaceButton, replaceButtonGBC);
		searchDialog.setSize(500, 180);
		searchDialog.setLocation(380, 300);
		searchDialog.setVisible(true);
		// 查找下一个按钮的监听
		searchButton.addActionListener(new ActionListener() {
			int start = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				String lookupTextFileString = lookupTextFile.getText();
				if (lookupTextFileString != null) {
					String jString = jTextArea.getText();
					int searchStringLength = lookupTextFileString.length();
					int selectIdex = jString.indexOf(lookupTextFileString, start);
				if (selectIdex != -1) {
					jTextArea.setCaretPosition(selectIdex);
					jTextArea.select(selectIdex, selectIdex + searchStringLength);
					}
					start = selectIdex + searchStringLength;
					if (start > jString.length()) {
						start = 0;
					}
				}

			}
		});

		// 替换当前
		replaceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jTextArea.getSelectedText()!=null){
					jTextArea.replaceSelection(replaceTextFile.getText());
				}
			}
		});

		// 全部替换
		replaceAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String jString = jTextArea.getText();
				if (replaceTextFile.getText() != null) {
					jString=jString.replaceAll(lookupTextFile.getText(),replaceTextFile.getText());
					jTextArea.setText(jString);
				}
			}
		});

	}
}
