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
		// 新建编辑区
		fileMune.newItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileMune.createTab(editorFrame);
			}
		});

		// 关闭编辑区
		fileMune.closeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editorFrame.jTabbedPane.remove(editorFrame.jTabbedPane.getSelectedIndex());
			}
		});

		// 保存文本
		fileMune.saveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileMune.doSave(editorFrame);
			}
		});
		
		// 另保存文本
		fileMune.saveAsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileMune.doSaveAs(editorFrame);
			}
		});

		// 退出
		fileMune.exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 待完善，关闭窗口时可以提示用户是否保存文件
				System.exit(0);
			}
		});

		// 打开
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
		
		//放大
		editMune.enlargeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index=editorFrame.jTabbedPane.getSelectedIndex();
				editorFrame.fontSize=editorFrame.fontSize+5;
				editorFrame.jTextArea[index].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, editorFrame.fontSize));
			}
		});
		//缩小
		editMune.reduceItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index=editorFrame.jTabbedPane.getSelectedIndex();
				editorFrame.fontSize=editorFrame.fontSize-5;
				editorFrame.jTextArea[index].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, editorFrame.fontSize));
			}
		});
		
		//恢复
		editMune.redoItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(editorFrame.undoManager.canRedo()){
					editorFrame.undoManager.redo();
				}
			}
		});
		
		//撤销
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