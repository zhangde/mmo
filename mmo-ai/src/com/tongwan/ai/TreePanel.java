package com.tongwan.ai;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.tongwan.common.ai.behaviortree.BehaviorTree;
import com.tongwan.common.ai.behaviortree.node.BehaviorNode;

/**
 * 树编辑面板
 * @author zhangde
 * @date 2013年12月27日
 */
public class TreePanel extends JPanel implements TreeSelectionListener{
	/** 行为树滚动面板容器 */
	private JScrollPane scrollPane;
	/** 当前选择的节点*/
	private TreePath selectedNode;
	/** 树组件*/
	private JTree tree;
	private MainJFrame mainJFrame;
	public static int width=0;
	public TreePanel(MainJFrame mainJFrame){
		super();
		this. mainJFrame= mainJFrame;
		this.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		width=MainJFrame.width-AiListPanel.width-50;
		this.setPreferredSize(new Dimension(width, 500));
	}

	public void showTree(BehaviorTree bTree){
		this.removeAll();
//		BehaviorNode root=new SelectorNode(name)
		AITreeNode top = new AITreeNode(bTree.getRoot());
//		AITreeNode top2 = new AITreeNode(bTree.getRoot());
//		top.add(top2);
		tree = new JTree(top);
		tree.setCellRenderer(new TreeNodeRenderer());
		tree.addTreeSelectionListener(this);
		tree.addMouseListener(new NodeEditPopupMenu(this,mainJFrame));
		this.add(tree);
		this.validate();
		this.repaint();
	}
	@Override
	public void valueChanged(TreeSelectionEvent event) {
		System.out.println(event);
		selectedNode=event.getPath();
	}
	/**
	 * 添加新节点
	 */
	public void addNode(BehaviorNode bn){
		AITreeNode node=new AITreeNode(bn);
		AITreeNode parent=getSelectedNode();
		parent.getBehaviorNode().addChilden(bn);
		DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
		treeModel.insertNodeInto(node, parent, parent.getChildCount());
//		parent.add(node);
//		tree.updateUI();
	}
	/**
	 * 删除选中的子节点
	 */
	public void removeNode(){
		DefaultMutableTreeNode current=getSelectedNode();
		if(current.getParent()!=null){
			DefaultTreeModel treeModel=(DefaultTreeModel) tree.getModel();
			treeModel.removeNodeFromParent(current);
		}else{
			JOptionPane.showMessageDialog(null, "不能删除根结点.", "错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * @return the tree
	 */
	public JTree getTree() {
		return tree;
	}
	/**
	 * 得到已选择的节点
	 */
	public AITreeNode getSelectedNode(){
		AITreeNode n=(AITreeNode) selectedNode.getLastPathComponent();
		return n;
	}
	public void changeSize(){
		width=MainJFrame.width-AiListPanel.width-50;
		this.setPreferredSize(new Dimension(width, 500));
	}
}
