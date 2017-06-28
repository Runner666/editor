package com.lanqiao.editor.component;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.undo.UndoManager;

public class EditorFrame {
	private static final int WIDTH = 1000;
	private static final int LENGTH = 600;

	public static JFrame jFrame;
	public static JTabbedPane jTabbedPane;
	public static JTextArea []jTextArea=new JTextArea[100];
	public static JScrollPane jTreejScrollPane;
	private JMenuBar jMenuBar;
	public static File jTreeFile;
	public static JSplitPane jSplitPane;
	public static JTree jTree;
	private JScrollPane jTextAreajScrollPane;
	public static int fontSize=20;
	public static UndoManager undoManager;
	
	public EditorFrame() {
		super();
		jFrame = new JFrame("编辑器");
		jFrame.setSize(WIDTH, LENGTH);
		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
		jFrame.setLocation(200, 100);

		// 创建菜单条和菜单
		FrameJMenuBar frameJMenuBar = new FrameJMenuBar();
		jMenuBar = new JMenuBar();
		jMenuBar.add(frameJMenuBar.fileMune);
		jMenuBar.add(frameJMenuBar.editMune);
		jMenuBar.add(frameJMenuBar.searchMune);
		//jMenuBar.add(frameJMenuBar.setingMune);
		//jMenuBar.add(frameJMenuBar.winMune);
		jMenuBar.add(frameJMenuBar.helpMune);
		jFrame.setJMenuBar(jMenuBar);

		// 设置分隔条
		jSplitPane = new JSplitPane();
		jSplitPane.setDividerSize(2);
		jSplitPane.setDividerLocation(130);
		// 分隔条左侧的目录树
		jTreejScrollPane = new JScrollPane(jTree, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jSplitPane.setLeftComponent(jTreejScrollPane);
		// 分隔条右侧的文本编辑区
		jTabbedPane = new JTabbedPane();
		jTextAreajScrollPane = new JScrollPane(jTabbedPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jSplitPane.setRightComponent(jTextAreajScrollPane);
		jFrame.getContentPane().add(jSplitPane);
		jFrame.setVisible(true);
		undoManager=new UndoManager();

	}

	public void deleteTab(int index) {
		jTabbedPane.remove(index);
	}

}
