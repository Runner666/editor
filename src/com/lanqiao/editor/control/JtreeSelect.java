package com.lanqiao.editor.control;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class JtreeSelect implements TreeSelectionListener {

	JEditorPane editorPane;

	public JtreeSelect() {
		JFrame jFrame = new JFrame("TreeDemo");
		Container contentPane = jFrame.getContentPane();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("资源管理器");
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(
				"TreeDemo1.java");
		root.add(node);
		node = new DefaultMutableTreeNode("TreeDemo2.java");
		root.add(node);
		node = new DefaultMutableTreeNode("TreeDemo3.java");
		root.add(node);
		node = new DefaultMutableTreeNode("TreeDemo4.java");
		root.add(node);

		JTree tree = new JTree(root);
		// 设置Tree的选择模式为一次只能选择一个节点
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// 检查是否有TreeSelectionEvent事件。
		tree.addTreeSelectionListener(this);

		// 下面五行，JSplitPane中，左边是放含有JTree的JScrollPane,右边是放JEditorPane.
		JScrollPane scrollPane1 = new JScrollPane(tree);
		editorPane = new JEditorPane();
		JScrollPane scrollPane2 = new JScrollPane(editorPane);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				true, scrollPane1, scrollPane2);

		contentPane.add(splitPane);
		jFrame.pack();
		jFrame.setVisible(true);

		jFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	// 本方法实作valueChanged()方法
	public void valueChanged(TreeSelectionEvent e) {
		JTree tree = (JTree) e.getSource();
		// 利用JTree的getLastSelectedPathComponent()方法取得目前选取的节点.
		DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		String nodeName = selectionNode.toString();

		// 判断是否为树叶节点，若是则显示文件内容，若不是则不做任何事。
		if (selectionNode.isLeaf()) {
			/*
			 * 取得文件的位置路径,System.getProperty("user.dir")可以取得目前工作的路径，
			 * System.getProperty("file.separator")是取得文件分隔符，例如在window环境的
			 * 文件分陋符是"\",而Unix环境的文件分隔符刚好相反，是"/".利用System.getProperty()
			 * 方法你可以取得下列的信息: java.version 显示java版本 java.endor 显示java制造商
			 * java.endor.url 显示java制造商URL java.home 显示java的安装路径
			 * java.class.version 显示java类版本 java.class.path 显示java classpath
			 * os.name 显示操作系统名称 os.arch 显示操作系统结构，如x86 os.version 显示操作系统版本
			 * file.separator 取得文件分隔符 path.separator 取得路径分隔符，如Unix是以“:”表示
			 * line.separator 取得换行符号，如Unix是以"\n"表示 user.name 取得用户名称 user.home
			 * 取得用户家目录(home directory),如Windows中Administrator的家目 录为c:\Documents
			 * and Settings\Administrator user.dir 取得用户目前的工作目录.
			 */
			String filepath = "file:" + System.getProperty("user.dir")
					+ System.getProperty("file.separator") + nodeName;

			try {
				// 利用JEditorPane的setPage()方法将文件内容显示在editorPane中。若文件路径错误，则会产生IOException.
				editorPane.setPage(filepath);
			} catch (IOException ex) {
				System.out.println("找不到此文件");
			}
		}
	}

}
