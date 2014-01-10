package com.tongwan.ai;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author zhangde
 * @date 2013年12月27日
 */
public class UIMain {

	public static void main(String[] args) {
		MainJFrame mainJFrame=new MainJFrame();
		mainJFrame.init();
		
		// 创建没有父节点和子节点、但允许有子节点的树节点，并使用指定的用户对象对它进行初始化。
//         public DefaultMutableTreeNode(Object userObject)
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("软件部");
        node1.add(new DefaultMutableTreeNode("小花"));
        node1.add(new DefaultMutableTreeNode("小虎"));
        node1.add(new DefaultMutableTreeNode("小龙"));
 
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("销售部");
        node2.add(new DefaultMutableTreeNode("小叶"));
        node2.add(new DefaultMutableTreeNode("小雯"));
        node2.add(new DefaultMutableTreeNode("小夏"));
 
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("职员管理");
 
        top.add(new DefaultMutableTreeNode("总经理"));
        top.add(node1);
        top.add(node2);
        final JTree tree = new JTree(top);
        JFrame f = new JFrame("JTreeDemo");
        f.add(tree);
        f.setSize(300, 300);
//        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加选择事件
        tree.addTreeSelectionListener(new TreeSelectionListener() {
 
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();
 
                if (node == null)
                    return;
 
                Object object = node.getUserObject();
                if (node.isLeaf()) {
//                    User user = (User) object;
                    System.out.println("你选择了：" + object.toString());
                }
 
            }
        });
	}
	
}
