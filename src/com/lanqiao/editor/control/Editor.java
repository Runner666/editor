package com.lanqiao.editor.control;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTabbedPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

import com.lanqiao.editor.component.EditMune;
import com.lanqiao.editor.component.EditorFrame;
import com.lanqiao.editor.component.FileMune;
import com.lanqiao.editor.tools.EditManage;
import com.lanqiao.editor.tools.EditorTool;

public class Editor {
	public static void main(String[] args) {
		final EditorFrame editorFrame = new EditorFrame();
		final FileMune fileMune = new FileMune();
		final EditMune editMune=new EditMune();
		// �½��༭��
		fileMune.newItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileMune.createTab(editorFrame);
			}
		});

		// �رձ༭��
		fileMune.closeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editorFrame.jTabbedPane.remove(editorFrame.jTabbedPane.getSelectedIndex());
			}
		});

		// �����ı�
		fileMune.saveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileMune.doSave(editorFrame);
			}
		});
		
		// �����ı�
		fileMune.saveAsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileMune.doSaveAs(editorFrame);
			}
		});

		// �˳�
		fileMune.exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// �����ƣ��رմ���ʱ������ʾ�û��Ƿ񱣴��ļ�
				System.exit(0);
			}
		});

		// ��
		fileMune.openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					fileMune.doOpenFile(editorFrame);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//�Ŵ�
		editMune.enlargeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index=editorFrame.jTabbedPane.getSelectedIndex();
				editorFrame.fontSize=editorFrame.fontSize+5;
				editorFrame.jTextArea[index].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, editorFrame.fontSize));
			}
		});
		//��С
		editMune.reduceItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index=editorFrame.jTabbedPane.getSelectedIndex();
				editorFrame.fontSize=editorFrame.fontSize-5;
				editorFrame.jTextArea[index].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, editorFrame.fontSize));
			}
		});
		
		//�ָ�
		editMune.redoItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(editorFrame.undoManager.canRedo()){
					editorFrame.undoManager.redo();
				}
			}
		});
		
		//����
		editMune.undoItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(editorFrame.undoManager.canUndo()){
					editorFrame.undoManager.undo();
				}
			}
		});
	}
}