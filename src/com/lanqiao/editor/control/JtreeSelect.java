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
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("��Դ������");
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
		// ����Tree��ѡ��ģʽΪһ��ֻ��ѡ��һ���ڵ�
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// ����Ƿ���TreeSelectionEvent�¼���
		tree.addTreeSelectionListener(this);

		// �������У�JSplitPane�У�����Ƿź���JTree��JScrollPane,�ұ��Ƿ�JEditorPane.
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

	// ������ʵ��valueChanged()����
	public void valueChanged(TreeSelectionEvent e) {
		JTree tree = (JTree) e.getSource();
		// ����JTree��getLastSelectedPathComponent()����ȡ��Ŀǰѡȡ�Ľڵ�.
		DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		String nodeName = selectionNode.toString();

		// �ж��Ƿ�Ϊ��Ҷ�ڵ㣬��������ʾ�ļ����ݣ������������κ��¡�
		if (selectionNode.isLeaf()) {
			/*
			 * ȡ���ļ���λ��·��,System.getProperty("user.dir")����ȡ��Ŀǰ������·����
			 * System.getProperty("file.separator")��ȡ���ļ��ָ�����������window������
			 * �ļ���ª����"\",��Unix�������ļ��ָ����պ��෴����"/".����System.getProperty()
			 * ���������ȡ�����е���Ϣ: java.version ��ʾjava�汾 java.endor ��ʾjava������
			 * java.endor.url ��ʾjava������URL java.home ��ʾjava�İ�װ·��
			 * java.class.version ��ʾjava��汾 java.class.path ��ʾjava classpath
			 * os.name ��ʾ����ϵͳ���� os.arch ��ʾ����ϵͳ�ṹ����x86 os.version ��ʾ����ϵͳ�汾
			 * file.separator ȡ���ļ��ָ��� path.separator ȡ��·���ָ�������Unix���ԡ�:����ʾ
			 * line.separator ȡ�û��з��ţ���Unix����"\n"��ʾ user.name ȡ���û����� user.home
			 * ȡ���û���Ŀ¼(home directory),��Windows��Administrator�ļ�Ŀ ¼Ϊc:\Documents
			 * and Settings\Administrator user.dir ȡ���û�Ŀǰ�Ĺ���Ŀ¼.
			 */
			String filepath = "file:" + System.getProperty("user.dir")
					+ System.getProperty("file.separator") + nodeName;

			try {
				// ����JEditorPane��setPage()�������ļ�������ʾ��editorPane�С����ļ�·������������IOException.
				editorPane.setPage(filepath);
			} catch (IOException ex) {
				System.out.println("�Ҳ������ļ�");
			}
		}
	}

}
