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
	public static final JMenuItem lookupItem = new JMenuItem("����/�滻");

	/**
	 * @param index
	 *            ����
	 */
	public void doLookup(final JTextArea jTextArea) {
		JDialog searchDialog = new JDialog(EditorFrame.jFrame);
		// Dialog�����
		searchDialog.setTitle("����/�滻");
		GridBagLayout gridBagLayout = new GridBagLayout();
		searchDialog.setLayout(gridBagLayout);
		JLabel lookupLable = new JLabel("����Ŀ��:");
		final JTextField lookupTextFile = new JTextField(16);
		JButton searchButton = new JButton("������һ��");
		JLabel replceLable = new JLabel("�滻Ϊ:");
		final JTextField replaceTextFile = new JTextField(16);
		JButton replaceButton = new JButton("�滻");
		JButton replaceAllButton = new JButton(" �滻ȫ��   ");
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
		// ������һ����ť�ļ���
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

		// �滻��ǰ
		replaceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jTextArea.getSelectedText()!=null){
					jTextArea.replaceSelection(replaceTextFile.getText());
				}
			}
		});

		// ȫ���滻
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
