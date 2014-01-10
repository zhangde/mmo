package com.tongwan.ai.ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.tongwan.ai.node.BehaviorNode;
import com.tongwan.ai.node.SelectorNode;
import com.tongwan.ai.node.SequenceNode;
import com.tongwan.ai.type.NodeType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * @author zhangde
 * @date 2013-12-29
 */
public class NodeEditPopupMenu extends MouseAdapter{
	private MainJFrame mainJFrame;
	private JPopupMenu jPopupMenu;
	private JMenuItem editItem;
	private JMenuItem deleteItem;
	private JMenuItem addChildItem;
	private final TreePanel treeJPanel;
	private static final SelectNodeType NODE_TYPES[]={new SelectNodeType(NodeType.SELECTOR,"行为选择节点"),new SelectNodeType(NodeType.SEQUENCE,"行为顺序节点"),new SelectNodeType(NodeType.CONDITION,"行为判定节点"),new SelectNodeType(NodeType.ACTION,"行为动作节点")};

	public NodeEditPopupMenu(final TreePanel treeJPanel,final MainJFrame mainJFrame){
		this.mainJFrame=mainJFrame;
		this.treeJPanel=treeJPanel;
		jPopupMenu=new JPopupMenu();
		editItem=new JMenuItem("编辑");
		deleteItem=new JMenuItem("删除");
		addChildItem=new JMenuItem("添加子节点");
		editItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("sss"+e.getActionCommand());
				String content=JOptionPane.showInputDialog(null,"选择新建节点类型","节点编辑",JOptionPane.INFORMATION_MESSAGE);
				DefaultMutableTreeNode node=treeJPanel.getSelectedNode();
				node.setUserObject(content);
				
			}
		});
		addChildItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectNodeType selectNodeType = (SelectNodeType) JOptionPane.showInputDialog(null,"请选择新建节点类型:\n", "节点类型", JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), NODE_TYPES, "test");
				System.out.println(selectNodeType);
				BehaviorNode newNode;
				
				switch (selectNodeType.getType()) {
				case SELECTOR:
					String contentSEL=JOptionPane.showInputDialog(null,"新选择节点名称","节点编辑",JOptionPane.INFORMATION_MESSAGE);
					
					newNode=new SelectorNode(contentSEL);
					treeJPanel.addNode(newNode);
					break;
				case SEQUENCE:
					String contentSEQ=JOptionPane.showInputDialog(null,"新顺序节点名称","节点编辑",JOptionPane.INFORMATION_MESSAGE);
					newNode=new SequenceNode(contentSEQ);
					treeJPanel.addNode(newNode);
					break;
				case CONDITION:
					SelectCondListFrame frame=new SelectCondListFrame(mainJFrame);
					frame.setVisible(true);
					break;
				case ACTION:
					SelectActionListFrame actionFrame=new SelectActionListFrame(mainJFrame);
					actionFrame.setVisible(true);
					break;
				default:
					break;
				}
				
			}
		});
		deleteItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				treeJPanel.removeNode();
			}
		});
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			
			jPopupMenu.add(editItem);
			jPopupMenu.add(deleteItem);
			jPopupMenu.add(addChildItem);
			jPopupMenu.show(treeJPanel, e.getX(), e.getY());
//			if(mainJFrame.getTreePanel().getSelectedNode()==null){
				
				if(e.getSource() instanceof JTree){
					JTree jtree=(JTree) e.getSource();
					Object o=jtree.findComponentAt(e.getX(), e.getY());
//					jtree.get
					TreePath treePath = jtree.getPathForLocation(e.getX(), e.getY());
					if(treePath!=null){
						jtree.setSelectionPath(treePath);
					}
					System.out.println();
				}
				
//			}
		}
	}
}
