package com.lanqiao.editor.tools;

import java.io.File;

import com.lanqiao.editor.component.EditorFrame;
import com.lanqiao.editor.component.FileMune;

public class EditorTool {
	public static void searchFile(File file1, String fileName) {
		if (file1.isDirectory()) {
			File[] files = file1.listFiles();
			if (files.length == 0 && file1.getName().equals(fileName)) {
				FileMune.file = new File(file1.getAbsolutePath());
			}
			for (File file2 : files) {
				searchFile(new File(file2.getAbsolutePath()), fileName);
			}
		} else {
			if (file1.getName().equals(fileName)) {
				FileMune.file = new File(file1.getAbsolutePath());
			}
		}
	}

	public static void searchFileForSave(File file1, String fileName) {
		if (file1.isDirectory()) {
			File[] files = file1.listFiles();
			for (File file2 : files) {
				searchFileForSave(new File(file2.getAbsolutePath()), fileName);
			}
		} else {
			if (file1.getName().equals(fileName)) {
				EditorFrame.jTreeFile = new File(file1.getAbsolutePath());
			}
		}
	}

	public static String bSubstring(String string, int length) throws Exception {
		byte[] bytes = string.getBytes("Unicode");
		int n = 0; // ��ʾ��ǰ���ֽ���
		int i = 2; // Ҫ��ȡ���ֽ������ӵ�3���ֽڿ�ʼ
		for (; i < bytes.length && n < length; i++) {
			// ����λ�ã���3��5��7�ȣ�ΪUCS2�����������ֽڵĵڶ����ֽ�
			if (i % 2 == 1) {
				n++; // ��UCS2�ڶ����ֽ�ʱn��1
			} else {
				// ��UCS2����ĵ�һ���ֽڲ�����0ʱ����UCS2�ַ�Ϊ���֣�һ�������������ֽ�
				if (bytes[i] != 0) {
					n++;
				}
			}
		}
		// ���iΪ����ʱ�������ż��
		if (i % 2 == 1) {
			// ��UCS2�ַ��Ǻ���ʱ��ȥ�������һ��ĺ���
			if (bytes[i - 1] != 0)
				i = i - 1;
			// ��UCS2�ַ�����ĸ�����֣��������ַ�
			else
				i = i + 1;
		}
		if (bytes.length > 5) {
			return new String(bytes, 0, i, "Unicode") + "��";
		} else {
			return string + "     ";
		}
	}
}
