package com.lanqiao.editor.component;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.lanqiao.editor.tools.EditorTool;

public class FileMune {
	public static final JMenuItem newItem = new JMenuItem("新建");
	public static final JMenuItem openItem = new JMenuItem("打开");
	public static final JMenuItem saveItem = new JMenuItem("保存");
	public static final JMenuItem saveAsItem = new JMenuItem("另存为");
	public static final JMenuItem printItem = new JMenuItem("打印");
	public static final JMenuItem closeItem = new JMenuItem("关闭");
	public static final JMenuItem exitItem = new JMenuItem("退出");
	public static File file;

	/**
	 * 将文本区的文字另存到一个文件夹
	 * 
	 * @param editorFrame
	 */
	public void doSaveAs(EditorFrame editorFrame) {
		FileDialog fileDialog = new FileDialog(editorFrame.jFrame, "另存为", FileDialog.SAVE);// 保存文件对话框

		fileDialog.addWindowListener(new WindowAdapter() { // 关闭文件对话框窗口
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		fileDialog.setVisible(true);

		// 得到所输入的文本内容
		String s = editorFrame.jTextArea[editorFrame.jTabbedPane.getSelectedIndex()].getText();

		try// 异常处理
		{
			File f = new File(fileDialog.getDirectory() + fileDialog.getFile());// 新建文件
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));// 输入到文件中
			bw.write(s, 0, s.length());
			bw.close();
			int index = editorFrame.jTabbedPane.getSelectedIndex();
			editorFrame.jTabbedPane.setTitleAt(index, fileDialog.getFile());
		} catch (FileNotFoundException fe) {
			System.out.println("file not found");
			System.exit(0);
		} catch (IOException ie) {
			System.out.println(" IO error");
			System.exit(0);
		}
	}

	/**
	 * 创建一个新的文本区
	 * 
	 * @param editorFrame
	 */
	public void createTab(EditorFrame editorFrame) {
		int index = editorFrame.jTabbedPane.getComponentCount();
		editorFrame.jTextArea[index] = new JTextArea();
		editorFrame.jTextArea[index].setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, editorFrame.fontSize));
		editorFrame.jTabbedPane.addTab("new " + (int) (index + 1), editorFrame.jTextArea[index]);
		editorFrame.jTabbedPane.setSelectedIndex(index);
		editorFrame.jTextArea[index].setCaretPosition(0);
		editorFrame.jTextArea[index].getDocument().addUndoableEditListener(editorFrame.undoManager);
	}

	/**
	 * @param editorFrame
	 *            保存
	 */
	public void doSave(EditorFrame editorFrame) {
		int index = editorFrame.jTabbedPane.getSelectedIndex();
		String fileName = editorFrame.jTabbedPane.getToolTipTextAt(index);
		// 如果
		if(fileName==null){
			this.doSaveAs(editorFrame);
		}else{
			new EditorTool().searchFileForSave(editorFrame.jTreeFile, fileName);
			try {
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(editorFrame.jTreeFile));
				String str = editorFrame.jTextArea[index].getText();
				bufferedWriter.write(str, 0, str.length());
				bufferedWriter.close();
			} catch (IOException e1) {
				System.out.println("file not found");
			}
		}
	}

	/**
	 * 打开一个文件
	 * 
	 * @param editorFrame
	 * @return
	 * @throws Exception
	 */
	public void doOpenFile(final EditorFrame editorFrame) throws Exception {
		DefaultMutableTreeNode root;
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setFileSelectionMode(jFileChooser.FILES_AND_DIRECTORIES);
		jFileChooser.setCurrentDirectory(new File("E:\\"));
		int state = jFileChooser.showOpenDialog(null);
		if (state == 1) {
			return;
		} else {
			editorFrame.jTreeFile = jFileChooser.getSelectedFile();
			root = new DefaultMutableTreeNode(editorFrame.jTreeFile.getName());
			getFile(editorFrame.jTreeFile, root);
		}
		jFileChooser.setVisible(false);

		editorFrame.jTree = new JTree(root);
		DefaultTreeCellRenderer render = new DefaultTreeCellRenderer(); // 树叶
		render.setTextSelectionColor(Color.blue); // 选择时候的颜色
		render.setTextNonSelectionColor(Color.black); // 非选择时候颜色
		editorFrame.jTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				JTree tree = (JTree) e.getSource();
				EditorTool editorTool = new EditorTool();
				// 利用JTree的getLastSelectedPathComponent()方法取得目前选取的节点.
				DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

				String nodeName = selectionNode.toString();
				if (selectionNode.isLeaf()) {
					editorTool.searchFile(editorFrame.jTreeFile, nodeName);
					File[] tempFile = null;
					if (file.isDirectory()) {
						tempFile = file.listFiles();
					}
					if (tempFile == null) {
						// 是叶节点，并且不是文件夹
						try {
							BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
							String string = null;
							int index = editorFrame.jTabbedPane.getComponentCount();
							editorFrame.jTextArea[index] = new JTextArea();
							int lineNumber = 1;
							while ((string = bufferedReader.readLine()) != null) {
								editorFrame.jTextArea[index].append(string + "\n");
							}
							editorFrame.jTabbedPane.addTab(editorTool.bSubstring(nodeName, 10), null,
									editorFrame.jTextArea[index], nodeName);
							editorFrame.jTextArea[index]
									.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, editorFrame.fontSize));
							editorFrame.jTabbedPane.setSelectedIndex(index);
							editorFrame.jTextArea[index].setCaretPosition(0);
							editorFrame.jTextArea[index].getDocument().addUndoableEditListener(editorFrame.undoManager);
							bufferedReader.close();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		editorFrame.jTree.setCellRenderer(render);
		editorFrame.jTreejScrollPane = new JScrollPane(editorFrame.jTree,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		editorFrame.jSplitPane.setLeftComponent(editorFrame.jTreejScrollPane);
		editorFrame.jSplitPane.setDividerLocation(130);
	}

	private static void getFile(File file, DefaultMutableTreeNode root) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(file2.getName());
				root.add(node);
				getFile(new File(file2.getAbsolutePath()), node);
			}
		} else {
		}
	}
}